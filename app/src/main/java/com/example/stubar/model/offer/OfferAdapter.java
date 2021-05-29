package com.example.stubar.model.offer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stubar.R;
import com.example.stubar.utils.api.Requests;
import com.example.stubar.utils.constants.Constants;
import com.example.stubar.utils.decode.Decode;
import com.squareup.picasso.Picasso;

public class OfferAdapter extends RecyclerView.Adapter<com.example.stubar.model.offer.OfferAdapter.ViewHolder> {
    private final Context context;
    private final OfferApiResponse offer;

    /**
     * Constructor of the OfferAdapter
     *
     * @param context Context
     * @param offer   OfferApiResponse
     */
    public OfferAdapter(Context context, OfferApiResponse offer) {
        this.context = context;
        this.offer = offer;
    }

    /**
     * Method inherited from RecyclerView
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_promotions, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Method inherited from RecyclerView
     *
     * @param holder   OfferAdapter.ViewHolder
     * @param position int
     */
    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.ViewHolder holder, int position) {
        holder.setOffer(offer.getOffers().get(position));
    }

    /**
     * Method inherited from RecyclerView
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return offer.getOffers().size();
    }

    /**
     * Protected class that extends to RecyclerView.ViewHolder to show all the data has been requested
     * to the API and show the data to the layout
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cnstLayFront, cnstLayBack, itemPromo;
        TextView tvLocalName, tvComment, tvGoTo, tvOfferPrice;
        ImageView ivBackground;
        ImageButton btnDownloadOffer;
        Offer offers;

        boolean clicked;

        /**
         * Constructor ViewHolder
         *
         * @param itemView @NonNull View
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPromo = itemView.findViewById(R.id.itemPromo);
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

        /**
         * Method to set an offer
         *
         * @param offer
         */
        @SuppressLint("SetTextI18n")
        public void setOffer(Offer offer) {
            Requests requests = new Requests();

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            itemPromo.setMaxWidth(display.getWidth() / 2);
            itemPromo.setMaxHeight(display.getWidth() / 2);

            this.offers = offer;
            tvLocalName.setText(Decode.decodeUTF8(offer.getLocalName()));
            tvComment.setText(offer.getComment());
            tvOfferPrice.setText(offer.getPrice() + " €");
            tvGoTo.setText("Go to " + Decode.decodeUTF8(offer.getLocalName()));


            if (!offer.getImageOffer().equals("00000000-0000-0000-0000-000000000000"))
                Picasso.with(context).load(Constants.ALL_OFFERS_URL + "/" + offer.getIdOffer() +
                        "/offerImage").into(ivBackground);

            btnDownloadOffer.setOnClickListener(view -> requests.getLocal(offer.getLocalID().toString(), context));
        }
    }
}