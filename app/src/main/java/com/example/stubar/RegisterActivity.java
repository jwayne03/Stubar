package com.example.stubar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.user.User;
import com.example.stubar.utils.constants.Constants;
import com.example.stubar.utils.serializer.LocalDateSerializer;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class RegisterActivity extends AppCompatActivity {

    EditText edName, edSurname, edUsername, edEmail, edLocation, edBirthday,
            edPassword, edConfirmPassword;

    CheckBox cbTermsConditions;
    Button btnSignUp, btnLoginHere;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = findViewById(R.id.edName);
        edSurname = findViewById(R.id.edSurname);
        edUsername = findViewById(R.id.edUsername);
        edEmail = findViewById(R.id.edEmail);
//        edLocation = findViewById(R.id.edLocation);
        edBirthday = findViewById(R.id.edBirthday);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);

        cbTermsConditions = findViewById(R.id.cbTermsConditions);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLoginHere = findViewById(R.id.btnLoginHere);

        btnLoginHere.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });

        btnSignUp.setOnClickListener(v -> {
            checkData(v);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerUser(User newUser) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(newUser));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, Constants.REGISTER_URL, jsonObject,
                response -> Log.d("Response", response.toString()),
                error -> Log.d("Error.Response", error.toString()));
        queue.add(putRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkData(View view) {



        if (!edPassword.getText().toString().equals(edConfirmPassword.getText().toString())) {
            Snackbar.make(view, "The passwords don't coincide", Snackbar.LENGTH_LONG)
                    .show();
        } else if (this.edEmail.getText().toString().matches("/^(([^<>()\\[\\]\\\\.,;:\\s@”]+(\\.[^<>()\\[\\]\\\\.,;:\\s@”]+)*)|(“.+”))@((\\[[0–9]{1,3}\\.[0–9]{1,3}\\.[0–9]{1,3}\\.[0–9]{1,3}])|(([a-zA-Z\\-0–9]+\\.)+[a-zA-Z]{2,}))$/")) {
            Snackbar.make(view, "The email is in the incorrect format", Snackbar.LENGTH_LONG)
                    .show();
        } else if (this.edBirthday.getText().toString().matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            Snackbar.make(view, "The Birthday is in the incorrect format", Snackbar.LENGTH_LONG)
                    .show();
        } else {
            User newUser = new User();
            newUser.setName(this.edName.getText().toString().trim());
            newUser.setSurname(this.edSurname.getText().toString().trim());
            newUser.setUsername(this.edUsername.getText().toString().trim());
            newUser.setEmail(this.edEmail.getText().toString().trim());
            newUser.setBirthday(this.edBirthday.getText().toString().trim());
            newUser.setPassword(this.edPassword.getText().toString().trim());
            newUser.setInstitution("UAB");
            newUser.setProfilePhoto("test");

            registerUser(newUser);
        }
    }
}
