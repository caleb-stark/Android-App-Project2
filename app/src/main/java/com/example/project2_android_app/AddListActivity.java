package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.database.entities.ShoppingList;
import com.example.project2_android_app.databinding.ActivityAddListBinding;

public class AddListActivity extends AppCompatActivity {
    private ActivityAddListBinding binding;
    private AppRepository repository;

    public static final String EXTRA_USER_ID = "com.example.project2_android_app.EXTRA_USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        repository = AppRepository.getRepository(getApplication());

        int userId = getIntent().getIntExtra(EXTRA_USER_ID, -1);

        binding.buttonSaveList.setOnClickListener(v -> {
            String listName = binding.editTextListName.getText().toString().trim();

            if(listName.isEmpty()){
                Toast.makeText(this, "Enter a list name", Toast.LENGTH_SHORT).show();
                return;
            }

            if(userId == -1){
                Toast.makeText(this, "User not found. Please log in again.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            ShoppingList shoppingList = new ShoppingList(listName, userId);
            long newListId = repository.insertList(shoppingList);

            if(newListId == -1){
                Toast.makeText(this, "List could not be added", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "List added", Toast.LENGTH_SHORT).show();
            finish();
        });

        binding.buttonCancelList.setOnClickListener(v -> {
            finish();
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

    public static Intent addListActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, AddListActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }
}