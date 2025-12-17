/**
 * ActionDaoTest.java | Testing the Data Access Object of the Action entity
 * @author Cristian Perez
 * @since 12/17/25
 */

package com.talentengine.pocketpixelpets;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.talentengine.pocketpixelpets.database.ActionDao;
import com.talentengine.pocketpixelpets.database.AppDatabase;
import com.talentengine.pocketpixelpets.database.entities.Action;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ActionDaoTest {
    private AppDatabase database;
    private ActionDao actionDao;
    private final int testPetId = 42;

    private Action testAction1;
    private Action testAction2;
    private Action testAction3;
    private Action testAction4;


    // In order to test the database, we need to get an application context first
    @Before
    public void initDatabase() {
        Context context = ApplicationProvider.getApplicationContext();

        // Build a database in memory to use for tests
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries().build();

        // Set our local action dao to the new one from database
        actionDao = database.actionDao();
    }

    @Test
    public void testGetLastThreeActionsFromPetId() throws InterruptedException {
        // Make and insert three custom actions
        testAction1 = new Action("action 1", testPetId);
        testAction2 = new Action("action 2", testPetId);
        testAction3 = new Action("action 3", testPetId);
        testAction4 = new Action("action 4", testPetId);

        actionDao.insertAction(testAction1);
        actionDao.insertAction(testAction2);
        actionDao.insertAction(testAction3);
        actionDao.insertAction(testAction4);

        // Make sure we ONLY got 3 actions
        List<Action> actions = actionDao.getLastThreeActionsFromPetId(testPetId);
        assertEquals(3, actions.size());

        // Action 4 should be the newest action, then 3, then 2
        assertEquals("action 4", actions.get(0).getAction_type());
        assertEquals("action 3", actions.get(1).getAction_type());
        assertEquals("action 2", actions.get(2).getAction_type());
    }

    @Test
    public void testDeleteActionsFromPetId() {
        // Add 4 actions to our pet
        testAction1 = new Action("action 1", testPetId);
        testAction2 = new Action("action 2", testPetId);
        testAction3 = new Action("action 3", testPetId);
        testAction4 = new Action("action 4", testPetId);

        actionDao.insertAction(testAction1);
        actionDao.insertAction(testAction2);
        actionDao.insertAction(testAction3);
        actionDao.insertAction(testAction4);

        // Delete the actions for the pet
        actionDao.deleteActionsFromPetId(testPetId);

        // Ensure that retrieving the actions returns an empty list
        assertEquals(0, actionDao.getActionsFromPetId(testPetId).size());

    }

    @After
    public void closeDatabase() {
        database.close();
    }
}