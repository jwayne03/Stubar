package com.example.stubar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.squareup.picasso.Picasso;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected FrameLayout frameLayout;
    private Context context;
    public TextView tvUsername, tvEmail;
    public ImageView ivProfileImage;
    public EditText edSearch;
    public ImageButton tbSearch;

    private DrawerLayout drawer;

    /**
     * Method that invokes the UI of the activity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = this;
        initView();
    }

    private void setUsernameInformation() {
        tvUsername.setText(Constants.USER_LOGGED.getUsername());
        tvEmail.setText(Constants.USER_LOGGED.getEmail());
        if (Constants.USER_LOGGED.getProfilePhoto() == null || Constants.USER_LOGGED.getProfilePhoto().equals("00000000-0000-0000-0000-000000000000"))
            ivProfileImage.setImageResource(R.drawable.ic_baseline_person_24);
        else
            Picasso.with(this).load(Constants.PROFILE_PHOTO_URL + Constants.USER_LOGGED.getIdUser() +
                    "/profilePhoto").into(ivProfileImage);
    }

    /**
     * Method that show the BottomNavigationBar where the user controls the main activities of the app
     *
     * @param rootView View
     * @param home     int
     */
    @SuppressLint("NonConstantResourceId")
    public void initBottomNavigation(View rootView, int home) {
        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.promotions:
                    startActivity(new Intent(getApplicationContext(), ListPromotions.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return false;
                case R.id.book:
                    startActivity(new Intent(getApplicationContext(), ListDocuments.class));
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

    @SuppressLint({"RtlHardcoded", "WrongConstant"})
    private void initView() {
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        frameLayout = findViewById(R.id.container);
        ivProfileImage = headerView.findViewById(R.id.image_profile);
        tvUsername = headerView.findViewById(R.id.txt_username);
        tvEmail = headerView.findViewById(R.id.txt_email);
        ImageButton tbIcon = findViewById(R.id.tbButton);
        tbSearch = findViewById(R.id.tbSearch);
        ImageButton tbCross = findViewById(R.id.tbCross);
        edSearch = findViewById(R.id.ptSearch);
        TextView tbTitle = findViewById(R.id.tbTitle);
        tbSearch.setVisibility(View.VISIBLE);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        tbIcon.setOnClickListener(view -> {
            drawer.openDrawer(Gravity.LEFT);
            setUsernameInformation();
        });

        tbSearch.setOnClickListener(view -> {
            edSearch.setVisibility(View.VISIBLE);
            tbCross.setVisibility(View.VISIBLE);
            tbSearch.setVisibility(View.GONE);
            tbTitle.setVisibility(View.GONE);
        });

        tbCross.setOnClickListener(view -> {
            edSearch.setVisibility(View.GONE);
            tbCross.setVisibility(View.GONE);
            tbSearch.setVisibility(View.VISIBLE);
            tbTitle.setVisibility(View.VISIBLE);
            edSearch.getText().clear();
        });

    }

    /**
     * Method when the user clicks back in the navigation drawer takes it back.
     */
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

    /**
     * Method that is encharged to the flow of the applicacion in the Navigation Drawer
     *
     * @param item MenuItem
     * @return boolean
     */
    @SuppressLint({"ResourceType", "QueryPermissionsNeeded"})
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.documents) {
            intent = new Intent(this, ListDocuments.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.restaurants) {
            intent = new Intent(this, ListPromotions.class);
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
        } else if (id == R.id.stucat) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.stu.cat"));
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}