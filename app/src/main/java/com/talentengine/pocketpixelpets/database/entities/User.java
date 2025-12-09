/**
 * User.java | The table of users and the User entity
 * @author Cristian Perez
 * @since 12/7/25
 */

package com.talentengine.pocketpixelpets.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int user_id;
    /**
     * The saved password of the user
     */
    private String password;

    /**
     * Stores the date and time the user created their account
     */
    private LocalDateTime created_at;

    /**
     * The username of the account
     */
    private String username;

    /**
     * Boolean variable to declare if the user is an admin, default value is false.
     * <br>
     * The value can be updated by using the related setter, or by using the overloaded constructor
     */
    private boolean is_admin = false;

    /**
     * Given a username and password, generate a user. Automatically collect the current time
     * @param username The username of the user
     * @param password The password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;

        created_at = LocalDateTime.now();
    }

    public User(String username, String password, boolean is_admin) {
        this(username, password);

        this.is_admin = is_admin;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user_id == user.user_id && is_admin == user.is_admin && Objects.equals(password, user.password) && Objects.equals(created_at, user.created_at) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, password, created_at, username, is_admin);
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public boolean isAdmin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

}

