package com.talentengine.pocketpixelpets;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private AnimationDrawable otterAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageView otterImage = findViewById(R.id.otterImage);

        otterAnim = (AnimationDrawable) otterImage.getDrawable();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (otterAnim == null) return;

        if (hasFocus) {
            if (!otterAnim.isRunning()) {
                otterAnim.start();
            }
        } else {
            if (otterAnim.isRunning()) {
                otterAnim.stop();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (otterAnim != null && otterAnim.isRunning()) {
            otterAnim.stop();
        }
    }
}
