package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.database.entities.Item;
import com.example.project2_android_app.databinding.ActivityAddItemBinding;
import android.view.Menu;
import android.view.MenuItem;
/**
 * Author: Himansu Yapa
 */
public class AddItemActivity extends AppCompatActivity {

    private static final String EXTRA_LIST_ID = "com.example.project2_android_app.EXTRA_LIST_ID";

    private ActivityAddItemBinding binding;
    private AppRepository repository;
    private int listId;

    static Intent addItemActivityIntentFactory(Context context, int listId) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra(EXTRA_LIST_ID, listId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        repository = AppRepository.getRepository(getApplication());
        listId = getIntent().getIntExtra(EXTRA_LIST_ID, -1);

        binding.buttonSaveItem.setOnClickListener(v -> saveItem());
        binding.buttonCancelItem.setOnClickListener(v -> finish());
    }

    private void saveItem() {
        String name = binding.editTextItemName.getText().toString().trim();
        String desc = binding.editTextItemDescription.getText().toString().trim();
        String qtyStr = binding.editTextItemQuantity.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = 1;
        if (!TextUtils.isEmpty(qtyStr)) {
            try { quantity = Integer.parseInt(qtyStr); }
            catch (NumberFormatException ignored) { quantity = 1; }
        }
        if (quantity < 1) quantity = 1;

        Item item = new Item(name, desc, quantity, listId);
        repository.insertItem(item);
        Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
        finish();
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
