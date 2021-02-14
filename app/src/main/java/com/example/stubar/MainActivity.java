package com.example.stubar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) getGoogleCredentials(googleSignInAccount);
        else Log.d("onCreate: ", "nada");

        initBottomNavigation(rootView, R.id.home);
    }



    private void getGoogleCredentials(GoogleSignInAccount googleSignInAccount) {
        String name = googleSignInAccount.getDisplayName();
        String email = googleSignInAccount.getEmail();
        String person_id = googleSignInAccount.getId();
        Uri photo = googleSignInAccount.getPhotoUrl();
        String id = googleSignInAccount.getId();
        Log.d("onCreate: ", name + " " + email + " " + person_id + " " + photo);
    }
}