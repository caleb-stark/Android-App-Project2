package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.databinding.ActivityMainBinding;
import com.example.project2_android_app.database.entities.User;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2_android_app.MAIN_ACTIVITY_USER_ID";
    private static final int LOGGED_OUT = -1;
    private ActivityMainBinding binding;

    private AppRepository repository;

    int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,-1);

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginActivityIntentFactory(MainActivity.this);
            startActivity(intent);
            finish();
        }
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}