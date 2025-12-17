/**
 * NamePetActivity.java | Activity for naming a pet after choosing its color
 * @author Jason Campos
 * @since 12/15/25
 */
package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class NamePetActivity extends AppCompatActivity {

    private TextInputEditText petNameInput;
    private String username;
    private MaterialButton nextButton;
    private SpriteView petPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_pet);

        username = getIntent().getStringExtra("USERNAME");

        nextButton   = findViewById(R.id.nextButtonName);
        petNameInput = findViewById(R.id.petNameInput);
        petPreview   = findViewById(R.id.petPreview);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NamePetActivity.this, "You named your pet: " + petNameInput.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToNextStep() {
        Toast.makeText(this, "Pet naming feature coming soon!", Toast.LENGTH_SHORT).show();
    }
}
