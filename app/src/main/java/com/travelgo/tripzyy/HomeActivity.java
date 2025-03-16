package com.travelgo.tripzyy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.travelgo.tripzyy.databinding.ActivityMainBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Load HomeFragment by default
        replaceFragment(new HomeFragment());

        // Remove background from BottomNavigationView
        binding.bottomNavigationView.setBackground(null);

        // Set up BottomNavigationView click listener
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.shorts:
                    selectedFragment = new ShortsFragment();
                    break;

                case R.id.subscription:
                    selectedFragment = new SubscriptionFragment();
                    break;

                case R.id.library:
                    selectedFragment = new LibraryFragment();
                    break;
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }

            return true;
        });
    }

    // Method to replace fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
