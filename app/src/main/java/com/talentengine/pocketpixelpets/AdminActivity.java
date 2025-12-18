/**
 * Implementation of the Admin Activity page, utilizing a recycler view to get user information
 * @author Cristian Perez
 * @since 12/17/25
 */

package com.talentengine.pocketpixelpets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.talentengine.pocketpixelpets.database.AppDatabase;
import com.talentengine.pocketpixelpets.database.entities.Action;
import com.talentengine.pocketpixelpets.database.entities.Pet;
import com.talentengine.pocketpixelpets.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    // Variables required to use the recycler view
    private RecyclerView usersRecyclerView;
    private UserCardViewAdapter adapter;

    // Keep track of the users on screen
    private ArrayList<UserCardView> userCardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // LOGOUT MENU IMPLEMENTATION - Force update the menu
        invalidateOptionsMenu();

        usersRecyclerView = findViewById(R.id.usersListRecyclerView);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Load the users and information into the list
        loadCardList();

        // TODO: Create an adapater and pass the delete action listener

        // TODO: Attach the adapter to the recycler view

    }


    /**
     * Gets data from the database and stores the information to display on each card
     */
    private void loadCardList() {
        // Avoid duplicate information
        userCardList.clear();

        // Cycle through each user in the database
        List<User> usersInDatabase = AppDatabase.getDatabase(AdminActivity.this).userDao().getAllUsers();

        for (User currenUser : usersInDatabase) {
            UserCardView userCard = new UserCardView();
            // Get the User's username
            userCard.setUsername(currenUser.getUsername());

            // Get the User's Pet and its name from the petDao
            Pet userPet = AppDatabase.getDatabase(AdminActivity.this).PetDao().getPetFromOwnerUserId(currenUser.getUserId());
            userCard.setPetName(userPet.getName());

            // Get the three most recent actions and format the strings

            // Get the image res id to display
            userCard.setColorRes(getResFromColor(userPet.getPet_color()));

            // Add the new user to the list

        }
    }

    /**
     * Returns an ID for a drawable image given the string of the color
     * @param color The color of the pet as a string
     * @return the ID of the color's drawable resource
     */
    private int getResFromColor(String color) {
        switch(color) {
            case "green":
                return R.drawable.button_circle_mint;
            case "pink":
                return R.drawable.button_circle_pink;
            case "blue":
                return R.drawable.button_circle_blue;
            case "purple":
                return R.drawable.button_circle;
            default:
                return -1;
        }
    }


    // vvv vvv vvv LOGOUT MENU IMPLEMENTATION vvv vvv vvv
    // When copying the menu implementation, you must also include the following line in OnCreate:
    // invalidateOptionsMenu(); // Force update the menu
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

        // Retrieve the username from the previous activity (login or signup)
        Intent loginIntent = getIntent();
        String username = loginIntent.getStringExtra("USERNAME");

        // Use the collected username and set the Menu bar title to it
        item.setTitle(username);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminActivity.this);
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
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
    }
    // ^^^ ^^^ ^^^ LOGOUT MENU IMPLEMENTATION ^^^ ^^^ ^^^
}