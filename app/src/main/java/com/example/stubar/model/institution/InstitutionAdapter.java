package com.example.stubar.model.institution;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InstitutionAdapter extends BaseAdapter {
    private Context context;
    private InstitutionApiResponse listOfInstitutions;

    /**
     * Constructor of the adapter of institution
     *
     * @param context            Context
     * @param listOfInstitutions InstitutionApiResponse
     */
    public InstitutionAdapter(Context context, InstitutionApiResponse listOfInstitutions) {
        this.context = context;
        this.listOfInstitutions = listOfInstitutions;
    }

    /**
     * Method inherited from the RecyclerView to get the count of items of the adapter, the items
     * is called from the API
     *
     * @return int
     */
    @Override
    public int getCount() {
        return listOfInstitutions.size();
    }

    /**
     * Method inherited from the RecyclerView to get the position of items of the adapter, the items
     * is called from the API
     *
     * @param i int
     * @return int
     */
    @Override
    public Object getItem(int i) {
        return listOfInstitutions.get(i);
    }

    /**
     * Method inherited from the RecyclerView to get the count of items of the adapter, the items
     * is called from the API
     *
     * @param i int
     * @return int
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Get the name of the item against the position of the request
     *
     * @param i int
     * @return int
     */
    public String getItemName(int i) {
        return listOfInstitutions.get(i).getName();
    }

    /**
     * Method inherited from the RecyclerView to check if enabled, the items
     * is called from the API
     *
     * @param position int
     * @return int
     */
    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    /**
     * Method inherited from the RecyclerView to make the view of the layout, the items
     * is called from the API
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
        textView.setText(listOfInstitutions.get(i).getName());
        textView.setHeight(100);
        return view;
    }

    /**
     * The items
     * is called from the API
     *
     * @param position    int
     * @param convertView View
     * @param parent      ViewGroup
     * @return view       View
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) {
            tv.setVisibility(View.GONE);
        }
        return view;
    }
}
