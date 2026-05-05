package com.example.project2_android_app.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_android_app.database.AppDatabase;

@Entity(tableName = AppDatabase.LIST_TABLE)
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int userId;

    public ShoppingList(@NonNull String name, int userId){
        this.name=name;
        this.userId=userId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
