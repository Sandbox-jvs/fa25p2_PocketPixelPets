package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NamePetActivity extends AppCompatActivity {

    private String username;
    private Button nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_pet);

        username = getIntent().getStringExtra("USERNAME");

        nextButton = findViewById(R.id.nextButtonName);
        nextButton.setOnClickListener(v -> goToNextStep());
    }

    private void goToNextStep() {
        Toast.makeText(this, "Pet naming feature coming soon!", Toast.LENGTH_SHORT).show();
    }
}
