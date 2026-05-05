package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.databinding.ActivityListBinding;
import com.example.project2_android_app.viewholders.ItemAdapter;

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

        repository = AppRepository.getRepository(getApplication());

        listId = getIntent().getIntExtra(EXTRA_LIST_ID, -1);
        ItemAdapter adapter = new ItemAdapter(
                /* onClick = */ item ->
                        startActivity(EditItemActivity.editItemActivityIntentFactory(this, item.getItemId())),
                /* onCheckChanged = */ (item, checked) -> {
                    item.setBought(checked);
                    repository.updateItem(item);
                });
        binding.recyclerItems.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerItems.setAdapter(adapter);

        binding.buttonAddItem.setOnClickListener(v ->
                startActivity(AddItemActivity.addItemActivityIntentFactory(this, listId)));
    }
}
