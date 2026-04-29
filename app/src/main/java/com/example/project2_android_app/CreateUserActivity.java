package com.example.project2_android_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.database.entities.User;
import com.example.project2_android_app.databinding.ActivityCreateUserBinding;

public class CreateUserActivity extends AppCompatActivity {

    private ActivityCreateUserBinding binding;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.createUserButton.setOnClickListener(v -> {
            String username = binding.userNameCreateEditText.getText().toString().trim();
            String password = binding.passwordCreateEditText.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            User existingUser = repository.getUserByUserName(username);

            if(existingUser != null){
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(username, password);
            repository.insertUser(newUser);

            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", username);
            editor.putBoolean("isAdmin", false);
            editor.apply();

            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();

            Intent intent = LoginActivity.loginActivityIntentFactory(CreateUserActivity.this);
            startActivity(intent);
            finish();
        });
    }
}