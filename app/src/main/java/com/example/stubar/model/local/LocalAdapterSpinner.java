package com.example.stubar.model.local;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocalAdapterSpinner extends BaseAdapter {
    private Context context;
    private LocalResponseArray localResponseArray;

    public LocalAdapterSpinner(Context context, LocalResponseArray localResponseArray) {
        this.context = context;
        this.localResponseArray = localResponseArray;
    }

    @Override
    public int getCount() {
        return localResponseArray.size();
    }

    @Override
    public Object getItem(int i) {
        return localResponseArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String getItemName(int i) {
        return localResponseArray.get(i).getName();
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, null);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(localResponseArray.get(i).getName());
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

