package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.databinding.ActivityListBinding;
import com.example.project2_android_app.viewholders.ItemAdapter;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Shows the items inside a single ShoppingList.
 *
 * Author: Himansu Yapa
 */
public class ListActivity extends AppCompatActivity {

    private static final String EXTRA_LIST_ID = "com.example.project2_android_app.EXTRA_LIST_ID";
    private static final String EXTRA_USER_ID = "com.example.project2_android_app.EXTRA_USER_ID";

    private ActivityListBinding binding;
    private AppRepository repository;
    private int listId;

    static Intent listActivityIntentFactory(Context context, int listId) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra(EXTRA_LIST_ID, listId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        repository = AppRepository.getRepository(getApplication());

        listId = getIntent().getIntExtra(EXTRA_LIST_ID, -1);

        ItemAdapter adapter = new ItemAdapter(
                item -> startActivity(EditItemActivity.editItemActivityIntentFactory(this, item.getItemId())),
                (item, checked) -> {
                    item.setBought(checked);
                    repository.updateItem(item);
                }
        );

        binding.recyclerItems.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerItems.setAdapter(adapter);

        repository.getItemsForList(listId).observe(this, adapter::submitList);

        binding.buttonAddItem.setOnClickListener(v -> {
            startActivity(AddItemActivity.addItemActivityIntentFactory(this, listId));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        NavHelper.setupMenu(this, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(NavHelper.handleMenuClick(this, item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
