package com.travelgo.tripzyy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ShareFragment extends Fragment {

    private String shareText = "Hey! Check out this awesome app: https://yourwebsite.com/download-app";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        Button whatsappBtn = view.findViewById(R.id.shareToWhatsApp);
        Button instagramBtn = view.findViewById(R.id.shareToInstagram);
        Button telegramBtn = view.findViewById(R.id.shareToTelegram);

        whatsappBtn.setOnClickListener(v -> shareToApp("com.whatsapp"));
        instagramBtn.setOnClickListener(v -> shareToApp("com.instagram.android"));
        telegramBtn.setOnClickListener(v -> shareToApp("org.telegram.messenger"));

        return view;
    }

    private void shareToApp(String packageName) {
        PackageManager pm = requireActivity().getPackageManager();
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setPackage(packageName);

            if (intent.resolveActivity(pm) != null) {
                startActivity(intent);
            } else {
                // fallback to chooser if app not found
                startActivity(Intent.createChooser(intent, "Share via"));
            }

        } catch (Exception e) {
            // fallback to generic chooser
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(intent, "Share via"));
        }
    }
}