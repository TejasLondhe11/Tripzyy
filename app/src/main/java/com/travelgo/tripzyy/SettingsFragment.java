package com.travelgo.tripzyy;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;


public class SettingsFragment extends Fragment {

    private static final String PREFS_NAME = "TravelAppPrefs";

    private Switch notificationsSwitch, darkModeSwitch;
  //  private Spinner languageSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        notificationsSwitch = view.findViewById(R.id.notificationsSwitch);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);
        //languageSpinner = view.findViewById(R.id.languageSpinner);

        loadSettings();

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSetting("notifications_enabled", isChecked);
        });

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSetting("dark_mode_enabled", isChecked);
        });

//        languageSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
//                saveSetting("language", parent.getItemAtPosition(position).toString());
//            }
//
//            @Override
//            public void onNothingSelected(android.widget.AdapterView<?> parent) {
//            }
//        });

        return view;
    }

    private void loadSettings() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, 0);

        notificationsSwitch.setChecked(prefs.getBoolean("notifications_enabled", true));
        darkModeSwitch.setChecked(prefs.getBoolean("dark_mode_enabled", false));

//        String lang = prefs.getString("language", "English");
//        String[] languages = getResources().getStringArray(R.array.language_options);
//        for (int i = 0; i < languages.length; i++) {
//            if (languages[i].equals(lang)) {
//                languageSpinner.setSelection(i);
//                break;
//            }
//        }
    }

    private void saveSetting(String key, boolean value) {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void saveSetting(String key, String value) {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString(key, value);
        editor.apply();
    }
}