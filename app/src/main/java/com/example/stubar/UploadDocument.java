package com.example.stubar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.document.Document;
import com.example.stubar.model.offer.Offer;
import com.example.stubar.utils.constants.Constants;
import com.example.stubar.utils.serializer.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class UploadDocument extends BaseActivity {

    private ImageView ivStubar;
    private Spinner spinnerGrade;
    private EditText edNameOfTheDocument, edNameOfTheGrade;
    private TextView tbTitle;
    private Button btnInsertDocument;
    private final String[] grades = {"1", "2", "3", "4", "5", "6", "7", "8"};

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UploadDocument.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_upload_document, frameLayout);
        tbSearch.setVisibility(View.GONE);
        tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText("DOCUMENTS");

        this.ivStubar = findViewById(R.id.ivStubar);
        this.spinnerGrade = findViewById(R.id.spGrade);
        this.edNameOfTheDocument = findViewById(R.id.edNameDocument);
        this.edNameOfTheGrade = findViewById(R.id.edNameGrade);
        this.btnInsertDocument = findViewById(R.id.btnInsertDocument);
        this.inflateSpinner();

        btnInsertDocument.setOnClickListener(view -> {
            insertDocument();
            Intent intent = new Intent(UploadDocument.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void inflateSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spGrade);
        ArrayAdapter<String> spinnerTopicAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, grades);
        spinnerTopicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTopicAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertDocument() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(documentObject()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, Constants.INSERT_DOCUMENT, jsonObject,
                response -> Log.d("Response", response.toString()),
                error -> Log.d("Error.Response", error.toString()));
        queue.add(putRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Document documentObject() {
        Document document = new Document();
        document.setName(edNameOfTheDocument.getText().toString().trim());
        document.setGrade(Integer.parseInt(spinnerGrade.getSelectedItem().toString().trim()));
        document.setDocPath(null);
        document.setTopicID("effffc25-8434-11eb-9ac4-06a55b230c35");
        document.setTopicName(null);
        document.setUsername(Constants.USER_LOGGED.getUsername().trim());
        document.setUserID(Constants.USER_LOGGED.getIdUser().toString().trim());
        document.setDate(LocalDate.now());
        return document;
    }

}