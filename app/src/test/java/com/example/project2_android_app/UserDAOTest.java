package com.example.project2_android_app;

import com.example.project2_android_app.database.UserDAO;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Author: Caleb Stark
 */
public class UserDAOTest {

    @Test
    public void testUserDAOExists() {
        assertNotNull(UserDAO.class);
    }
}