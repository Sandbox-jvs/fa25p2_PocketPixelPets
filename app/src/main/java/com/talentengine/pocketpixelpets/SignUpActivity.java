package com.talentengine.pocketpixelpets;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private SpriteView otterSpriteView;
    private SpriteView logoSpriteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        otterSpriteView = findViewById(R.id.otterSpriteView);
        otterSpriteView.setSpriteSheet(
                R.drawable.otter_sprite_sheet_purple,
                2,
                3
        );
        otterSpriteView.setFrameDuration(900);

        logoSpriteView = findViewById(R.id.logoSpriteView);
        logoSpriteView.setSpriteSheet(
                R.drawable.logo_sprite_sheet,
                2,
                3
        );
        logoSpriteView.setFrameDuration(1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (otterSpriteView != null) otterSpriteView.start();
        if (logoSpriteView != null) logoSpriteView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (otterSpriteView != null) otterSpriteView.stop();
        if (logoSpriteView != null) logoSpriteView.stop();
    }
}
