package com.example.project2_android_app;

import com.example.project2_android_app.database.ItemDAO;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Author: Himansu Yapa
 */

public class ItemDAOTest {

    @Test
    public void testItemDAOExists() {
        assertNotNull(ItemDAO.class);
    }
}