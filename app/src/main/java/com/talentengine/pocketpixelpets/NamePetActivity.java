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
import com.talentengine.pocketpixelpets.database.AppDatabase;
import com.talentengine.pocketpixelpets.database.entities.Pet;

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
                if (savePetName()) {
                    Toast.makeText(NamePetActivity.this, "You named your pet: " + petNameInput.getText().toString(), Toast.LENGTH_SHORT).show();
                    goToNextStep();
                }
            }
        });
    }

    private boolean savePetName() {
        String petName = petNameInput.getText().toString();
        if (petName.isEmpty()) {
            Toast.makeText(this, "Pet name cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        int user_id = getIntent().getIntExtra("USER_ID", -1);

        Pet pet = AppDatabase.getDatabase(NamePetActivity.this).PetDao().getPetFromOwnerUserId(user_id);
        pet.setName(petName);
        AppDatabase.getDatabase(NamePetActivity.this).PetDao().updatePet(pet);
        return true;
    }

    private void goToNextStep() {
        int user_id = getIntent().getIntExtra("USER_ID", -1);
        Intent intent = new Intent(NamePetActivity.this, GamePlayActivity.class);
        intent.putExtra("USER_ID", user_id);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }
}
