package com.travelgo.tripzyy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


public class AboutFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView ratingText = view.findViewById(R.id.ratingText);
        Button btnRateApp = view.findViewById(R.id.btnRateApp);

// Show selected rating
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            ratingText.setText("You rated: " + rating + " stars");
        });

// Open Play Store on click
        btnRateApp.setOnClickListener(v -> {
            final String appPackageName = requireContext().getPackageName(); // get your app package
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + appPackageName)));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        });

        return view;

    }
}