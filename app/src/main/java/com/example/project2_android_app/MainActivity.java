package com.example.project2_android_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project2_android_app.database.AppRepository;
import com.example.project2_android_app.databinding.ActivityMainBinding;
import com.example.project2_android_app.viewholders.ShoppingListAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2_android_app.MAIN_ACTIVITY_USER_ID";
    private static final int LOGGED_OUT = -1;

    private ActivityMainBinding binding;
    private AppRepository repository;
    private int loggedInUserId = -1;
    private boolean isAdmin = false;
    private ShoppingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        repository = AppRepository.getRepository(getApplication());
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        if(loggedInUserId == LOGGED_OUT){
            Intent intent = LoginActivity.loginActivityIntentFactory(MainActivity.this);
            startActivity(intent);
            finish();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = prefs.getString("username", "Unknown User");
        isAdmin = prefs.getBoolean("isAdmin", false);

        setTitle(username);

        adapter = new ShoppingListAdapter(list ->
                startActivity(ListActivity.listActivityIntentFactory(this, list.getId()))
        );

        binding.recyclerShoppingLists.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerShoppingLists.setAdapter(adapter);

        binding.buttonAddList.setOnClickListener(v ->
                startActivity(AddListActivity.addListActivityIntentFactory(this, loggedInUserId))
        );

        loadShoppingLists();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadShoppingLists();
    }

    private void loadShoppingLists(){
        repository.getListsByUserId(loggedInUserId).observe(this, lists -> {
            adapter.setLists(lists);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem adminItem = menu.findItem(R.id.menu_admin);
        if(adminItem != null){
            adminItem.setVisible(isAdmin);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_logout){
            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = LandingActivity.landingActivityIntentFactory(MainActivity.this);
            startActivity(intent);
            finish();
            return true;
        }

        if(item.getItemId() == R.id.menu_admin){
            Intent intent = AdminActivity.adminActivityIntentFactory(MainActivity.this);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}