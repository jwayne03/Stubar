package com.example.stubar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.offer.OfferAdapter;
import com.example.stubar.model.offer.OfferApiResponse;
import com.google.gson.Gson;

public class Promotions extends BaseActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.activity_promotions, frameLayout);
        initBottomNavigation(rootView, R.id.promotions);
        recyclerView = findViewById(R.id.rvDocument);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showPromotions();
    }


    private void showPromotions() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://46.101.46.166:8080/stuapi/api/offer";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log.d("flx", "RESPONSE: " + response);
                        Gson gson = new Gson();
                        response = "{ \"offers\": " + response + "}";
                        Log.d("flx", response);
                        OfferApiResponse offer = gson.fromJson(response, OfferApiResponse.class);
                        OfferAdapter adapter = new OfferAdapter(Promotions.this, offer);
                        recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String msg = "Network error (timeout or disconnected)";
                        if (error.networkResponse != null) {
                            msg = "ERROR: " + error.networkResponse.statusCode;
                        }
                        Log.d("flx", msg);
                    }
                });
        queue.add(request);

    }
}