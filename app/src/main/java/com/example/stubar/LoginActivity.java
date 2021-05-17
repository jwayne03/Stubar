package com.example.stubar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.user.User;
import com.example.stubar.model.user.UserApiResponse;
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
    private User newUser;
    private Button btnLogin, btnRegister;
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
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
        normalLogin();
        signInWithGoogle();
    }

    private void normalLogin() {
        btnLogin.setOnClickListener(v -> {
            checkAuthentication(v);
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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

    private void checkAuthentication(View view) {
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
                            getUserApi(response);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            Log.d("response", e.getMessage());
                        }
                    } else {
                        try {
                            String errorMessage = response.getString("error");
                            Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG);
                            snackbar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.orange));
                            snackbar.show();
                        } catch (JSONException e) {
                            Log.d("error", e.getMessage());
                        }
                    }
                },
                error -> Log.d("Authentication/Error", error.toString()));

        queue.add(postRequest);
    }


    private void getUserApi(JSONObject loginResponse) throws JSONException{
        uuid = loginResponse.getString("response");
        Constants.USER_LOGGED.setIdUser(uuid);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                Constants.USER_URL + Constants.USER_LOGGED.getIdUser().toString(),
                response -> {
                    Gson gson = new Gson();
                    response = "{ \"user\": " + response + "}";
                    Log.d("user", response);
                    UserApiResponse userApiResponse = gson.fromJson(response, UserApiResponse.class);
                    if(userApiResponse.getUser() != null) {
                        Constants.USER_LOGGED = userApiResponse.getUser();
                        Constants.USER_LOGGED.setIdUser(uuid);
                    }
                },
                error -> {
                    String msg = "Network error (timeout or disconnected)";
                    if (error.networkResponse != null) {
                        msg = "ERROR: " + error.networkResponse.statusCode;
                    }
                    Log.d("flx", msg);
                });
        queue.add(request);
    }
    }