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
import androidx.appcompat.app.AlertDialog;
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
    private EditText edName;
    private EditText edSurname;
    private EditText edUsername;
    private EditText edEmail;
    private EditText edBirthday;
    private EditText edPassword;
    private EditText edConfirmPassword;

    private CheckBox cbTermsConditions;
    private Spinner spInstitution;

    /**
     * Method that invokes the UI of the activity
     *
     * @param savedInstanceState Bundle
     */
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
        Button btnConditions = findViewById(R.id.btnConditions);

        cbTermsConditions = findViewById(R.id.cbTermsConditions);

        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnLoginHere = findViewById(R.id.btnLoginHere);

        setSpInstitution();

        btnConditions.setOnClickListener(view -> showConditionsAlert());

        btnLoginHere.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });

        btnSignUp.setOnClickListener(this::checkData);
    }

    private void showConditionsAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Terms and Conditions of Stubar");
        builder.setMessage("Conditions of use\n" +
                "By using this application, you certify that you have read and reviewed this Agreement and that you agree to comply with its terms. If you do not want to be bound by the terms of this Agreement, you are advised to uninstall the application accordingly. Stubar only grants use and access of this application, its products, and its services to those who have accepted its terms.\n" +
                "\nPrivacy policy\n" +
                "Before you continue using our application, we advise you to read our privacy policy stu.cat regarding our user data collection. It will help you better understand our practices.\n" +
                "\nAge restriction\n" +
                "You must be at least 16 (sixteen) years of age before you can use this application. By using this application, you warrant that you are at least 16 years of age and you may legally adhere to this Agreement. Stubar assumes no responsibility for liabilities related to age misrepresentation.\n" +
                "\nIntellectual property\n" +
                "You agree that all materials, products, and services provided on this application are the property of Stubar, its affiliates, directors, officers, employees, agents, suppliers, or licensors including all copyrights, trade secrets, trademarks, patents, and other intellectual property. You also agree that you will not reproduce or redistribute the Stubar’s intellectual property in any way, including electronic, digital, or new trademark registrations.\n" +
                "You grant Stubar a royalty-free and non-exclusive license to display, use, copy, transmit, and broadcast the content you upload and publish. For issues regarding intellectual property claims, you should contact the company in order to come to an agreement.\n" +
                "\nUser accounts\n" +
                "As a user of this application, you may be asked to register with us and provide private information. You are responsible for ensuring the accuracy of this information, and you are responsible for maintaining the safety and security of your identifying information. You are also responsible for all activities that occur under your account or password.\n" +
                "If you think there are any possible issues regarding the security of your account on the application, inform us immediately so we may address them accordingly.\n" +
                "We reserve all rights to terminate accounts, edit or remove content and cancel orders at our sole discretion.\n" +
                " \n" +
                "Applicable law\n" +
                "By visiting this application, you agree that the laws of the Spain government, without regard to principles of conflict laws, will govern these terms and conditions, or any dispute of any sort that might come between Stubar and you, or its business partners and associates.\n" +
                "\nDisputes\n" +
                "Any dispute related in any way to your visit to this application or to products you purchase from us shall be arbitrated by state or federal court [location] and you consent to exclusive jurisdiction and venue of such courts.\n" +
                "\nIndemnification\n" +
                "You agree to indemnify Stubar and its affiliates and hold Stubar harmless against legal claims and demands that may arise from your use or misuse of our services. We reserve the right to select our own legal counsel.\n" +
                "\nLimitation on liability\n" +
                "Stubar is not liable for any damages that may occur to you as a result of your misuse of our application.\n" +
                "Stubar reserves the right to edit, modify, and change this Agreement at any time. We shall let our users know of these changes through electronic mail. This Agreement is an understanding between Stubar and the user, and this supersedes and replaces all prior agreements regarding the use of this application.");
        builder.setNegativeButton("Close", (dialogInterface, i) -> {
        });
        builder.show();
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

            newUser.setInstitution(adapter.getItemName(this.spInstitution.getSelectedItemPosition()));

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
