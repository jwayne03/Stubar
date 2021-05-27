package com.example.stubar;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.document.Document;
import com.example.stubar.model.topic.Topic;
import com.example.stubar.model.topic.TopicAdapterSpinner;
import com.example.stubar.model.topic.TopicResponse;
import com.example.stubar.utils.constants.Constants;
import com.example.stubar.utils.decode.Decode;
import com.example.stubar.utils.serializer.LocalDateSerializer;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;

public class UploadDocument extends BaseActivity {

    private ImageView ivStubar;
    private Spinner spinnerGrade;
    private Spinner spinnerStudy;
    private EditText edNameOfTheDocument;
    private TextView tbTitle;
    private Button btnInsertDocument, btnUploadDocument;
    private static final int PICK_PDF_FILE = 2;
    private View rootView;
    private String base64File;

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
        this.rootView = getLayoutInflater().inflate(R.layout.activity_upload_document, frameLayout);
        tbSearch.setVisibility(View.GONE);
        tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText(R.string.documents_upper);

        this.ivStubar = findViewById(R.id.ivDocument);
        this.spinnerGrade = findViewById(R.id.spGrade);
        this.spinnerStudy = findViewById(R.id.spStudy);
        this.edNameOfTheDocument = findViewById(R.id.edNameDocument);
        this.btnInsertDocument = findViewById(R.id.btnInsertDocument);
        this.btnUploadDocument = findViewById(R.id.btnUploadDocument);
        this.inflateSpinner();
        this.setTopicSpinner();

        btnInsertDocument.setOnClickListener(view -> {
            insertDocument();
            Intent intent = new Intent(UploadDocument.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnUploadDocument.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, PICK_PDF_FILE);


        });
    }

    private void inflateSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spGrade);
        ArrayAdapter<String> spinnerTopicAdapter;
        spinnerTopicAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, grades);
        spinnerTopicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTopicAdapter);
    }

    private void setTopicSpinner() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constants.GET_ALL_TOPICS;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {

            Gson gson = new Gson();
            TopicResponse topicResponse = gson
                    .fromJson((Decode.decodeUTF8(response)), TopicResponse.class);


            if (topicResponse.size() == 0) {
                this.spinnerStudy.setVisibility(View.GONE);
            } else {
                topicResponse.add(0, new Topic());
                this.spinnerStudy.setAdapter(new TopicAdapterSpinner(this, topicResponse));
            }
        }, error -> {
            Log.d("ERROR", "Error downloading institutions");
        });
        requestQueue.add(stringRequest);
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
        document.setDocPath(this.base64File);

        TopicAdapterSpinner adapter = (TopicAdapterSpinner) this.spinnerStudy.getAdapter();
        document.setTopicID(adapter.getItemName(this.spinnerStudy.getSelectedItemPosition()));

        document.setUsername(Constants.USER_LOGGED.getUsername().trim());
        document.setUserID(Constants.USER_LOGGED.getIdUser().toString().trim());
        document.setDate(LocalDate.now());

        return document;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("func", "onActivityResult: " + resultCode);
            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                Uri uri = data.getData();

                File file = new File(uri.getPath());

                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images`.Media.DATA);
                cursor.moveToFirst();
                String s = cursor.getString(column_index);
                cursor.close();
                Log.d("COSAS", "onActivityResult: " + s);


//                byte[] fileContent = new byte[0];
//                try {
//                    fileContent = Files.readAllBytes(Paths.get(myFile.getAbsolutePath()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                base64File = Base64.getEncoder().encodeToString(fileContent);

            } else {
                Snackbar snackbar = Snackbar.make(rootView, "Error selecting documents", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(UploadDocument.this, R.color.orange));
                snackbar.show();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
}