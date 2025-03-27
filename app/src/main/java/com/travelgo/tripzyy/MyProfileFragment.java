package com.travelgo.tripzyy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etBirthday, etPhone, etInstagram, etEmail, etPassword;
    private Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        etName = findViewById(R.id.et_name);
        etBirthday = findViewById(R.id.et_birthday);
        etPhone = findViewById(R.id.et_phone);
        etInstagram = findViewById(R.id.et_instagram);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnEditProfile = findViewById(R.id.btn_edit_profile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement profile update logic here
            }
        });
    }
}