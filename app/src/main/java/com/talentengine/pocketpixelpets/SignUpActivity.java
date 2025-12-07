package com.talentengine.pocketpixelpets;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private AnimationDrawable otterAnim;
    private AnimationDrawable logoSparkleAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView otterImage = findViewById(R.id.otterImage);
        if (otterImage != null && otterImage.getDrawable() instanceof AnimationDrawable) {
            otterAnim = (AnimationDrawable) otterImage.getDrawable();
        }

        ImageView logoImage = findViewById(R.id.logoImage);
        if (logoImage != null && logoImage.getDrawable() instanceof AnimationDrawable) {
            logoSparkleAnim = (AnimationDrawable) logoImage.getDrawable();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            if (otterAnim != null && !otterAnim.isRunning()) {
                otterAnim.start();
            }

            if (logoSparkleAnim != null && !logoSparkleAnim.isRunning()) {
                logoSparkleAnim.start();
            }
        } else {
            if (otterAnim != null && otterAnim.isRunning()) {
                otterAnim.stop();
            }

            if (logoSparkleAnim != null && logoSparkleAnim.isRunning()) {
                logoSparkleAnim.stop();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (otterAnim != null && otterAnim.isRunning()) {
            otterAnim.stop();
        }

        if (logoSparkleAnim != null && logoSparkleAnim.isRunning()) {
            logoSparkleAnim.stop();
        }
    }
}
