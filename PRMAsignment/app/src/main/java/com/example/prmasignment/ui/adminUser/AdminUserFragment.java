package com.example.prmasignment.ui.adminUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.model.UserResponse;
import com.example.prmasignment.repository.AdminUserRepository;
import com.example.prmasignment.ui.auth.SessionManager;

import java.util.ArrayList;

public class AdminUserFragment extends Fragment {

    private AdminUserViewModel viewModel;
    private RecyclerView recyclerView;
    private TextView textUserCount;
    private View layoutEmptyState;
    private UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerViewUsers);
        textUserCount = view.findViewById(R.id.textUserCount);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);

        adapter = new UserAdapter(new ArrayList<>(), user -> {
            try {
                int id = Integer.parseInt(user.getUserId());
                viewModel.deleteUser(id);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid user ID", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        String token = new SessionManager(requireContext()).getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(getContext(), "Token is missing. Please log in again.", Toast.LENGTH_LONG).show();
            return;
        }

        AdminUserRepository repo = new AdminUserRepository(token);
        viewModel = new ViewModelProvider(this, new AdminUserViewModelFactory(repo)).get(AdminUserViewModel.class);

        observeData();
        viewModel.fetchUsers();
    }

    private void observeData() {
        viewModel.usersLiveData.observe(getViewLifecycleOwner(), users -> {
            adapter.setUserList(users);
            textUserCount.setText(users != null ? users.size() + " users" : "0 users");
            layoutEmptyState.setVisibility((users == null || users.isEmpty()) ? View.VISIBLE : View.GONE);
        });

        viewModel.deleteUserResult.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Toast.makeText(getContext(), "Deleted: " + user.getFirstName(), Toast.LENGTH_SHORT).show();
                viewModel.fetchUsers(); // Refresh list
            } else {
                Toast.makeText(getContext(), "Failed to delete user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
