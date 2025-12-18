/**
 * UserDao.java | The User Dao interface to implement SQL with the Data Object
 * @author Cristian Perez
 * @since 12/7/25
 */

package com.talentengine.pocketpixelpets.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.talentengine.pocketpixelpets.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    // Insert data into the table
    @Insert
    long insertUser(User user);

    // Retrieve a user given the username
    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

    // Get a list of all the users
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("DELETE FROM users")
    void deleteAll();

    // Delete the actions where the pet_id is the same pet_id that is linked to the user_id
    @Query("DELETE FROM actions WHERE pet_id = (SELECT pet_id FROM pets WHERE user_id = :userId)")
    void deleteActionsByUserId(int userId);

    // Delete the pet owned by a given user_id
    @Query("DELETE FROM pets WHERE user_id = :userId")
    void deletePetsByUserId(int userId);

    // Delete the user by their id
    @Query("DELETE FROM users WHERE user_id = :userId")
    void deleteUserByUserId(int userId);

    // Delete the user and its pets + actions
    @Transaction
    default void deleteUserFromAllTables(int userId) {
        // First delete all actions
        deleteActionsByUserId(userId);

        // Then delete the pet
        deletePetsByUserId(userId);

        // Then delete the user
        deleteUserByUserId(userId);
    }
}