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
    @Insert
    void insertAction(Action action);

    // Get all actions
    @Query("SELECT * FROM actions")
    List<Action> getAllActions();

    /** Get all actions from a pet id
     * Given a pet_id, return all of the performed actions
     * @param petId the identifier of the pet
     * @return A list of the actions
     */
    @Query("SELECT * FROM actions WHERE pet_id = :petId")
    List<Action> getActionsFromPetId(int petId);

    /**
     * Given a pet id, return the 3 most recently performed actions sorted by newest
     * @param petId the identifier of the pet
     * @return A list of the three newest actions
     */
    @Query("SELECT * FROM actions WHERE pet_id = :petId ORDER BY action_id DESC LIMIT 3")
    List<Action> getLastThreeActionsFromPetId(int petId);

    /**
     * Given a pet id, delete all of its actions
     * @param petId the identifier of the pet
     */
    @Query("DELETE FROM actions WHERE pet_id = :petId")
    void deleteActionsFromPetId(int petId);
}
