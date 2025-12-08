package com.talentengine.pocketpixelpets.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "actions")
public class Action {
    @PrimaryKey(autoGenerate = true)
    private int action_id;

    /**
     * The id for the pet the action is tied to
     */
    private int pet_id;

    /**
     * The type of action that was completed
     */
    private String action_type;

    /**
     * The time the action was completed
     */
    private LocalDateTime created_at;

    /**
     * Creates a log of an action given its type and the id of the pet that it was acted upon.
     * <br>
     * The constructor will automatically store the time that the action was performed
     * @param action_type String of the type of action that was completed
     * @param pet_id integer value of the ID of the pet
     */
    public Action(String action_type, int pet_id) {
        this.action_type = action_type;
        this.pet_id = pet_id;

        created_at = LocalDateTime.now();
    }

    public int getAction_id() {
        return action_id;
    }

    public String getAction_type() {
        return action_type;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public int getPet_id() {
        return pet_id;
    }
}
