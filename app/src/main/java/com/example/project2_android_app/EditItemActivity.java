package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_android_app.database.ShoppingListRepository;
import com.example.project2_android_app.database.entities.Item;
import com.example.project2_android_app.databinding.ActivityEditItemBinding;

/**
 * Author: Himansu Yapa
 */
public class EditItemActivity extends AppCompatActivity {

    private static final String EXTRA_ITEM_ID = "com.example.project2_android_app.EXTRA_ITEM_ID";

    private ActivityEditItemBinding binding;
    private ShoppingListRepository repository;
    private Item currentItem;

    static Intent editItemActivityIntentFactory(Context context, int itemId) {
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ShoppingListRepository.getRepository(getApplication());

        int itemId = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);
        currentItem = repository.getItemById(itemId);
        if (currentItem == null) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.editTextEditItemName.setText(currentItem.getItemName());
        binding.editTextEditItemDescription.setText(currentItem.getDescription());
        binding.editTextEditItemQuantity.setText(String.valueOf(currentItem.getQuantity()));
        binding.checkboxBought.setChecked(currentItem.isBought());

        binding.buttonSaveChanges.setOnClickListener(v -> saveChanges());
        binding.buttonDeleteItem.setOnClickListener(v -> deleteItem());
    }

    private void saveChanges() {
        String name = binding.editTextEditItemName.getText().toString().trim();
        String desc = binding.editTextEditItemDescription.getText().toString().trim();
        String qtyStr = binding.editTextEditItemQuantity.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = 1;
        if (!TextUtils.isEmpty(qtyStr)) {
            try { quantity = Integer.parseInt(qtyStr); }
            catch (NumberFormatException ignored) { quantity = 1; }
        }

        currentItem.setItemName(name);
        currentItem.setDescription(desc);
        currentItem.setQuantity(quantity);
        currentItem.setBought(binding.checkboxBought.isChecked());
        repository.updateItem(currentItem);

        Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteItem() {
        repository.deleteItem(currentItem);
        Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
