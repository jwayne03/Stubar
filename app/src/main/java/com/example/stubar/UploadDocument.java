package com.example.stubar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class UploadDocument extends BaseActivity {

    private ImageView ivStubar;
    private Spinner spinnerGrade;
    private EditText edNameOfTheDocument, edNameOfTheGrade;
    private TextView tbTitle;
    private final String[] grades = {"1", "2", "3", "4", "5", "6", "7", "8"};

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_upload_offer, frameLayout);
        tbSearch.setVisibility(View.GONE);
        tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText("DOCUMENTS");

        this.ivStubar = findViewById(R.id.ivStubar);
        this.spinnerGrade = findViewById(R.id.spGrade);
        this.edNameOfTheDocument = findViewById(R.id.edNameDocument);
        this.edNameOfTheDocument = findViewById(R.id.edNameGrade);
        this.inflateSpinner();
    }

    private void inflateSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spGrade);
        ArrayAdapter<String> spinnerTopicAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, grades);
        spinnerTopicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTopicAdapter);
    }
}