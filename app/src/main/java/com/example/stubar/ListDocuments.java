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
import com.example.stubar.model.document.DocumentAdapter;
import com.example.stubar.model.document.DocumentApiResponse;
import com.example.stubar.utils.constants.Constants;
import com.google.gson.Gson;

public class ListDocuments extends BaseActivity {
    private RecyclerView recyclerView;
    private TextView tbTitle;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText("DOCUMENTS");
        View rootView = getLayoutInflater().inflate(R.layout.activity_upload_file, frameLayout);
        initBottomNavigation(rootView, R.id.book);
        recyclerView = findViewById(R.id.rvOffer);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        edSearch.addTextChangedListener(filterTextWatcher);
        showDocuments(false, "");
    }

    private final TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (edSearch.getText().length() == 0) {
                showDocuments(false, "");
            } else {
                showDocuments(true, edSearch.getText().toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    };


    private void showDocuments(boolean isSearching, String searchText) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url;
        if (!isSearching)
            url = Constants.DOCUMENTS_URL;
        else
            url = Constants.SEARCH_DOCUMENT + searchText;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    // Log.d("flx", "RESPONSE: " + response);
                    Gson gson = new Gson();
                    response = "{ \"document\": " + response + "}";
                    Log.d("flx", response);
                    DocumentApiResponse documentApiResponse = gson.fromJson(response, DocumentApiResponse.class);
                    DocumentAdapter adapter = new DocumentAdapter(ListDocuments.this, documentApiResponse);
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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