package com.talentengine.pocketpixelpets.database;

import android.app.Application;
import android.util.Log;

import com.talentengine.pocketpixelpets.MainActivity;
import com.talentengine.pocketpixelpets.database.entities.Pet;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * The Repository accesses data from anywhere.
 * @author Jessica Sandoval
 * @since 12/08/2025
 */
public class Repository {
    private static Repository repository;
    private PetDao petDao;
    private ArrayList<Pet> allPets;

    private Repository(Application application) {
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

    public static Repository getRepository(Application application) {
        if(repository != null) {
            return repository;
        }
        Future<Repository> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<Repository>() {
                    @Override
                    public Repository call() throws Exception {
                        return new Repository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting the virtual pet repository; thread error.");
        }
        return null;
    }
}
