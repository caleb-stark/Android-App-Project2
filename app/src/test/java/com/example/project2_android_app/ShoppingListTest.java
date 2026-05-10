package com.example.project2_android_app;

import com.example.project2_android_app.database.entities.ShoppingList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Author: Luis Cotes
 */
public class ShoppingListTest {

    @Test
    public void testShoppingListCreation() {
        ShoppingList list = new ShoppingList("Groceries", 5);

        assertEquals("Groceries", list.getName());
        assertEquals(5, list.getUserId());
    }

    @Test
    public void testSetName() {
        ShoppingList list = new ShoppingList("Old", 1);

        list.setName("New");

        assertEquals("New", list.getName());
    }
}