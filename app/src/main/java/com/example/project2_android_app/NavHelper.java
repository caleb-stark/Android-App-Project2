package com.example.project2_android_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class NavHelper {
    public static void setupMenu(AppCompatActivity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.main_menu, menu);

        SharedPreferences prefs = activity.getSharedPreferences("user_prefs", AppCompatActivity.MODE_PRIVATE);
        boolean isAdmin = prefs.getBoolean("isAdmin", false);

        MenuItem adminItem = menu.findItem(R.id.menu_admin);

        if(adminItem != null){
            adminItem.setVisible(isAdmin && !(activity instanceof AdminActivity));
        }
    }

    public static boolean handleMenuClick(AppCompatActivity activity, MenuItem item) {
        SharedPreferences prefs = activity.getSharedPreferences("user_prefs", AppCompatActivity.MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        if(item.getItemId() == R.id.menu_logout){
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = LandingActivity.landingActivityIntentFactory(activity);
            activity.startActivity(intent);
            activity.finish();
            return true;
        }

        if(item.getItemId() == R.id.menu_admin){
            Intent intent = AdminActivity.adminActivityIntentFactory(activity, userId);
            activity.startActivity(intent);
            return true;
        }

        return false;
    }
}