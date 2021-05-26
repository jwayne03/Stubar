package com.example.stubar;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveState {

    private Context context;
    private SharedPreferences sharedPreferences;

    public SaveState(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("value", Context.MODE_PRIVATE);
    }

    public void setState(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("value", value);
        editor.apply();
    }

    public boolean getState() {
        return sharedPreferences.getBoolean("value", false);
    }
}
