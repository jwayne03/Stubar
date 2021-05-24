package com.example.stubar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.user.User;
import com.example.stubar.utils.api.Requests;
import com.example.stubar.utils.constants.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private final int RC_SIGN_IN = 0;
    private EditText edUsername, edPassword;
    private User newUser;
    private Button btnLogin;
    private TextView tvBtnRegister;
    private SignInButton signInWithGoogle;
    private GoogleSignInClient googleSignInClient;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edUsernameLogin);
        edPassword = findViewById(R.id.edPasswordLogin);

        btnLogin = findViewById(R.id.btnLogin);
        signInWithGoogle = findViewById(R.id.sign_in_button);
        signInWithGoogle.setSize(SignInButton.SIZE_STANDARD);

        tvBtnRegister = findViewById(R.id.btnRegister);

        tvBtnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
        normalLogin();
        signInWithGoogle();
    }

    private void normalLogin() {
        btnLogin.setOnClickListener(this::checkAuthentication);
    }

    private void signInWithGoogle() {
        signInWithGoogle.setOnClickListener(view -> {
            if (view.getId() == R.id.sign_in_button) signIn();
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkAuthentication(View view) {
        Requests requests = new Requests();
        newUser = new User();
        RequestQueue queue = Volley.newRequestQueue(this);
        newUser.setUsername(edUsername.getText().toString().trim());
        newUser.setPassword(edPassword.getText().toString().trim());
        Constants.USER_LOGGED = newUser;

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(newUser));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Constants.AUTHENTICATION_USER_URL, jsonObject,
                response -> {
                    if (response.has("response")) {
                            try {
                                requests.getUserApi(response, LoginActivity.this);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    } else {
                        try {
                            String errorMessage = response.getString("error");
                            Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG);
                            snackbar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.orange));
                            snackbar.show();
                        } catch (JSONException e) {
                            Log.d("error", Objects.requireNonNull(e.getMessage()));
                        }
                    }
                },
                error -> Log.d("Authentication/Error", error.toString()));

        queue.add(postRequest);
    }
}
