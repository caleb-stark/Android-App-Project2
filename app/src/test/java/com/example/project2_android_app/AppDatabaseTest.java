package com.example.project2_android_app;

import com.example.project2_android_app.database.AppDatabase;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Author: Caleb Stark
 */
public class AppDatabaseTest {

    @Test
    public void testDatabaseClassExists() {
        assertNotNull(AppDatabase.class);
    }
}