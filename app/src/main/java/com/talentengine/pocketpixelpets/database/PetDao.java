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
    public int insertPet(Pet pet);

    // Get a pet given the Pet ID
    @Query("SELECT * FROM pets WHERE pet_id = :petId LIMIT 1")
    public Pet getPetFromPetId(int petId);

    // Get all pets
    @Query("SELECT * FROM pets")
    public List<Pet> getAllPets();

    // Get a pet give the user_id of the owner
    @Query("SELECT * FROM pets WHERE user_id = :ownerUserId")
    public Pet getPetFromOwnerUserId(int ownerUserId);

    // TODO: remove pet
    // TODO: REPLACE ALL 'pets' WITH A STATIC VARIABLE
}
