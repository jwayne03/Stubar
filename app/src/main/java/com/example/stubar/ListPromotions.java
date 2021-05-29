package com.example.stubar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.example.stubar.model.offer.OfferAdapter;
import com.example.stubar.model.offer.OfferApiResponse;
import com.example.stubar.utils.constants.Constants;
import com.google.gson.Gson;

public class ListPromotions extends BaseActivity {
    private RecyclerView recyclerView;

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
        TextView tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText("PROMOTIONS");
        View rootView = getLayoutInflater().inflate(R.layout.activity_promotions, frameLayout);
        initBottomNavigation(rootView, R.id.promotions);
        recyclerView = findViewById(R.id.rvOffer);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        edSearch.addTextChangedListener(filterTextWatcher);
        showPromotions(false, "");
    }

    private final TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (edSearch.getText().length() == 0) {
                showPromotions(false, "");
            } else {
                showPromotions(true, edSearch.getText().toString());
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    };


    private void showPromotions(boolean isSearching, String searchText) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url;
        if (!isSearching)
            url = Constants.ALL_OFFERS_URL;
        else
            url = Constants.SEARCH_OFFER + searchText;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Gson gson = new Gson();
                    response = "{ \"offers\": " + response + "}";
                    Log.d("promotions", response);
                    OfferApiResponse offer = gson.fromJson(response, OfferApiResponse.class);
                    OfferAdapter adapter = new OfferAdapter(ListPromotions.this, offer);
                    recyclerView.setAdapter(adapter);
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