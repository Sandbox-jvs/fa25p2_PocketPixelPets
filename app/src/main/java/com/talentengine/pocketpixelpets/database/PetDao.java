/**
 * PetDao.java | The Pet Dao interface to implement SQL with the Data Object
 * @author Cristian Perez
 * @since 12/7/25
 */

package com.talentengine.pocketpixelpets.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.talentengine.pocketpixelpets.database.entities.Pet;

import java.util.List;

@Dao
public interface PetDao {
    // Insert a Pet
    @Insert
    public long insertPet(Pet pet);

    // Get a pet given the Pet ID
    @Query("SELECT * FROM pets WHERE pet_id = :petId LIMIT 1")
    public Pet getPetFromPetId(int petId);

    // Get all pets
    @Query("SELECT * FROM pets")
    public List<Pet> getAllPets();

    // Get a pet give the user_id of the owner
    @Query("SELECT * FROM pets WHERE user_id = :ownerUserId")
    public Pet getPetFromOwnerUserId(int ownerUserId);

    @Query("DELETE FROM pets")
    void deleteAll();

    // TODO: REPLACE ALL 'pets' WITH A STATIC VARIABLE
}
