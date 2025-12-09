/**
 * UserTest.java | Testing the User table/entity
 * @author Cristian Perez
 * @since 12/9/25
 */

package com.talentengine.pocketpixelpets.database.entities;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;

import org.junit.Test;

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
        admin.setIs_admin(true);

        assertTrue(admin.isIs_admin());
        assertFalse(user1.isIs_admin());
        assertNotEquals(user1, admin);
    }
}