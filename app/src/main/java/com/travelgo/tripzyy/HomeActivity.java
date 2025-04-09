package com.travelgo.tripzyy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.travelgo.tripzyy.MyProfileFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    boolean doubletap = false;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    HomeFragment homeFragment = new HomeFragment();
    PackageFragment packagesFragment = new PackageFragment();
    PaymentFragment paymentFragment = new PaymentFragment();


    MyProfileFragment myProfileFragment = new MyProfileFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    AboutFragment aboutFragment = new AboutFragment();
    VehiclesFragment vehiclesFragment = new VehiclesFragment();
    TermsAndConditionsFragment termsAndConditionsFragment = new TermsAndConditionsFragment();
    LanguagesFragment languagesFragment = new LanguagesFragment();
    LogoutFragment logoutFragment = new LogoutFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.homeBottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.homeDrawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

            navigationView.setCheckedItem(R.id.homeMenuHome);
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.homeBottomNavigationHome) {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, homeFragment).commit();
                } else if (item.getItemId() == R.id.homeBottomNavigationPayment) {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, paymentFragment).commit();
                } else if (item.getItemId() == R.id.homeBottomNavigationVehicles) {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, vehiclesFragment).commit();
                }else if (item.getItemId() == R.id.homeBottomNavigationPackages) {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, packagesFragment).commit();
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        if (doubletap) {
            finishAffinity();
            super.onBackPressed();
        } else {
            Toast.makeText(HomeActivity.this,
                    "Press Again to Exit App",
                    Toast.LENGTH_SHORT).show();
            doubletap = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubletap = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.homeMenuHome) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    homeFragment).commit();
        }
        if (itemId == R.id.homeMenuMyProfile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    myProfileFragment).commit();
        }

        if (itemId == R.id.homeMenusettings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settingsFragment).commit();
        }
        if (itemId == R.id.homeMenuLanguages) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, languagesFragment).commit();
        }
        if (itemId == R.id.homeMenuTermsAndConditions) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, termsAndConditionsFragment).commit();
        }
        if (itemId == R.id.homeMenuAboutUs) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutFragment).commit();
        }
        if (itemId == R.id.homeMenuLogout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, logoutFragment).commit();
            //   Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            logout();

        }
        if (item.getItemId() == R.id.homeBottomNavigationHome) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        }
        if (item.getItemId() == R.id.homeBottomNavigationPackages) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, packagesFragment).commit();
        }
        if (item.getItemId() == R.id.homeBottomNavigationVehicles) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, vehiclesFragment).commit();
        }
        if (item.getItemId() == R.id.homeBottomNavigationPayment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, paymentFragment).commit();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    private void logout() {

        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("TripzyyApp");
        ad.setMessage("Are you sure you want to logout?");
        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                editor.putBoolean("isLogin", false).apply();
                startActivity(intent);
                finish();
            }
        }).create().show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
