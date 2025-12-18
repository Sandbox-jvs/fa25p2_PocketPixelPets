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

        // Load the users and information into the list
        loadCardList();

        // TODO: Create an adapater and pass the delete action listener
        adapter = new UserCardViewAdapter(userCardList, (user, position) -> {
            // When the delete button is selected, we will take care of the dialog and confirmation
            showDeleteConfirmationDialog(user, position);
        })

        // TODO: Attach the adapter to the recycler view
        usersRecyclerView.setAdapter(adapter);

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
            assignThreeMostRecentActions(userCard, userPet);

            // Get the image res id to display
            userCard.setColorRes(getResFromColor(userPet.getPet_color()));

            // Add the new user card to the list
            userCardList.add(userCard);
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

    /**
     * Given a user card to update and the user's pet, retrieve the 3 most recent (if applicable) actions and format it
     * @param userCard the card to update
     * @param userPet the pet of the user
     */
    private void assignThreeMostRecentActions(UserCardView userCard, Pet userPet) {
        // In case the pet doesn't have three actions, set the actions to default display
        userCard.setFirstAction("No action here...");
        userCard.setSecondAction("No action here...");
        userCard.setThirdAction("No action here...");

        // Get the three most recent actions from a given pet id
        List<Action> actions = AppDatabase.getDatabase(AdminActivity.this)
                .actionDao().getLastThreeActionsFromPetId(userPet.getPet_id());

        // Check the size and display accordingly
        // The toString() of Action has been overridden to the format of "MM-dd-yyyy HH:mm:ss"
        if (!actions.isEmpty()) {
            userCard.setFirstAction(actions.get(0).getAction_type());

            if (actions.size() >= 2) {
                userCard.setSecondAction(actions.get(1).getAction_type());

                if (actions.size() >= 3) {
                    userCard.setThirdAction(actions.get(2).getAction_type());
                }
            }
        }
    }

    /**
     * Since the recycler view adapter doesn't handle the actual implementation of deleting users,
     * we must implement that functionality here.
     * <br>
     * This method shows a dialog to the admin to confirm that they want the selected user to be
     * deleted. If so, that user, their pet, and their actions will be removed from the database
     * and list.
     */
    private void showDeleteConfirmationDialog(UserCardView userCard, int position) {
        // Setup a dialog menu, and if they want to confirm the deletion then direct to that method

        new android.app.AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete + " + userCard.getUsername() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    deleteUserFromDatabase(userCard, position);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /**
     * Given a user's card and their position in the list, remove them from the database
     * @param userCard the card of the user that is to be removed
     * @param position the position of the user in the card list
     */
    private void deleteUserFromDatabase(UserCardView userCard, int position) {
        // Remove the user from the repository


        // Remove the user from the card list
        adapter.notifyItemRemoved(position);

        // Update the adapter to account for the removed item and ensure it doesn't change position
        adapter.notifyItemRangeChanged(position, userCardList.size());
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