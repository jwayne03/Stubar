package com.example.stubar;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import android.util.Base64;

public class UploadDocument extends BaseActivity implements Runnable {

    private ImageView ivStubar;
    private Spinner spinnerGrade;
    private Spinner spinnerStudy;
    private EditText edNameOfTheDocument;
    private static final int PICK_PDF_FILE = 2;
    private View rootView;
    private String base64File;
    private Button btnUploadDocument, btnInsertDocument;
    private ProgressBar pbDocument;
    private int stepCounter;
    private Handler handler;

    private final String[] grades = {"Select Grade","1", "2", "3", "4", "5", "6", "7", "8"};

    /**
     * Method when the user clicks back in the navigation drawer takes it back.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UploadDocument.this, ListDocuments.class);
        startActivity(intent);
        finish();
    }

    /**
     * Method that invokes the UI of the activity
     *
     * @param savedInstanceState Bundle
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.rootView = getLayoutInflater().inflate(R.layout.activity_upload_document, frameLayout);
        tbSearch.setVisibility(View.GONE);
        TextView tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText(R.string.documents_upper);

        this.ivStubar = findViewById(R.id.ivDocument);
        this.spinnerGrade = findViewById(R.id.spGrade);
        this.spinnerStudy = findViewById(R.id.spStudy);
        this.edNameOfTheDocument = findViewById(R.id.edNameDocument);
        this.btnInsertDocument = findViewById(R.id.btnInsertDocument);
        this.btnUploadDocument = findViewById(R.id.btnUploadDocument);
        this.pbDocument = findViewById(R.id.pbDocument);
        this.handler = new Handler();
        this.inflateSpinner();
        this.setTopicSpinner();

        btnInsertDocument.setOnClickListener(view -> {
            if (edNameOfTheDocument.getText() == null || edNameOfTheDocument.getText().toString().trim().isEmpty()) {
                Snackbar snackbar = Snackbar.make(rootView, "Some fields are empty. Fill them and try it again.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(UploadDocument.this, R.color.orange));
                snackbar.show();
            } else if (spinnerStudy.getSelectedItemPosition() == 0) {
                Snackbar snackbar = Snackbar.make(rootView, "Some fields are empty. Fill them and try it again.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(UploadDocument.this, R.color.orange));
                snackbar.show();
            } else if (spinnerGrade.getSelectedItemPosition() == 0) {
                Snackbar snackbar = Snackbar.make(rootView, "Some fields are empty. Fill them and try it again.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(UploadDocument.this, R.color.orange));
                snackbar.show();
            } else if (base64File == null) {
                Snackbar snackbar = Snackbar.make(rootView, "Some fields are empty. Fill them and try it again.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(UploadDocument.this, R.color.orange));
                snackbar.show();
            } else {
                insertDocument();
                loadDashboard();
            }
        });

        btnUploadDocument.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, PICK_PDF_FILE);
        });
    }

    private void inflateSpinner() {
        Spinner spinner = findViewById(R.id.spGrade);
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
        }, error -> Log.d("ERROR", "Error downloading institutions"));
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
        document.setUsername(Constants.USER_LOGGED.getUsername());
        document.setDocPath(this.base64File);

        TopicAdapterSpinner adapter = (TopicAdapterSpinner) this.spinnerStudy.getAdapter();
        document.setTopicID(adapter.getItemName(this.spinnerStudy.getSelectedItemPosition()));

        document.setUsername(Constants.USER_LOGGED.getUsername().trim());
        document.setUserID(Constants.USER_LOGGED.getIdUser().toString().trim());
        document.setDate(LocalDate.now());

        return document;
    }


    /**
     * Method to upload a document
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("func", "onActivityResult: " + resultCode);
            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                Uri uri = data.getData();

                this.btnUploadDocument.setText("File selected");

                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                    int read;
                    byte[] byteArray = new byte[16384];

                    while ((read = inputStream.read(byteArray, 0, byteArray.length)) != -1) {
                        buffer.write(byteArray, 0, read);
                    }

                    base64File = Base64.encodeToString(buffer.toByteArray(), Base64.DEFAULT);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Snackbar snackbar = Snackbar.make(rootView, "Error selecting documents", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(UploadDocument.this, R.color.orange));
                snackbar.show();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadDashboard() {
        if (stepCounter == 0) {
            // First step
            pbDocument.setVisibility(View.VISIBLE);
            stepCounter++;
            handler.postDelayed( this, 2000);
        }
    }

    public void run() {
        if (stepCounter == 1) {
            Intent intent = new Intent(UploadDocument.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}