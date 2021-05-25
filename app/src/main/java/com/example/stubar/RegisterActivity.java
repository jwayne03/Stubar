package com.example.stubar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.institution.Institution;
import com.example.stubar.model.institution.InstitutionAdapter;
import com.example.stubar.model.institution.InstitutionApiResponse;
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
    Spinner spInstitution;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = findViewById(R.id.edName);
        edSurname = findViewById(R.id.edSurname);
        edUsername = findViewById(R.id.edUsername);
        edEmail = findViewById(R.id.edEmail);
        edBirthday = findViewById(R.id.edBirthday);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        spInstitution = findViewById(R.id.spInstitution);

        cbTermsConditions = findViewById(R.id.cbTermsConditions);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLoginHere = findViewById(R.id.btnLoginHere);

        setSpInstitution();

        btnLoginHere.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });

        btnSignUp.setOnClickListener(this::checkData);
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
        Snackbar snackbar = Snackbar.make(view, "FATAL ERROR", Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.orange));
        InstitutionAdapter adapter = (InstitutionAdapter) this.spInstitution.getAdapter();
        String institution = adapter.getItemName(this.spInstitution.getSelectedItemPosition());

        if (!edPassword.getText().toString().equals(edConfirmPassword.getText().toString())) {
            snackbar.setText(R.string.errorPassword).show();
        } else if (!this.edEmail.getText().toString().matches(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
                        "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-" +
                        "\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)" +
                        "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]" +
                        "|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|" +
                        "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\" +
                        "x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            snackbar.setText(R.string.errorEmail).show();
        } else if (!this.edBirthday.getText().toString().matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
            snackbar.setText(R.string.errorBirthday).show();
        } else if (institution.equals("Institution")) {
            snackbar.setText(R.string.errorInstitution).show();
        } else {
            User newUser = new User();
            newUser.setName(this.edName.getText().toString().trim());
            newUser.setSurname(this.edSurname.getText().toString().trim());
            newUser.setUsername(this.edUsername.getText().toString().trim());
            newUser.setEmail(this.edEmail.getText().toString().trim());
            newUser.setBirthday(this.edBirthday.getText().toString().trim());
            newUser.setPassword(this.edPassword.getText().toString().trim());

            InstitutionAdapter a = (InstitutionAdapter) this.spInstitution.getAdapter();
            newUser.setInstitution(a.getItemName(this.spInstitution.getSelectedItemPosition()));

            registerUser(newUser);
        }
    }

    private void setSpInstitution() {
        RequestQueue queque = Volley.newRequestQueue(this);
        String url = Constants.INSTITUTION_URL;
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new Gson();
            InstitutionApiResponse listOfInstitutions = gson.fromJson(response, InstitutionApiResponse.class);
            if (listOfInstitutions.size() == 0) {
                spInstitution.setVisibility(View.GONE);
                Log.d("ERROR", "setSpInstitution: " + response);
            } else {
                listOfInstitutions.add(0, new Institution());
                spInstitution.setAdapter(new InstitutionAdapter(this, listOfInstitutions));
            }
        }, error -> Log.d("ERROR", "Error downloading institutions"));
        queque.add(request);
    }
}
