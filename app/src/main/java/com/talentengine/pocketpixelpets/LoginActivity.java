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
import com.talentengine.pocketpixelpets.database.entities.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private MaterialButton loginButton;

    private AppDatabase database;
    private User user = null;

    private SpriteView otterSpriteView;
    private SpriteView logoSpriteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = AppDatabase.getDatabase(this);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton   = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verifyUser()) {
                    toastMaker("Invalid credentials");
                } else {
                    Intent intent = new Intent(LoginActivity.this, ChoosePetActivity.class);
                    intent.putExtra("USER_ID", user.getUser_id());
                    intent.putExtra("USERNAME", user.getUsername());
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
        if (username.isEmpty()) {
            toastMaker("username should not be blank");
            return false;
        }
        user = database.userDao().getUserByUsername(username);
        if (user != null) {
            String password = passwordInput.getText().toString();
            if (password.equals(user.getPassword())) {
                return true;
            } else {
                toastMaker("Invalid password");
                return false;
            }
        }
        toastMaker(String.format("No %s found", username));
        return false;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
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