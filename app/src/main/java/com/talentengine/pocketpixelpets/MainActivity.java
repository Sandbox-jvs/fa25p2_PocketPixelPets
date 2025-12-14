package com.talentengine.pocketpixelpets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.transition.MaterialFade;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        // LOGOUT MENU IMPLEMENTATION - Force update the menu
        invalidateOptionsMenu();

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
        otterSpriteView.setFrameDuration(1500);

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

    // vvv vvv vvv LOGOUT MENU IMPLEMENTATION vvv vvv vvv
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);

        // TODO: UPDATE THE TITLE TO REFLECT THE USERNAME OF THE CURRENT USER
        item.setTitle("TEST");      // Set the username

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                // TODO: Logout Alert Dialog
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    /**
     * Asks the user if they really want to log out. If so, they will be removed to login screen
     */
    private void showLogoutDialog() {
        // TODO: Update MainActivity to reflect the current activity
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        /*
         * Display a menu as:
         * | - - - - - - - - - - - - - - - |
         * | Do you really want to logout? |
         * |        Logout | Cancel        |
         * | - - - - - - - - - - - - - - - |
         */
        alertBuilder.setTitle("Do you really want to logout?");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        // TODO: Finish logout method
        // TODO: Start the activity
        Toast.makeText(this, "LOGGED OUT!", Toast.LENGTH_SHORT).show();
    }
    // ^^^ ^^^ ^^^ LOGOUT MENU IMPLEMENTATION ^^^ ^^^ ^^^
}
