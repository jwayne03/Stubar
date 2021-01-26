package com.example.stubar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            getGoogleCredentials(googleSignInAccount);
        } else {
            Log.d("onCreate: ", "nada");
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.promotions:
                        startActivity(new Intent(getApplicationContext(), Promotions.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return false;
                    case R.id.book:
                        startActivity(new Intent(getApplicationContext(), UploadFile.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return false;
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return false;
                    case R.id.home:
                }
                return false;
            }
        });
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