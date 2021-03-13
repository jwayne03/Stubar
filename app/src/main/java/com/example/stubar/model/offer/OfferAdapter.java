package com.example.stubar.model.offer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stubar.R;

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
        Offer offer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }

        public void setOffer(Offer offer) {
            this.offer = offer;
            tvDescription.setText(offer.getComment());
            tvPrice.setText(String.valueOf(offer.getPrice()));
        }
    }
}
