package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.transition.MaterialFade;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import com.talentengine.pocketpixelpets.database.AppDatabase;
import com.talentengine.pocketpixelpets.database.Repository;

public class MainActivity extends AppCompatActivity {
    AppDatabase database;
    private Repository repository;
    public static final String TAG = "TE_VIRTUALPET";
    private SpriteView otterSpriteView;
    private SpriteView logoSpriteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //check database is functioning; DATABASE CREATED! appears only on first-time db creation
        Log.d(TAG, "MainActivity onCreate called");

        database = AppDatabase.getDatabase(this);
        //database.getOpenHelper().getWritableDatabase();
        repository = Repository.getRepository(getApplication());



        MaterialButton signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

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
