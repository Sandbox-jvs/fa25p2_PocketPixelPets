/**
 * NamePetActivity.java | Activity for naming a pet after choosing its color
 * @author Jason Campos
 * @since 12/15/25
 */
package com.talentengine.pocketpixelpets;

import android.content.Context;
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
    private int user_id = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_pet);

        username = getIntent().getStringExtra("USERNAME");

        nextButton   = findViewById(R.id.nextButtonName);
        petNameInput = findViewById(R.id.petNameInput);
        petPreview   = findViewById(R.id.petPreview);

        loadPetPreview();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savePetName()) {
                    toastMaker("Pet name successfully!");
                    goToNextStep();
                }
            }
        });
    }

    private boolean savePetName() {
        String petName = petNameInput.getText().toString();
        if (petName.isEmpty()) {
            toastMaker("Pet name cannot be empty!");
            return false;
        }

        Pet pet = AppDatabase.getDatabase(NamePetActivity.this).PetDao().getPetFromOwnerUserId(user_id);
        pet.setName(petName);
        AppDatabase.getDatabase(NamePetActivity.this).PetDao().updatePet(pet);
        return true;
    }

    private void goToNextStep() {
        Intent intent = new Intent(NamePetActivity.this, GamePlayActivity.class);
        intent.putExtra("USER_ID", user_id);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent namePetActivityIntent(Context context, String username, int user_id) {
        Intent intent = new Intent(context, NamePetActivity.class);
        intent.putExtra("USERNAME", username);
        intent.putExtra("USER_ID", user_id);
        return intent;
    }

    private void loadPetPreview() {
        Pet pet = AppDatabase.getDatabase(NamePetActivity.this).PetDao().getPetFromOwnerUserId(user_id);
        if (pet != null && petPreview != null) {
            String petType = pet.getPet_type();
            String petColor = pet.getPet_color();

            if (petType != null && petColor != null) {

                int resId = getResources().getIdentifier(petType + "_sprite_sheet_" + petColor, "drawable", getPackageName());

                if (resId != 0) {
                    petPreview.setSpriteSheet(resId, 2, 3);
                    petPreview.setFrameDuration(1500);
                    petPreview.start();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (petPreview != null) {
            petPreview.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (petPreview != null) {
            petPreview.stop();
        }
    }
}
