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
import com.example.stubar.model.document.DocumentAdapter;
import com.example.stubar.model.document.DocumentApiResponse;
import com.example.stubar.utils.constants.Constants;
import com.google.gson.Gson;

public class UploadFile extends BaseActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Documents");
        View rootView = getLayoutInflater().inflate(R.layout.activity_upload_file, frameLayout);
        initBottomNavigation(rootView, R.id.book);
        recyclerView = findViewById(R.id.rvDocument);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showDocuments();

    }

    private void showDocuments() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                Constants.DOCUMENTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log.d("flx", "RESPONSE: " + response);
                        Gson gson = new Gson();
                        response = "{ \"document\": " + response + "}";
                        Log.d("flx", response);
                        DocumentApiResponse documentApiResponse = gson.fromJson(response, DocumentApiResponse.class);
                        DocumentAdapter adapter = new DocumentAdapter(UploadFile.this, documentApiResponse);
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