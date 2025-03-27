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

import com.travelgo.tripzyy.R;

public class SplashActivity extends AppCompatActivity {
    ImageView ivLogo;
    TextView tvTitle;

    Animation animtranslate,translate;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivLogo=findViewById(R.id.ivSplashLogo1);
        tvTitle=findViewById(R.id.tvSplashTitle);
        animtranslate= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.toptobottomtranslate);
        ivLogo.startAnimation(animtranslate);
        translate=AnimationUtils.loadAnimation(SplashActivity.this,R.anim.translate);
        tvTitle.startAnimation(translate);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
            }
        },3000);
    }
}