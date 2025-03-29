package com.travelgo.tripzyy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class SplashActivity extends AppCompatActivity {
    ImageView ivLogo;

    Animation animtranslate;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivLogo=findViewById(R.id.ivSplashLogo);

        animtranslate= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.toptobottomtranslate);
        ivLogo.startAnimation(animtranslate);


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
            }
        },3000);
    }
}