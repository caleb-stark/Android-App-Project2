package com.example.project2_android_app.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_android_app.R;
import com.example.project2_android_app.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();
    private final Consumer<User> onClick;

    public UserAdapter(Consumer<User> onClick){
        this.onClick = onClick;
    }

    public void setUsers(List<User> users){
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position){
        User user = users.get(position);
        holder.bind(user, onClick);
    }

    @Override
    public int getItemCount(){
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView textUsername;

        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            textUsername = itemView.findViewById(R.id.textUsername);
        }

        public void bind(User user, Consumer<User> onClick){
            textUsername.setText(user.getUsername());
            itemView.setOnClickListener(v -> onClick.accept(user));
        }
    }
}