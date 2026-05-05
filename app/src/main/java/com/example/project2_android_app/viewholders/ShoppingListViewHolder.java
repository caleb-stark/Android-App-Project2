package com.example.project2_android_app.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_android_app.R;
import com.example.project2_android_app.database.entities.ShoppingList;

import java.util.function.Consumer;

public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

    private final TextView textListName;

    public ShoppingListViewHolder(@NonNull View itemView){
        super(itemView);
        textListName = itemView.findViewById(R.id.textListName);
    }

    public void bind(ShoppingList list, Consumer<ShoppingList> onClick){
        textListName.setText(list.getName());
        itemView.setOnClickListener(v -> onClick.accept(list));
    }
}