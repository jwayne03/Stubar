package com.example.stubar.model.topic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TopicAdapterSpinner extends BaseAdapter {
    private Context context;
    private TopicResponse topicResponse;

    /**
     * Constructor to inflate the layout with an adapter to show all the topics in the spinner of the UI
     *
     * @param context       Context
     * @param topicResponse TopicResponse
     */
    public TopicAdapterSpinner(Context context, TopicResponse topicResponse) {
        this.context = context;
        this.topicResponse = topicResponse;
    }

    /**
     * Method inherited from RecyclerView and get the count of the items
     *
     * @return int
     */
    @Override
    public int getCount() {
        return this.topicResponse.size();
    }

    /**
     * Method inherited from RecyclerView and get the item by position
     *
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return this.topicResponse.get(i);
    }

    /**
     * Method inherited from RecyclerView and get the ID of the id
     *
     * @param i
     * @return int
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Method to get name by position
     *
     * @param i int
     * @return int
     */
    public String getItemName(int i) {
        Log.d("TAG", "getItemName: " + topicResponse);
        return this.topicResponse.get(i).getIdTopic().toString();
    }

    /**
     * Method inherited from RecyclerView
     *
     * @param position int
     * @return int
     */
    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    /**
     * Method inherited from RecyclerView to build the view to inflate the layout
     *
     * @param i           int
     * @param convertView View
     * @param viewGroup   ViewGroup
     * @return view
     */
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, null);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(this.topicResponse.get(i).getDescription());
        textView.setHeight(100);
        return view;
    }

    /**
     * Method inherited from RecyclerView
     *
     * @param position    int
     * @param convertView View
     * @param parent      ViewGroup
     * @return
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) tv.setVisibility(View.GONE);
        return view;
    }
}


