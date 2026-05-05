package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.database.entities.ShoppingList;
import com.example.project2_android_app.databinding.ActivityAddListBinding;

public class AddListActivity extends AppCompatActivity {

    private static final String EXTRA_USER_ID = "com.example.project2_android_app.EXTRA_USER_ID";

    private ActivityAddListBinding binding;
    private AppRepository repository;
    private int userId;

    static Intent addListActivityIntentFactory(Context context,int userId) {
        Intent intent = new Intent(context, AddListActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());
        userId = getIntent().getIntExtra(EXTRA_USER_ID, -1);

        binding.buttonSaveList.setOnClickListener(v -> saveList());
        binding.buttonCancelList.setOnClickListener(v -> finish());
    }

    private void saveList() {
        String listName = binding.editTextListName.getText().toString().trim();

        if (TextUtils.isEmpty(listName)) {
            Toast.makeText(this, "Please enter a list name", Toast.LENGTH_SHORT).show();
            return;
        }

        ShoppingList shoppingList = new ShoppingList(listName, userId);
        repository.insertList(shoppingList);
        Toast.makeText(this, "List added", Toast.LENGTH_SHORT).show();
        finish();
    }
}