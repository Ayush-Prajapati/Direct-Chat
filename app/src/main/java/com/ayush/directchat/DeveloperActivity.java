package com.ayush.directchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Objects;

public class DeveloperActivity extends AppCompatActivity {

    private ImageView img_instagram,img_facebook;
    private TextView textViewFeedback, textViewRateApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        img_instagram = findViewById(R.id.img_instagram);
        img_facebook = findViewById(R.id.img_facebook);
        textViewFeedback = findViewById(R.id.textViewFeedBack);
        textViewRateApp = findViewById(R.id.textViewRateApp);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Developer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        img_instagram.setOnClickListener(v -> {
            YoYo.with(Techniques.Pulse)
                    .duration(500)
                    .repeat(0)
                    .playOn(img_instagram);
            openInsta();
        });
        img_facebook.setOnClickListener(v -> {
            YoYo.with(Techniques.Pulse)
                    .duration(500)
                    .repeat(0)
                    .playOn(img_facebook);
            openFacebook();
        });

        textViewFeedback.setOnClickListener(v -> {
            YoYo.with(Techniques.Pulse)
                    .duration(500)
                    .repeat(0)
                    .playOn(textViewFeedback);
            openFeedback();
        });
        textViewRateApp.setOnClickListener(v -> {
            YoYo.with(Techniques.Pulse)
                    .duration(500)
                    .repeat(0)
                    .playOn(textViewRateApp);
            openRateApp();
        });


    }

    private void openRateApp() {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(String.valueOf(R.string.rate_app_link)));
        startActivity(viewIntent);
    }

    private void openFeedback() {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse("mailto:ayushprajapati8@gmail.com"));
        startActivity(viewIntent);
    }

    private void openFacebook() {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse("https://www.facebook.com/ayush.prajapati.7355/"));
        startActivity(viewIntent);
    }

    private void openInsta() {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse("https://www.instagram.com/ayush_prajapati1689/"));
        startActivity(viewIntent);
    }
}
