package com.example.project2_android_app.viewholders;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_android_app.R;
import com.example.project2_android_app.database.entities.ShoppingList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder> {

    private List<ShoppingList> lists = new ArrayList<>();
    private final Consumer<ShoppingList> onClick;

    public ShoppingListAdapter(Consumer<ShoppingList> onClick){
        this.onClick = onClick;
    }

    public void setLists(List<ShoppingList> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list_item,parent,false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder,int position){
        ShoppingList list = lists.get(position);

        holder.bind(list,onClick);
    }

    @Override
    public int getItemCount(){
        return lists.size();
    }
}
