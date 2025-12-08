package com.talentengine.pocketpixelpets.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

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

    public LocalDateTime getCreated_at() {
        return created_at;
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
}

