package com.example.project2_android_app.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project2_android_app.database.entities.Item;
import com.example.project2_android_app.database.entities.ShoppingList;
import com.example.project2_android_app.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository{
    private final UserDAO userDAO;
    private final ItemDAO itemDAO;
    private final ShoppingListDAO listDAO;

    private static AppRepository repository;

    private AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.itemDAO = db.itemDAO();
        this.listDAO = db.listDAO();
    }

    public static AppRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<AppRepository> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<AppRepository>() {
                    @Override
                    public AppRepository call() throws Exception {
                        return new AppRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i("PROBLEM", "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public void insertUser(User... user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    public User getUserByUserName(String username){
        Future<User> future = AppDatabase.databaseWriteExecutor.submit(() -> userDAO.getUserByUserName(username));
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i("PROBLEM", "Problem getting user by username.");
        }
        return null;
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }


    public void insertItem(Item item) {
        itemDAO.insert(item);
    }

    public Item getItemById(int itemId) {
        return itemDAO.getItemById(itemId);
    }

    public void updateItem(Item currentItem) {
        itemDAO.update(currentItem);
    }

    public void deleteItem(Item currentItem) {
        itemDAO.delete(currentItem);
    }

    public LiveData<List<ShoppingList>> getListsByUserId(int userId){
        return listDAO.getListsByUserId(userId);
    }

    public void insertList(ShoppingList list){
        listDAO.insert(list);
    }
}
