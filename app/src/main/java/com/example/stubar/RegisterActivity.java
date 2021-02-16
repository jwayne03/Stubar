package com.example.stubar;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText edName, edUsername, edEmail, edLocation, edBirthday,
            edPassword, edConfirmPassword;

    CheckBox cbTermsConditions;
    Button btnSignUp, btnLoginHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = findViewById(R.id.edName);
        edUsername = findViewById(R.id.edUsername);
        edEmail = findViewById(R.id.edEmail);
        edLocation = findViewById(R.id.edLocation);
        edBirthday = findViewById(R.id.edBirthday);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);

        cbTermsConditions = findViewById(R.id.cbTermsConditions);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLoginHere = findViewById(R.id.btnLoginHere);

        sendRequest();
    }

    private void sendRequest() {
        String url = "http://46.101.46.166:8080/stuapi/api/user";
        RequestQueue queue = Volley.newRequestQueue(this);

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", edUsername);
            jsonObject.put("password", edPassword);
            jsonObject.put("name", edName);
            jsonObject.put("email", edEmail);
            jsonObject.put("surname", null);
            jsonObject.put("profilePhoto", null);
            jsonObject.put("birthday", edBirthday);
            jsonObject.put("institution", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                });

        queue.add(putRequest);
    }


}
