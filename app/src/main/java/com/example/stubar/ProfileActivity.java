package com.example.stubar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.utils.constants.Constants;
import com.example.stubar.utils.decode.Decode;
import com.example.stubar.utils.serializer.LocalDateSerializer;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class ProfileActivity extends BaseActivity {
    private EditText edPass, edConfirmPass;
    private String image64;
    private ImageView ivProfile;
    private View rootView;


    /**
     * Method when the user clicks back in the navigation drawer takes it back.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
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
        rootView = getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);
        TextView tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText(R.string.my_profile);
        tbSearch.setVisibility(View.GONE);
        Button btnImage = findViewById(R.id.btnProfileImage);
        Button btnSaveChanges = findViewById(R.id.btnSaveChanges);
        edPass = findViewById(R.id.edPorfilePass);
        edConfirmPass = findViewById(R.id.edProfileConfirmPass);
        ivProfile = findViewById(R.id.ivPorfileImage);

        btnImage.setOnClickListener(view -> selectImage());

        btnSaveChanges.setOnClickListener(view -> saveChangesAlert());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    /**
     * Method to load the image of the user
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap thumbnail = null;
            if (Build.VERSION.SDK_INT >= 29) {
                Uri imageUri = data.getData();
                ivProfile.setBackgroundResource(android.R.color.transparent);
                ivProfile.setImageURI(imageUri);
                try (ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(imageUri, "r")) {
                    if (pfd != null)
                        thumbnail = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (thumbnail != null) image64 = Decode.bitMapToString(thumbnail);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateUser() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(Constants.USER_LOGGED).replaceAll("[^\\x00-\\x7F]", ""));
            Log.d("jsonUser", "updateUser: " + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Constants.UPDATE_USER, jsonObject,
                response -> Log.d("Response", response.toString()),
                error -> Log.d("Error.Response", error.toString()));
        queue.add(postRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveChangesAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("IMPORTANT!");
        builder.setMessage("By clicking 'YES' you will accept all the changes. Are you sure?");
        builder.setPositiveButton("YES", (dialogInterface, i) -> {

            if (!edPass.getText().toString().isEmpty() && !edConfirmPass.getText().toString().isEmpty()) {
                if (edPass.getText().toString().trim().equals(edConfirmPass.getText().toString().trim())) {
                    Constants.USER_LOGGED.setPassword(edPass.getText().toString());
                    if (image64 != null)
                        Constants.USER_LOGGED.setProfilePhoto(image64);
                    updateUser();
                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(rootView, "ERROR! Passwords do not match.", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(ProfileActivity.this, R.color.orange));
                    snackbar.show();
                }
            } else {
                Snackbar snackbar = Snackbar.make(rootView, "ERROR! Some fields are empty. Fill them and try it again.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(ProfileActivity.this, R.color.orange));
                snackbar.show();
            }
        });
        builder.setNegativeButton("NO", (dialogInterface, i) -> {
        });
        builder.show();
    }
}