package com.example.project2_android_app;

import com.example.project2_android_app.database.entities.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Author: Caleb Stark
 */

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("john", "1234");

        assertEquals("john", user.getUsername());
        assertEquals("1234", user.getPassword());
        assertFalse(user.isAdmin());
    }

    @Test
    public void testSetAdmin() {
        User user = new User("admin", "pass");

        user.setAdmin(true);

        assertTrue(user.isAdmin());
    }
}