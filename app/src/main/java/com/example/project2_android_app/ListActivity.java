package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project2_android_app.database.ShoppingListRepository;
import com.example.project2_android_app.database.entities.ShoppingList;
import com.example.project2_android_app.databinding.ActivityListBinding;
import com.example.project2_android_app.viewholders.ItemAdapter;

public class ListActivity extends AppCompatActivity {

    private static final String EXTRA_LIST_ID = "com.example.project2_android_app.EXTRA_LIST_ID";
    private static final String EXTRA_USER_ID = "com.example.project2_android_app.EXTRA_USER_ID";

    private ActivityListBinding binding;
    private ShoppingListRepository repository;
    private int listId;
    private int userId;

    /** Intent factory. */
    public static Intent getIntent(Context context, int listId, int userId) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra(EXTRA_LIST_ID, listId);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ShoppingListRepository.getRepository(getApplication());

        listId = getIntent().getIntExtra(EXTRA_LIST_ID, -1);
        userId = getIntent().getIntExtra(EXTRA_USER_ID, -1);

        ShoppingList list = repository.getListById(listId);
        if (list != null) {
            binding.textListTitle.setText(list.getListName());
        }

        ItemAdapter adapter = new ItemAdapter(
                /* onClick = */ item ->
                        startActivity(EditItemActivity.getIntent(this, item.getItemId(), listId, userId)),
                /* onCheckChanged = */ (item, checked) -> {
                    item.setBought(checked);
                    repository.updateItem(item);
                });
        binding.recyclerItems.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerItems.setAdapter(adapter);

        repository.getItemsForList(listId).observe(this, adapter::submitList);

        binding.buttonAddItem.setOnClickListener(v ->
                startActivity(AddItemActivity.getIntent(this, listId, userId)));
    }
}
