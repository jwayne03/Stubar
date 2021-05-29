package com.example.stubar.model.local;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocalAdapterSpinner extends BaseAdapter {
    private final Context context;
    private final LocalResponseArray localResponseArray;

    /**
     * Constructor to create the adapter for the specific Spinner of Local
     *
     * @param context            Context
     * @param localResponseArray LocalResponseArray
     */
    public LocalAdapterSpinner(Context context, LocalResponseArray localResponseArray) {
        this.context = context;
        this.localResponseArray = localResponseArray;
    }

    /**
     * Method inheritated from RecyclerView to get the count of the items that has been requested to
     * the API
     *
     * @return int
     */
    @Override
    public int getCount() {
        return localResponseArray.size();
    }

    /**
     * Method inheritated from RecyclerView Get the item of array to get the object that has been selected
     *
     * @param i int
     * @return int
     */
    @Override
    public Object getItem(int i) {
        return localResponseArray.get(i);
    }

    /**
     * Method inheritated from RecyclerView to get the item ID
     *
     * @param i int
     * @return int
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Method to get the name by the position of the array
     *
     * @param i
     * @return
     */
    public String getItemName(int i) {
        return localResponseArray.get(i).getIdLocal().toString();
    }

    /**
     * Method inheritated from RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    /**
     * Method inheritated from RecyclerView and inflate the layout to the XML
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
        textView.setText(localResponseArray.get(i).getName());
        textView.setHeight(100);
        return view;
    }

    /**
     * Method inheritated from RecyclerView
     *
     * @param position    int
     * @param convertView View
     * @param parent      ViewGroup
     * @return view
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) tv.setVisibility(View.GONE);
        return view;
    }
}