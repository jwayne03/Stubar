package com.example.stubar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.user.User;
import com.example.stubar.utils.serializer.LocalDateSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
            sendRequest();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendRequest() {

        User newUser = checkData();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(newUser));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://46.101.46.166:8080/stuapi/api/user";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                response -> Log.d("Response", response.toString()),
                error -> Log.d("Error.Response", error.toString()));
        queue.add(putRequest);
    }

    private User checkData() {
        User newUser = new User();
        newUser.setName(this.edName.getText().toString().trim());
        newUser.setSurname(this.edSurname.getText().toString().trim());
        newUser.setUsername(this.edUsername.getText().toString().trim());
        newUser.setEmail(this.edEmail.getText().toString().trim());
        newUser.setBirthday(this.edBirthday.getText().toString().trim());
        newUser.setPassword(this.edPassword.getText().toString().trim());
        newUser.setInstitution("UAB");
        newUser.setProfilePhoto("test");

        return newUser;

    }
}
