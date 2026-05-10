package com.example.project2_android_app;

import com.example.project2_android_app.database.entities.Item;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Author: Himansu Yapa
 */
public class ItemTest {

    @Test
    public void testItemCreation() {
        Item item = new Item("Milk", "Dairy item", 2, 1);

        assertEquals("Milk", item.getItemName());
        assertEquals("Dairy item", item.getDescription());
        assertEquals(2, item.getQuantity());
        assertEquals(1, item.getListId());
        assertFalse(item.isBought());
    }

    @Test
    public void testSetBought() {
        Item item = new Item("Eggs", "Breakfast", 1, 1);

        item.setBought(true);

        assertTrue(item.isBought());
    }
}