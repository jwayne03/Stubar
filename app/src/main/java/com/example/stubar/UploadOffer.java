package com.example.stubar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.document.DocumentAdapter;
import com.example.stubar.model.document.DocumentApiResponse;
import com.example.stubar.model.offer.Offer;
import com.example.stubar.utils.constants.Constants;
import com.example.stubar.utils.serializer.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

/**
 *      Ofertas:
 *      <p>
 *      - Comment
 *      - Image
 *      - Price
 *      - Local (Spinner)
 */
public class UploadOffer extends BaseActivity{
    Button btnImage, btnInsertOffer;
    EditText edOfferComment, edOfferPrice;
    TextView tbTitle;
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
        tbTitle.setText("OFFERS");
        btnImage = findViewById(R.id.btnUploadImage);
        edOfferComment = findViewById(R.id.edOfferComment);
        edOfferPrice= findViewById(R.id.edOfferPrice);
        btnInsertOffer = findViewById(R.id.btnInsertOffer);

        btnInsertOffer.setOnClickListener(view -> {
            insertOffer();
            Intent intent = new Intent(UploadOffer.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertOffer() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                    .create();

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(gson.toJson(offerObject()));
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
        offer.setImageOffer(null);
        offer.setDate(LocalDate.now());
        return offer;
    }

    private String encodeImageBase64(int stubarlogo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), stubarlogo);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}