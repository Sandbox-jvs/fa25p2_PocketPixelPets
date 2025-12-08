/**
 * UserDao.java | The User Dao interface to implement SQL with the Data Object
 * @author Cristian Perez
 * @since 12/7/25
 */

package com.talentengine.pocketpixelpets.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talentengine.pocketpixelpets.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    // Insert data into the table
    @Insert
    int insertUser(User user);

    // Retrieve a user given the username
    //TODO: Change 'users' to be a static variable from the database
    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

    // Get a list of all the users
    //TODO: Change 'users' to be a static variable from the database
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    // TODO: Delete user
}