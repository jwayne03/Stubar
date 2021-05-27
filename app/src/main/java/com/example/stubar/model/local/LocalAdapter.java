package com.example.stubar.model.local;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocalAdapter extends RecyclerView.Adapter<com.example.stubar.model.local.LocalAdapter.ViewHolder> {

    /**
     * Method inhertidate form RecyclerView.Adapter
     *
     * @param parent   ViewGroup
     * @param viewType int
     * @return null
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * Method inhertidate form RecyclerView.Adapter
     *
     * @param holder   ViewHolder
     * @param position int
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    /**
     * Method inhertidate form RecyclerView.Adapter to get the count of items that has been requested
     * to the API
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Protected class Viewholder that extends to RecyclerView.Adapter to inflate the layout
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Constructor of the protected class ViewHolder
         *
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
