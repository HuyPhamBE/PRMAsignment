package com.example.prmasignment.ui.adminUser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.model.UserResponse;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<UserResponse> userList;
    private final OnUserActionListener listener;

    public interface OnUserActionListener {
        void onDelete(UserResponse user);
    }

    public UserAdapter(List<UserResponse> userList, OnUserActionListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    public void setUserList(List<UserResponse> list) {
        this.userList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserResponse user = userList.get(position);
        holder.textUsername.setText(user.getUsername());
        holder.textFullName.setText(user.getFirstName() + " " + user.getLastName());
        holder.textEmail.setText(user.getEmail());
        holder.textPhone.setText(user.getPhoneNumber());

        holder.buttonDelete.setOnClickListener(v -> listener.onDelete(user));
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername, textFullName, textEmail, textPhone;
        MaterialButton buttonDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.textUsername);
            textFullName = itemView.findViewById(R.id.textFullName);
            textEmail = itemView.findViewById(R.id.textEmail);
            textPhone = itemView.findViewById(R.id.textPhone);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
