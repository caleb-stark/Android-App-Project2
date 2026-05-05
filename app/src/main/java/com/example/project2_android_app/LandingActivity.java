package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {
    private ActivityLandingBinding binding;

    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(v -> {
            startActivity(new Intent(LandingActivity.this, LoginActivity.class));
        });

        binding.createAccountButton.setOnClickListener(v -> {
            startActivity(new Intent(LandingActivity.this, CreateUserActivity.class));
        });
    }

    public static Intent landingActivityIntentFactory(Context context) {
        return new Intent(context, LandingActivity.class);
    }
}