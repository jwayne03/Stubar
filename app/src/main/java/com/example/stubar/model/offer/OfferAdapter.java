package com.example.stubar.model.offer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stubar.R;
import com.example.stubar.utils.api.Requests;
import com.example.stubar.utils.decode.Decode;

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
        ConstraintLayout cnstLayFront, cnstLayBack;
        TextView tvLocalName, tvComment, tvGoTo, tvOfferPrice;
        ImageView ivBackground;
        ImageButton btnDownloadOffer;
        Offer offers;

        boolean clicked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cnstLayFront = itemView.findViewById(R.id.cnstLayFront);
            cnstLayBack = itemView.findViewById(R.id.cnstLayBack);
            tvLocalName = itemView.findViewById(R.id.tvLocalName);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvOfferPrice = itemView.findViewById(R.id.tvOfferPrice);
            tvGoTo = itemView.findViewById(R.id.tvGoTo);
            ivBackground = itemView.findViewById(R.id.ivBackground);
            btnDownloadOffer = itemView.findViewById(R.id.btnDownloadOffer);

            clicked = false;

            itemView.setOnClickListener(view -> {
                clicked = !clicked;

                if (clicked) {
                    tvLocalName.setVisibility(View.GONE);
                    cnstLayBack.setVisibility(View.VISIBLE);
                } else {
                    tvLocalName.setVisibility(View.VISIBLE);
                    cnstLayBack.setVisibility(View.GONE);
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void setOffer(Offer offer) {
            Requests requests = new Requests();
            this.offers = offer;
            tvLocalName.setText(Decode.decodeUTF8(offer.getLocalName()));
            tvComment.setText(offer.getComment());
            tvOfferPrice.setText(String.valueOf(offer.getPrice()));
            tvGoTo.setText("Go to " + Decode.decodeUTF8(offer.getLocalName()));


            //if(offer.getImageOffer() != null || !offer.getImageOffer().equals("00000000-0000-0000-0000-000000000000"))
              //  Picasso.with(context).load(Constants.PROFILE_PHOTO_URL + Constants.USER_LOGGED.getIdUser() +
                  //      "/profilePhoto").fit().into(ivBackground);

            btnDownloadOffer.setOnClickListener(view -> {
                requests.getLocal(offer.getLocalID().toString(), context);
            });
        }
    }
}
