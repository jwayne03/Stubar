package com.example.stubar.model.offer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stubar.R;
import com.example.stubar.model.local.Local;
import com.example.stubar.model.local.LocalApiResponse;
import com.example.stubar.utils.constants.Constants;
import com.google.gson.Gson;

public class OfferAdapter extends RecyclerView.Adapter<com.example.stubar.model.offer.OfferAdapter.ViewHolder> {
    private Context context;
    private OfferApiResponse offer;

    public OfferAdapter(Context context, OfferApiResponse offer) {
        this.context = context;
        this.offer = offer;
    }

    @NonNull
    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_promotions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.ViewHolder holder, int position) {
        holder.setOffer(offer.getOffers().get(position));
    }

    @Override
    public int getItemCount() {
        return offer.getOffers().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvPrice;
        Offer offers;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    getLocal(offer.getOffers().get(getAdapterPosition()).getLocalID().toString());
                }
            });
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }

        public void setOffer(Offer offer) {
            this.offers = offer;
            tvDescription.setText(offer.getComment());
            tvPrice.setText(String.valueOf(offer.getPrice()));
        }
    }


    private void getLocal(String localId) {
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

                    Uri gmmIntentUri = Uri.parse("geo:" + local.getGeolat()+ "," +
                            local.getGeolong() + "?q="+ local.getName());
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
