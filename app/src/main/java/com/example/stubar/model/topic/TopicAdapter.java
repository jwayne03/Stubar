package com.example.stubar.model.topic;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * Method inherited from RecyclerView
     *
     * @param parent   ViewGroup
     * @param viewType int
     * @return null
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * Method inherited from RecyclerView
     *
     * @param holder   RecyclerView.ViewHolder
     * @param position int
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * Method inherited from RecyclerView that gets the item count
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Protected class that extends to RecyclerView.ViewHolder to show all the data has been requested
     * to the API and show the data to the layout
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}