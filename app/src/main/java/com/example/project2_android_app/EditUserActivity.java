package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.database.entities.User;
import com.example.project2_android_app.databinding.ActivityEditUserBinding;

public class EditUserActivity extends AppCompatActivity {
    private static final String EXTRA_USER_ID = "com.example.project2_android_app.EXTRA_EDIT_USER_ID";

    private ActivityEditUserBinding binding;
    private AppRepository repository;
    private User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        int userId = getIntent().getIntExtra(EXTRA_USER_ID, -1);

        if(userId == -1){
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        selectedUser = repository.getUserByIdNow(userId);

        if(selectedUser == null){
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.editTextUsername.setText(selectedUser.getUsername());
        binding.editTextPassword.setText(selectedUser.getPassword());

        binding.buttonSaveUser.setOnClickListener(v -> {
            String username = binding.editTextUsername.getText().toString().trim();
            String password = binding.editTextPassword.getText().toString().trim();

            if(username.isEmpty()){
                Toast.makeText(this, "Enter a username", Toast.LENGTH_SHORT).show();
                return;
            }

            if(password.isEmpty()){
                Toast.makeText(this, "Enter a password", Toast.LENGTH_SHORT).show();
                return;
            }

            selectedUser.setUsername(username);
            selectedUser.setPassword(password);
            repository.updateUser(selectedUser);

            Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    public static Intent editUserActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, EditUserActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }
}