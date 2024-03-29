package com.example.stubar.utils.api;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.model.local.Local;
import com.example.stubar.model.local.LocalApiResponse;
import com.example.stubar.model.user.User;
import com.example.stubar.model.user.UserApiResponse;
import com.example.stubar.utils.constants.Constants;
import com.example.stubar.utils.decode.Decode;
import com.example.stubar.utils.serializer.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class Requests {

    /**
     * Method to call to the API and get all the information of the user
     *
     * @param loginResponse JSONObject
     * @param context       Context
     * @throws JSONException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getUserApi(JSONObject loginResponse, Context context) throws JSONException {
        String uuid = loginResponse.getString("response");
        Constants.USER_LOGGED.setIdUser(uuid);
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET,
                Constants.USER_URL + Constants.USER_LOGGED.getIdUser().toString(), response -> {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).create();
            response = "{ \"user\": " + response + "}";
            User user = gson.fromJson(response, UserApiResponse.class).getUser();
            user.setBirthday(null);
            if (user != null) {
                Constants.USER_LOGGED = user;
                Constants.USER_LOGGED.setIdUser(uuid);
            }
        }, error -> {
            String msg = "Network error (timeout or disconnected)";
            if (error.networkResponse != null) {
                msg = "ERROR: " + error.networkResponse.statusCode;
            }
            Log.d("flx", msg);
        });
        queue.add(request);
    }


    /**
     * Method to call to the API to get all the Local
     *
     * @param localId String
     * @param context Context
     */
    public void getLocal(String localId, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                Constants.LOCAL_URL + localId,
                response -> {
                    // Log.d("flx", "RESPONSE: " + response);
                    Gson gson = new Gson();
                    response = "{ \"local\": " + response + "}";
                    Log.d("local", response);
                    Local local = gson.fromJson(response, LocalApiResponse.class).getLocal();

                    Uri gmmIntentUri = Uri.parse("geo:" + local.getGeolat() + "," +
                            local.getGeolong() + "?q=" + Decode.decodeUTF8(local.getName()));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);
                },
                error -> {
                    String msg = "Network error (timeout or disconnected)";
                    if (error.networkResponse != null) {
                        msg = "ERROR: " + error.networkResponse.statusCode;
                    }
                    Log.d("flx", msg);
                });
        queue.add(request);
    }
}