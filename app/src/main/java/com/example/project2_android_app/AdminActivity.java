package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.databinding.ActivityAdminBinding;
import com.example.project2_android_app.viewholders.UserAdapter;

public class AdminActivity extends AppCompatActivity {

    private static final String EXTRA_USER_ID = "com.example.project2_android_app.EXTRA_USER_ID";

    private ActivityAdminBinding binding;
    private AppRepository repository;
    private UserAdapter adapter;
    private int userId;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        repository = AppRepository.getRepository(getApplication());

        userId = getIntent().getIntExtra(EXTRA_USER_ID, -1);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        isAdmin = prefs.getBoolean("isAdmin", false);

        if (!isAdmin) {
            Toast.makeText(this, "Admins only", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setTitle("Admin");

        adapter = new UserAdapter(user -> {
            Intent intent = EditUserActivity.editUserActivityIntentFactory(this, user.getId());
            startActivity(intent);
        });

        binding.recyclerUsers.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerUsers.setAdapter(adapter);

        repository.getNonAdminUsers().observe(this, users -> {
            adapter.setUsers(users);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        NavHelper.setupMenu(this, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (NavHelper.handleMenuClick(this, item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Intent adminActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }
}