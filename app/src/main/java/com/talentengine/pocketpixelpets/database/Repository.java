package com.talentengine.pocketpixelpets.database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;

import com.talentengine.pocketpixelpets.MainActivity;
import com.talentengine.pocketpixelpets.database.entities.Pet;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Jessica Sandoval
 * @since 12/08/2025
 */
public class Repository {
    private PetDao petDao;
    private ArrayList<Pet> allPets;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.petDao = db.PetDao();
        this.allPets = (ArrayList<Pet>) this.petDao.getAllPets();

    }

    public ArrayList<Pet> getAllPets() {
        Future<ArrayList<Pet>> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Pet>>() {
                    @Override
                    public ArrayList<Pet> call() throws Exception {
                        return (ArrayList<Pet>) petDao.getAllPets();
                    }
                });
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all pets in the repository");
        }
        return null;
    }
    public void insert(Pet pet) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            petDao.insertPet(pet);
        });
    }
}
