package com.travelgo.tripzyy;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;


public class LanguagesFragment extends Fragment {

    private Spinner languageSpinner;
    private Button applyButton;
    private static final String PREFS_NAME = "TravelAppPrefs";

    private String[] languageCodes = {"en", "es", "fr", "de", "hi"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_languages, container, false);

        languageSpinner = view.findViewById(R.id.languageSpinner);
        applyButton = view.findViewById(R.id.applyLanguageButton);

        loadSavedLanguage();

        applyButton.setOnClickListener(v -> {
            int selectedIndex = languageSpinner.getSelectedItemPosition();
            String selectedLangCode = languageCodes[selectedIndex];

            saveLanguage(selectedLangCode);
            changeAppLanguage(selectedLangCode);
        });

        return view;
    }

    private void saveLanguage(String langCode) {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString("app_language", langCode);
        editor.apply();
    }

    private void loadSavedLanguage() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, 0);
        String savedLang = prefs.getString("app_language", "en");

        for (int i = 0; i < languageCodes.length; i++) {
            if (languageCodes[i].equals(savedLang)) {
                languageSpinner.setSelection(i);
                break;
            }
        }
    }

    private void changeAppLanguage(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        requireActivity().getResources().updateConfiguration(config,
                requireActivity().getResources().getDisplayMetrics());

        requireActivity().recreate();  // Recreate activity to apply new language
    }
}