package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChoosePersonalityActivity extends AppCompatActivity {

    private String username;
    private Button nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_personality);

        username = getIntent().getStringExtra("USERNAME");

        nextButton = findViewById(R.id.nextButtonPersonality);
        nextButton.setOnClickListener(v -> goToNextStep());
    }

    private void goToNextStep() {
        // Pass username to the Choose Food activity
        Intent intent = new Intent(this, ChooseFoodActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }
}
