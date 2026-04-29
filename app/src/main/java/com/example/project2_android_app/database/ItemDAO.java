package com.example.project2_android_app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2_android_app.database.entities.Item;

import java.util.List;

/**
 * Data Access Object for the Item table.
 *
 * Author: Himansu Yapa
 */
@Dao
public interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE itemId = :id LIMIT 1")
    Item getItemById(int id);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE listId = :listId ORDER BY isBought ASC, itemName ASC")
    LiveData<List<Item>> getItemsForList(int listId);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE listId = :listId")
    List<Item> getItemsForListSync(int listId);

    @Query("SELECT COUNT(*) FROM " + AppDatabase.ITEM_TABLE + " WHERE listId = :listId")
    int countItemsForList(int listId);
}
