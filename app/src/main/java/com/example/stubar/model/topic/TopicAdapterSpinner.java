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

    public TopicAdapterSpinner(Context context, TopicResponse topicResponse) {
        this.context = context;
        this.topicResponse = topicResponse;
    }

    @Override
    public int getCount() {
        return this.topicResponse.size();
    }

    @Override
    public Object getItem(int i) {
        return this.topicResponse.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String getItemName(int i) {
        Log.d("TAG", "getItemName: "+ topicResponse);
        return this.topicResponse.get(i).getIdTopic().toString();
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, null);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(this.topicResponse.get(i).getDescription());
        textView.setHeight(100);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) tv.setVisibility(View.GONE);
        return view;
    }
}


