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

public class AppRepository {
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

    public User getUserByUserName(String username) {
        Future<User> future = AppDatabase.databaseWriteExecutor.submit(() -> userDAO.getUserByUserName(username));
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i("PROBLEM", "Problem getting user by username.");
        }
        return null;
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }


    public void insertItem(Item item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            itemDAO.insert(item);
        });
    }

    public Item getItemById(int itemId) {
        Future<Item> future = AppDatabase.databaseWriteExecutor.submit(() -> itemDAO.getItemById(itemId));
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i("PROBLEM", "Problem getting item by id.");
        }
        return null;
    }

    public void updateItem(Item currentItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            itemDAO.update(currentItem);
        });
    }

    public void deleteItem(Item currentItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            itemDAO.delete(currentItem);
        });
    }

    public LiveData<List<ShoppingList>> getListsByUserId(int userId) {
        return listDAO.getListsByUserId(userId);
    }

    public long insertList(ShoppingList list){
        Future<Long> future = AppDatabase.databaseWriteExecutor.submit(() -> listDAO.insert(list));
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i("PROBLEM", "Problem inserting shopping list.");
        }
        return -1;
    }

    public LiveData<List<User>> getNonAdminUsers(){
        return userDAO.getNonAdminUsers();
    }

    public User getUserByIdNow(int userId){
        Future<User> future = AppDatabase.databaseWriteExecutor.submit(() -> userDAO.getUserByIdNow(userId));
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i("PROBLEM", "Problem getting user by id.");
        }
        return null;
    }

    public void updateUser(User user){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.update(user);
        });
    }

    public LiveData<List<Item>> getItemsForList(int listId){
        return itemDAO.getItemsForList(listId);
    }
}
