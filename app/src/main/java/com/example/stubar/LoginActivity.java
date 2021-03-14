package com.example.stubar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.user.User;
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

public class LoginActivity extends AppCompatActivity {

    private final int RC_SIGN_IN = 0;
    private EditText edUsername, edPassword;

    private Button btnLogin, btnRegister;
    private SignInButton signInWithGoogle;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edUsernameLogin);
        edPassword = findViewById(R.id.edPasswordLogin);

        btnLogin = findViewById(R.id.btnLogin);
        signInWithGoogle = findViewById(R.id.sign_in_button);
        signInWithGoogle.setSize(SignInButton.SIZE_STANDARD);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
        normalLogin();
        signInWithGoogle();
    }

    private void normalLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAuthentication(v);
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
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

    private void checkAuthentication(View v) {
        User newUser = new User();
        newUser.setUsername(edUsername.getText().toString().trim());
        newUser.setPassword(edPassword.getText().toString().trim());


        RequestQueue queue = Volley.newRequestQueue(this);

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
                            String uuid = response.getString("response");
                            newUser.setIdUser(uuid);
                            Constants.USER_LOGGED = newUser;

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            Log.d("response", e.getMessage());
                        }
                    } else {
                        try {
                            String errorMessage = response.getString("error");
                            Snackbar.make(v, errorMessage, Snackbar.LENGTH_LONG)
                                    .show();
                        } catch (JSONException e) {
                            Log.d("error", e.getMessage());
                        }
                    }
                },
                error -> Log.d("Authentication/Error", error.toString()));

        queue.add(postRequest);

    }
}