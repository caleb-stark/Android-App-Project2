package com.example.project2_android_app.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Represents an item on a shopping list (e.g. "Milk", "Bread").
 * Each item belongs to exactly one ShoppingList via a foreign key on listId.
 *
 * Author: Himansu Yapa
 */
@Entity(
        tableName = Item.TABLE_NAME,
        foreignKeys = @ForeignKey(
                entity = ShoppingList.class,
                parentColumns = "listId",
                childColumns = "listId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("listId")}
)
public class Item {

    public static final String TABLE_NAME = "item_table";

    @PrimaryKey(autoGenerate = true)
    private int itemId;

    @ColumnInfo(name = "itemName")
    @NonNull
    private String itemName;

    @ColumnInfo(name = "description")
    @NonNull
    private String description;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "isBought")
    private boolean isBought;

    @ColumnInfo(name = "listId")
    private int listId;

    public Item(@NonNull String itemName, @NonNull String description, int quantity, int listId) {
        this.itemName = itemName;
        this.description = description;
        this.quantity = quantity;
        this.listId = listId;
        this.isBought = false;
    }

    public int getItemId() { return itemId; }
    @NonNull public String getItemName() { return itemName; }
    @NonNull public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public boolean isBought() { return isBought; }
    public int getListId() { return listId; }

    public void setItemId(int itemId) { this.itemId = itemId; }
    public void setItemName(@NonNull String itemName) { this.itemName = itemName; }
    public void setDescription(@NonNull String description) { this.description = description; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setBought(boolean bought) { this.isBought = bought; }
    public void setListId(int listId) { this.listId = listId; }
}
