package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseColorActivity extends AppCompatActivity {

    private String username;
    private Button nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);

        username = getIntent().getStringExtra("USERNAME");

        nextButton = findViewById(R.id.nextButtonColor);
        nextButton.setOnClickListener(v -> goToPersonality());
    }

    private void goToPersonality() {
        // Pass username to the next activity
        Intent intent = new Intent(this, ChoosePersonalityActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        // Prevent going back to this activity
        finish();
    }
}
