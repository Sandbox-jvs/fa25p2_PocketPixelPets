/**
 * ActionDao.java | The Action Dao interface to implement SQL with the Data Object
 * @author Cristian Perez
 * @since 12/7/25
 */

package com.talentengine.pocketpixelpets.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.talentengine.pocketpixelpets.database.entities.Action;

import java.util.List;

@Dao
public interface ActionDao {
    // Insert an action TODO: Replace 'actions' with a static variable
    @Insert
    void insertAction(Action action);

    // Get all actions TODO: Replace 'actions' with a static variable
    @Query("SELECT * FROM actions")
    List<Action> getAllActions();

    /** Get all actions from a pet id
     * Given a pet_id, return all of the performed actions
     * @param petId - int, ID of the pet
     * @return A list of the actions
     */
    @Query("SELECT * FROM actions WHERE pet_id = :petId")
    List<Action> getActionsFromPetId(int petId);
}
