package com.talentengine.pocketpixelpets.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.talentengine.pocketpixelpets.MainActivity;
import com.talentengine.pocketpixelpets.database.entities.Action;
import com.talentengine.pocketpixelpets.database.entities.Pet;
import com.talentengine.pocketpixelpets.database.entities.User;
import com.talentengine.pocketpixelpets.database.typeConverter.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jessica Sandoval
 * @since 12/07/2025
 */
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {Pet.class, Action.class, User.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "pixelPets_database";
    //Volatile data will only ever live in RAM and ensure thread visibility; this is where the singleton lives
    private static volatile AppDatabase INSTANCE;

    public static final String PETS_TABLE = "pets";
    /**
     * We don't want to run multiple processes on the main UI thread. One thread is used to display
     * the application to the user. If this same thread is used for another process, such as query
     * the database, the UI can freeze trying to get results back from the database.
     */
    public static final int NUMBER_OF_THREADS = 4;

    /** Child processee that will run in the background. Create a service that will supply threads for us
     * to do database operations. Create them all at startup and put them in a pool (Executors), and we do
     * need to do database operations, pull something out of the pool (newFixedThreadPool), then return it
     * to the pool when finished.
     */
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Singleton Accessor - Avoids having multiple instances of the database in memory at a given time.  Prevents race conditions
     * from occurring where two process conflict - Ex: One is trying to query from a table while another
     * attempts to write.
     * @param context
     * @return an instance of out virtual pet database
     */
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            // synchronized ensures that the database class supplied is locked into a single thread
            synchronized (AppDatabase.class) {
                //Make sure it's still null after synchronization and that nothing else made a reference to it
                if(INSTANCE == null) {
                    Log.d(MainActivity.TAG, "Creating Room database instance...");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }

        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDao dao = INSTANCE.userDao();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setIs_admin(true);
                dao.insertUser(admin);

                User testUser1 = new User("testuser1","testuser1");
                dao.insertUser(testUser1);
            });
        }
    };


    public abstract PetDao PetDao();
    public abstract ActionDao actionDao();
    public abstract UserDao userDao();
}
