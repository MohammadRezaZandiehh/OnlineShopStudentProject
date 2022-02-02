package com.example.onlineshop.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class Manager {
    private SharedPreferences sharedPreferences;

    public Manager(Context context) {
        sharedPreferences = context.getSharedPreferences("information", Context.MODE_PRIVATE);

        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            }
        });
    }

    public String getGender() {
        return sharedPreferences.getString("gender", "");
    }


    public void saveUserInformation(String gender) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("gender", gender);
        editor.apply();
    }
}
