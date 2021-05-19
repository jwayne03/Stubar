package com.example.stubar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class UploadDocument extends AppCompatActivity {

    private ImageView ivStubar;
    private Spinner spinnerGrade;
    private EditText edNameOfTheDocument;
    private EditText edNameOfTheGrade;
    private final String[] grades = {"1", "2", "3", "4", "5", "6", "7", "8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        setTitle("Upload file");

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