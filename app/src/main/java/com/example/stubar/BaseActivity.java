package com.example.stubar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.stubar.utils.constants.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected FrameLayout frameLayout;
    private Context context;
    public TextView tvUsername, tvEmail;
    public ImageView image_profile;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = this;
        initView();
        setUsernameInformation();
    }


    private void setUsernameInformation() {
        tvUsername.setText(Constants.USER_LOGGED.getUsername());
        tvEmail.setText(Constants.USER_LOGGED.getEmail());
        //todo implement image
    }

    @SuppressLint("NonConstantResourceId")
    public void initBottomNavigation(View rootView, int home) {
        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return false;
            }
            return false;
        });

    }

    @SuppressLint("RtlHardcoded")
    private void initView() {
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        image_profile = headerview.findViewById(R.id.image_profile);
        image_profile.setImageResource(R.drawable.alarm);
        tvUsername = headerview.findViewById(R.id.txt_username);
        tvEmail =  headerview.findViewById(R.id.txt_email);
        frameLayout = findViewById(R.id.container);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ImageButton tbIcon = findViewById(R.id.tbButton);
        tbIcon.setOnClickListener(v -> drawer.openDrawer(Gravity.LEFT));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            ExitApp();
        }
    }

    private void ExitApp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Exit?");
        builder.setMessage("Do You Want To Exit?");

        //final AlertDialog dialog = builder.create();
        builder.setPositiveButton("YES", (dialogInterface, i) -> finish());
        builder.setNegativeButton("NO", (dialogInterface, i) -> {

        });
        builder.show();
    }

    @SuppressLint("ResourceType")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.documents) {
            intent = new Intent(this, UploadFile.class);
            startActivity(intent);
            finish();
            // Handle the camera action
        } else if (id == R.id.restaurants) {
            intent = new Intent(this, Promotions.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.logOut) {
            Constants.USER_LOGGED = null;
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.edProfileNav) {
            intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.maps) {
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
