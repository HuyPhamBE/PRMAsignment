package com.example.prmasignment.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.R;
import com.example.prmasignment.dtos.request.UpdateUserByUsernameRequest;
import com.example.prmasignment.ui.auth.LoginActivity;
import com.example.prmasignment.ui.auth.SessionManager;


public class ProfileActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPhone;
    private Button btnUpdate;
    private ProfileViewModel viewModel;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_profile) {
//                showProfileMenu(); // Tạo hàm này để hiển thị 2 chức năng
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnUpdate = findViewById(R.id.btnUpdate);

        SessionManager sessionManager = new SessionManager(this);

        String token = sessionManager.getToken();
        String username = sessionManager.getUsername();
        Log.d("ProfileActivity", "Username: " + username);

        ProfileViewModelFactory factory = new ProfileViewModelFactory(token);
        viewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);

// ✅ gọi đúng chỗ sau khi đã gán viewModel
        viewModel.getUserByUsername(username);


//        ProfileViewModelFactory factory = new ProfileViewModelFactory(token);
//        viewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);

        viewModel.userLiveData.observe(this, user -> {
            if (user != null) {
                Log.d("ProfileActivity", "User fetched: " + user.getUsername());
                etFirstName.setText(user.getFirstName());
                etLastName.setText(user.getLastName());
                etEmail.setText(user.getEmail());
                etPhone.setText(user.getPhoneNumber());
            } else {
                Log.e("ProfileActivity", "User is null!");
            }
        });


        viewModel.updateResultLiveData.observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, "Update successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            UpdateUserByUsernameRequest request = new UpdateUserByUsernameRequest(
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    username,
                    etEmail.getText().toString(),
                    etPhone.getText().toString(),
                    null,
                    null,
                    null,
                    java.time.LocalDateTime.now().toString()
            );
            viewModel.updateUserProfile(username, request);
        });
    }

//    private void showProfileMenu() {
//        View menuItemView = findViewById(R.id.action_profile); // ID của icon
//        PopupMenu popupMenu = new PopupMenu(this, menuItemView);
//        popupMenu.getMenu().add("View Profile");
//        popupMenu.getMenu().add("Logout");
//
//        popupMenu.setOnMenuItemClickListener(item -> {
//            if (item.getTitle().equals("View Profile")) {
//                // Chuyển sang màn hình Profile
//                startActivity(new Intent(this, ProfileActivity.class));
//                return true;
//            } else if (item.getTitle().equals("Logout")) {
//                // Xử lý logout
//                logout();
//                return true;
//            }
//            return false;
//        });
//        popupMenu.show();
//    }
//
//    private void logout() {
//        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.clear();
//        editor.apply();
//
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }

}
