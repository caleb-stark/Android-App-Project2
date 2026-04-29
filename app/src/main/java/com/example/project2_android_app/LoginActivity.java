package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.database.entities.User;
import com.example.project2_android_app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    static Intent loginActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString().trim();
        String password = binding.passwordLoginEditText.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){
            return;
        }

        User user = repository.getUserByUserName(username);

        if(user != null){
            if(isValid(user, password)){
                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", user.getUsername());
                editor.putBoolean("isAdmin", user.isAdmin());
                editor.apply();

                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                finish();
            }else{
                binding.passwordLoginEditText.setText("");
            }
        }else{
            binding.passwordLoginEditText.setText("");
        }
    }

    public static boolean isValid(User user, String password){
        if (user == null){
            return false;
        }
        return user.getPassword().equals(password);
    }

}