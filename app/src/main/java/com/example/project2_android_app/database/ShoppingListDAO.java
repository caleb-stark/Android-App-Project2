package com.example.project2_android_app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2_android_app.database.entities.ShoppingList;

import java.util.List;

@Dao
public interface ShoppingListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ShoppingList list);

    @Delete
    void delete(ShoppingList list);
    @Query("SELECT * FROM " + AppDatabase.LIST_TABLE + " WHERE userId = :userId")
    LiveData<List<ShoppingList>> getListsByUserId(int userId);
}