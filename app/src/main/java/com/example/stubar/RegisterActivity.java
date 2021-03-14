package com.example.stubar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkData(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        Snackbar snackbar = Snackbar.make(view, "FATAL ERROR", Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.orange));

        if (!edPassword.getText().toString().equals(edConfirmPassword.getText().toString())) {
            snackbar.setText("The passwords don't coincide").show();
        } else if (!this.edEmail.getText().toString().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            snackbar.setText("The email is in the incorrect format").show();
        } else if (!this.edBirthday.getText().toString().matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
            snackbar.setText("The Birthday is in the incorrect format").show();
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
