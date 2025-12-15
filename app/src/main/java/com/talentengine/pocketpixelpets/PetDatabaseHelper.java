package com.talentengine.pocketpixelpets;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pocketpixelpets.db";
    private static final int DATABASE_VERSION = 7;

    public static final String TABLE_PETS = "pets";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PET_TYPE = "pet_type";
    public static final String COLUMN_COLOR = "color";

    public static final String COLUMN_PERSONALITY = "pet_personality";

    public static final String COLUMN_FAVORITE_FOOD = "favorite_food";

    public static final String COLUMN_PET_TOY = "pet_toy";
    public static final String COLUMN_BACKGROUND = "background";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CREATED_AT = "created_at";

    private static final String CREATE_TABLE_PETS =
            "CREATE TABLE " + TABLE_PETS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_PET_TYPE + " TEXT NOT NULL, " +
                    COLUMN_COLOR + " TEXT, " +
                    COLUMN_PERSONALITY + " TEXT, " +
                    COLUMN_FAVORITE_FOOD + " TEXT, " +
                    COLUMN_PET_TOY + " TEXT, " +
                    COLUMN_BACKGROUND + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    public PetDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PETS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop and recreate to force update
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        onCreate(db);
    }

    /**
     * Inserts a new pet with just username and pet type.
     * Later steps can fill in color, favorite toy, etc via update methods.
     *
     * @return row ID or -1 on error
     */
    public long insertNewPet(String username, String petType) {
        return insertPetFull(username, petType, null, null, null, null, null, null);
    }

    /**
     * Inserts a new pet with full customization.
     *
     * @return row ID or -1 on error
     */
    public long insertPetFull(String username,
                              String petType,
                              String color,
                              String petPersonality,
                              String favoriteFood,
                              String favoriteToy,
                              String background,
                              String name) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PET_TYPE, petType);
        values.put(COLUMN_COLOR, color);
        values.put(COLUMN_PERSONALITY, petPersonality);
        values.put(COLUMN_FAVORITE_FOOD, favoriteFood);
        values.put(COLUMN_PET_TOY, favoriteToy);
        values.put(COLUMN_BACKGROUND, background);
        values.put(COLUMN_NAME, name);

        return db.insert(TABLE_PETS, null, values);
    }

    /**
     * Update customization fields for a user's pet.
     * Pass null for any field you don't want to change.
     *
     * @return number of rows updated
     */
    public int updatePetCustomization(String username,
                                      String petType,
                                      String color,
                                      String petPersonality,
                                      String favoriteFood,
                                      String favoriteToy,
                                      String background,
                                      String name) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        if (petType != null) {
            values.put(COLUMN_PET_TYPE, petType);
        }
        if (color != null) {
            values.put(COLUMN_COLOR, color);
        }
        if (petPersonality != null) {
            values.put(COLUMN_PERSONALITY, petPersonality);
        }
        if (favoriteFood != null) {
            values.put(COLUMN_FAVORITE_FOOD, favoriteFood);
        }
        if (favoriteToy != null) {
            values.put(COLUMN_PET_TOY, favoriteToy);
        }
        if (background != null) {
            values.put(COLUMN_BACKGROUND, background);
        }
        if (name != null) {
            values.put(COLUMN_NAME, name);
        }

        if (values.isEmpty()) {
            return 0;
        }

        return db.update(
                TABLE_PETS,
                values,
                COLUMN_USERNAME + " = ?",
                new String[]{ username }
        );
    }
}
