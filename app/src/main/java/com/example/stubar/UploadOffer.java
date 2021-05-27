package com.example.stubar;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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
import com.example.stubar.model.local.Local;
import com.example.stubar.model.local.LocalAdapterSpinner;
import com.example.stubar.model.local.LocalResponseArray;
import com.example.stubar.model.offer.Offer;
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

public class UploadOffer extends BaseActivity {
    private Button btnImage, btnInsertOffer;
    private EditText edOfferComment, edOfferPrice;
    private TextView tbTitle;
    private Spinner nameOfLocalSpinner;
    private String image64;
    private View rootView;
    private ImageView ivOffer;
    private final String[] projection = new String[]{
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.MIME_TYPE,
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UploadOffer.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = getLayoutInflater().inflate(R.layout.activity_upload_offer, frameLayout);
        tbSearch.setVisibility(View.GONE);
        tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText(R.string.offers);
        btnImage = findViewById(R.id.btnUploadImage);
        edOfferComment = findViewById(R.id.edOfferComment);
        edOfferPrice = findViewById(R.id.edOfferPrice);
        btnInsertOffer = findViewById(R.id.btnInsertOffer);
        nameOfLocalSpinner = findViewById(R.id.spOffer);
        ivOffer = findViewById(R.id.ivOffer);

        this.setLocalSpinner();

        btnInsertOffer.setOnClickListener(view -> {
            if (edOfferPrice.getText() == null || edOfferPrice.getText().toString().trim().isEmpty()) {
                Snackbar snackbar = Snackbar.make(rootView, "Some fields are empty. Fill them and try it again.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(UploadOffer.this, R.color.orange));
                snackbar.show();
            } else if (edOfferComment.getText() == null || edOfferComment.getText().toString().trim().isEmpty()) {
                Snackbar snackbar = Snackbar.make(rootView, "Some fields are empty. Fill them and try it again.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(UploadOffer.this, R.color.orange));
                snackbar.show();
            } else if (nameOfLocalSpinner.getSelectedItemPosition() == 0) {
                Snackbar snackbar = Snackbar.make(rootView, "Some fields are empty. Fill them and try it again.", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(UploadOffer.this, R.color.orange));
                snackbar.show();
            } else {
                insertOffer();
                Intent intent = new Intent(UploadOffer.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnImage.setOnClickListener(view -> selectImage());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertOffer() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(offerObject()).replaceAll("[^\\x00-\\x7F]", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, Constants.INSERT_OFFER, jsonObject,
                response -> Log.d("Response", response.toString()),
                error -> Log.d("Error.Response", error.toString()));
        queue.add(putRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Object offerObject() {
        Offer offer = new Offer();
        offer.setComment(edOfferComment.getText().toString().trim());
        offer.setPrice(Double.parseDouble(edOfferPrice.getText().toString().trim()));

        LocalAdapterSpinner adapter = (LocalAdapterSpinner) this.nameOfLocalSpinner.getAdapter();
        offer.setLocal(adapter.getItemName(this.nameOfLocalSpinner.getSelectedItemPosition()));

        offer.setUserID(Constants.USER_LOGGED.getIdUser().toString().trim());
        offer.setImageOffer(image64);
        offer.setDate(LocalDate.now());
        return offer;
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap thumbnail = null;
            if (Build.VERSION.SDK_INT >= 29) {
                Uri imageUri = data.getData();
                ivOffer.setBackgroundResource(android.R.color.transparent);
                ivOffer.setImageURI(imageUri);
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

    private void setLocalSpinner() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constants.LOCAL_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new Gson();
            LocalResponseArray localResponseArray = gson
                    .fromJson((Decode.decodeUTF8(response)), LocalResponseArray.class);

            if (localResponseArray.size() == 0) {
                nameOfLocalSpinner.setVisibility(View.GONE);

            } else {
                localResponseArray.add(0, new Local());
                nameOfLocalSpinner.setAdapter(new LocalAdapterSpinner(this, localResponseArray));
            }

        }, error -> {
            Log.d("ERROR", "Error downloading institutions");
        });
        requestQueue.add(stringRequest);
    }
}