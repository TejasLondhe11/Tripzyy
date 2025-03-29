package com.travelgo.tripzyy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.travelgo.tripzyy.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyProfileFragment extends Fragment {

    private EditText etName, etBirthday, etPhone, etUsername, etEmail, etPassword;
    private ImageView ivProfilePhoto;
    private Button btnEditProfile;

    private SharedPreferences preferences;
     SharedPreferences.Editor editor;
    private Uri imagePath;
    private Bitmap bitmap;

    // Modern Activity Result API
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        handleImageSelection(result.getData());
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        // Initialize SharedPreferences
        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        editor = preferences.edit();

        // Initialize views
        initViews(view);

        // Load saved profile data
        loadProfileData();

        return view;
    }

    private void initViews(View view) {
        etName = view.findViewById(R.id.et_name);
        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);
        etBirthday = view.findViewById(R.id.et_birthday);
        etPhone = view.findViewById(R.id.et_phone);
        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);

        // Check for existing profile image
        File imageFile = new File(requireContext().getFilesDir(), "user.jpg");
        if (imageFile.exists()) {
            Bitmap savedBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            ivProfilePhoto.setImageBitmap(savedBitmap);
        }

        // Profile photo click listener
        ivProfilePhoto.setOnClickListener(v -> openImagePicker());

        // Edit profile button click listener
        btnEditProfile.setOnClickListener(v -> updateProfile());
    }

    private void loadProfileData() {
        // Load saved data from SharedPreferences
        etName.setText(preferences.getString("name", ""));
        etBirthday.setText(preferences.getString("birthday", ""));
        etPhone.setText(preferences.getString("phone", ""));
        etUsername.setText(preferences.getString("username", ""));
        etEmail.setText(preferences.getString("email", ""));
        // Note: Storing passwords in SharedPreferences is not secure - consider better alternatives
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        imagePickerLauncher.launch(Intent.createChooser(intent, "Select Profile Photo"));
    }

    private void handleImageSelection(Intent data) {
        imagePath = data.getData();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imagePath);
            ivProfilePhoto.setImageBitmap(bitmap);

            // Save the image to internal storage
            File imageFile = new File(requireContext().getFilesDir(), "user.jpg");
            try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProfile() {
        // Retrieving user input
        String name = etName.getText().toString().trim();
        String birthday = etBirthday.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Name, Email, and Password are required!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to SharedPreferences
        editor.putString("name", name);
        editor.putString("birthday", birthday);
        editor.putString("phone", phone);
        editor.putString("username", username);
        editor.putString("email", email);
        // Note: In production, never store passwords in plain text - use encryption
        editor.apply();

        Toast.makeText(requireContext(), "Profile Updated Successfully!",
                Toast.LENGTH_SHORT).show();
    }
}