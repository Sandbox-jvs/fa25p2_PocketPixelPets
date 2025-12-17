/**
 * UserTest.java | Testing the User table/entity
 * @author Cristian Perez
 * @since 12/9/25
 */

package com.talentengine.pocketpixelpets.database.entities;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;

public class UserTest extends TestCase {
    String username1 = "Username";
    String password1 = "Password";
    /**
     * This generated user was constructed without using the admin argument, so is_admin == false
     */
    User user1 = new User(username1, password1);

    /**
     * This generated user will be updated with setIs_admin
     */
    User admin = new User(username1, password1);



    public void testIsAdmin() {
        admin.setAdmin(true);

        assertTrue(admin.isAdmin());
        assertFalse(user1.isAdmin());
        assertNotEquals(user1, admin);
    }
}