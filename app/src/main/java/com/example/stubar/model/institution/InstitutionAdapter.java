package com.example.stubar.model.institution;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.UUID;

public class InstitutionAdapter extends BaseAdapter {
    private Context context;
    private InstitutionApiResponse listOfInstitutions;

    public InstitutionAdapter(Context context, InstitutionApiResponse listOfInstitutions) {
        this.context = context;
        this.listOfInstitutions = listOfInstitutions;
    }

    @Override
    public int getCount() {
        return listOfInstitutions.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfInstitutions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String getItemName(int i) {
        return listOfInstitutions.get(i).getName();
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, null);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(listOfInstitutions.get(i).getName());
        textView.setHeight(100);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) {
            // Set the hint text color gray
            tv.setVisibility(View.GONE);
        }
        return view;
    }
}
