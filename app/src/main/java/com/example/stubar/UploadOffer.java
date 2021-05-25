package com.example.stubar;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.local.Local;
import com.example.stubar.model.local.LocalAdapter;
import com.example.stubar.model.local.LocalAdapterSpinner;
import com.example.stubar.model.local.LocalResponseArray;
import com.example.stubar.model.offer.Offer;
import com.example.stubar.utils.constants.Constants;
import com.example.stubar.utils.serializer.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Ofertas:
 * <p>
 * - Comment
 * - Image
 * - Price
 * - Local (Spinner)
 */
public class UploadOffer extends BaseActivity {
    Button btnImage, btnInsertOffer;
    EditText edOfferComment, edOfferPrice;
    TextView tbTitle;
    Spinner nameOfLocalSpinner;
    private String image64;
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
        getLayoutInflater().inflate(R.layout.activity_upload_offer, frameLayout);
        tbSearch.setVisibility(View.GONE);
        tbTitle = findViewById(R.id.tbTitle);
        tbTitle.setText(R.string.offers);
        btnImage = findViewById(R.id.btnUploadImage);
        edOfferComment = findViewById(R.id.edOfferComment);
        edOfferPrice = findViewById(R.id.edOfferPrice);
        btnInsertOffer = findViewById(R.id.btnInsertOffer);
        nameOfLocalSpinner = findViewById(R.id.spOffer);
        this.setOfferSpinner();

        btnInsertOffer.setOnClickListener(view -> {
            insertOffer();
            Intent intent = new Intent(UploadOffer.this, MainActivity.class);
            startActivity(intent);
            finish();
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
        offer.setLocal("57e28428-7110-11eb-91d0-06a55b230c35");
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
                try (ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(imageUri, "r")) {
                    if (pfd != null) {
                        thumbnail = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (thumbnail != null) image64 = BitMapToString(thumbnail);
            Log.d("length", "onActivityResult: " + image64.length());
        }
    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void setOfferSpinner() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constants.LOCAL_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new Gson();
            LocalResponseArray localResponseArray = gson.fromJson(String.valueOf(response), LocalResponseArray.class);
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