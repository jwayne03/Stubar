package com.example.stubar;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.document.DocumentAdapter;
import com.example.stubar.model.document.DocumentApiResponse;
import com.example.stubar.model.offer.OfferAdapter;
import com.example.stubar.model.offer.OfferApiResponse;
import com.example.stubar.utils.constants.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class MainActivity extends BaseActivity {
    private RecyclerView rvOffer, rvDocument;
    private TextView tbTitle, tvEmptyOffer, tvEmptyDocument;
    private FloatingActionButton fbAdd, fbDoc, fbPro, fbDel;

    /**
     * Method that invokes the UI of the activity
     *
     * @param savedInstanceState Bundle
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText("MY CREATIONS");
        View rootView = getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        initBottomNavigation(rootView, R.id.home);
        rvOffer = findViewById(R.id.rvOffer);
        rvOffer.setLayoutManager(new LinearLayoutManager(this));
        rvDocument = findViewById(R.id.rvDocument);
        rvDocument.setLayoutManager(new LinearLayoutManager(this));
        tvEmptyOffer = findViewById(R.id.tvEmptyOffer);
        tvEmptyDocument = findViewById(R.id.tvEmptyDocument);
        fbAdd = findViewById(R.id.fbAdd);
        fbDoc = findViewById(R.id.fbDocuments);
        fbPro = findViewById(R.id.fbPromotions);
        fbDel = findViewById(R.id.fbClose);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) getGoogleCredentials(googleSignInAccount);
        else Log.d("onCreate: ", "nada");

        fbAdd.setOnClickListener(view -> {
            fbDel.setVisibility(View.VISIBLE);
            fbAdd.setVisibility(View.GONE);
            fbDoc.setVisibility(View.VISIBLE);
            fbPro.setVisibility(View.VISIBLE);
        });

        fbDoc.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UploadDocument.class);
            startActivity(intent);
            finish();
        });

        fbPro.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UploadOffer.class);
            startActivity(intent);
            finish();
        });

        fbDel.setOnClickListener(view -> {
            fbDel.setVisibility(View.GONE);
            fbAdd.setVisibility(View.VISIBLE);
            fbDoc.setVisibility(View.INVISIBLE);
            fbPro.setVisibility(View.INVISIBLE);

        });

        edSearch.setVisibility(View.GONE);
        tbSearch.setVisibility(View.GONE);
        showPromotions();
        showDocuments();
    }

    private void showDocuments() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(
                Request.Method.GET, Constants.USER_DOCUMENTS_URL + Constants.USER_LOGGED.getIdUser().toString(),
                response -> {
                    // Log.d("flx", "RESPONSE: " + response);
                    Gson gson = new Gson();
                    response = "{ \"document\": " + response + "}";
                    Log.d("main documents", response);
                    DocumentApiResponse documentApiResponse = gson.fromJson(response, DocumentApiResponse.class);
                    if (documentApiResponse.getDocuments().size() != 0) {
                        DocumentAdapter adapter = new DocumentAdapter(MainActivity.this, documentApiResponse);
                        rvDocument.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
                        rvDocument.setAdapter(adapter);
                    } else {
                        tvEmptyDocument.setVisibility(View.VISIBLE);
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

    private void showPromotions() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.GET, Constants.USER_OFFERS_URL + Constants.USER_LOGGED.getIdUser().toString(),
                response -> {
                    // Log.d("flx", "RESPONSE: " + response);
                    Gson gson = new Gson();
                    response = "{ \"offers\": " + response + "}";
                    Log.d("main promotions", response);
                    OfferApiResponse offer = gson.fromJson(response, OfferApiResponse.class);
                    if (offer.getOffers().size() != 0) {
                        OfferAdapter adapter = new OfferAdapter(MainActivity.this, offer);
                        rvOffer.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
                        rvOffer.setAdapter(adapter);
                    } else {
                        tvEmptyOffer.setVisibility(View.VISIBLE);
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

    private void getGoogleCredentials(GoogleSignInAccount googleSignInAccount) {
        String name = googleSignInAccount.getDisplayName();
        String email = googleSignInAccount.getEmail();
        String person_id = googleSignInAccount.getId();
        Uri photo = googleSignInAccount.getPhotoUrl();
        String id = googleSignInAccount.getId();
        Log.d("onCreate: ", name + " " + email + " " + person_id + " " + photo + " " + id);
    }
}