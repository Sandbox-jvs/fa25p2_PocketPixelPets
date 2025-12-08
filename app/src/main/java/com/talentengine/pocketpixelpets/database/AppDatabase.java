package com.talentengine.pocketpixelpets.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.talentengine.pocketpixelpets.database.entities.Pets;
import com.talentengine.pocketpixelpets.database.entities.User;

@Database(entities = {Pets.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
}
