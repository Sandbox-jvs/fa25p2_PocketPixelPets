package com.talentengine.pocketpixelpets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.talentengine.pocketpixelpets.database.AppDatabase;
import com.talentengine.pocketpixelpets.database.entities.Pet;
import com.talentengine.pocketpixelpets.database.entities.User;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private MaterialButton signupButton;
    private User user = null;

    private AppDatabase database;

    private SpriteView otterSpriteView;
    private SpriteView logoSpriteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = AppDatabase.getDatabase(this);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        signupButton  = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyUser()) {
                    toastMaker("User created successfully!");
                    Intent intent = new Intent(SignUpActivity.this, ChoosePetActivity.class);
                    intent.putExtra("USER_ID", user.getUser_id());
                    intent.putExtra("USERNAME", user.getUsername());


                    Pet pet = new Pet();
                    pet.setUser_id(user.getUser_id());
                    AppDatabase.getDatabase(SignUpActivity.this).PetDao().insertPet(pet);

                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });

        otterSpriteView = findViewById(R.id.otterSpriteView);
        otterSpriteView.setSpriteSheet(
                R.drawable.otter_sprite_sheet_purple,
                2,
                3
        );
        otterSpriteView.setFrameDuration(1500);

        logoSpriteView = findViewById(R.id.logoSpriteView);
        logoSpriteView.setSpriteSheet(
                R.drawable.logo_sprite_sheet,
                2,
                3
        );
        logoSpriteView.setFrameDuration(1000);
    }

    private boolean verifyUser() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            toastMaker("Username and password cannot be blank");
            return false;
        }
        user = database.userDao().getUserByUsername(username);
        if (user != null) {
            toastMaker("Username already exists");
            return false;
        }

        User newUser = new User(username, password);
        database.userDao().insertUser(newUser);

        user = database.userDao().getUserByUsername(username);

        return true;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent signUpIntentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
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
