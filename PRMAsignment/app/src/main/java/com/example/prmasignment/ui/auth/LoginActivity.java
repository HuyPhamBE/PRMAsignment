package com.example.prmasignment.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.R;
import com.example.prmasignment.ui.auth.admin.AdminActivity;
import com.google.gson.Gson;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private EditText editUsername, editPassword;
    private Button buttonLogin;
    private LoginViewModel viewModel;
    private SessionManager sessionManager;

    private Button buttonGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        sessionManager = new SessionManager(this);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        buttonLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            viewModel.login(username, password);
        });

        buttonGoToRegister = findViewById(R.id.buttonGoToRegister);
        buttonGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });


        viewModel.loginResult.observe(this, loginResponse -> {
            Log.d("LoginActivity", "Login Response: " + new Gson().toJson(loginResponse));
            if (loginResponse != null && loginResponse.isAuthenticated()) {
                sessionManager.saveToken(loginResponse.getToken());
                sessionManager.saveRole(loginResponse.getRole()); // NEW
                sessionManager.saveUsername(loginResponse.getUsername()); // Optional

                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();

                switch (loginResponse.getRole().toUpperCase()) {
                    case "ADMIN":
                        startActivity(new Intent(this, AdminActivity.class));
                        break;
                    case "USER":
//                        startActivity(new Intent(this, UserActivity.class)); // <- tạo sau
                        break;
                    default:
                        Toast.makeText(this, "Unknown role", Toast.LENGTH_SHORT).show();
                }
                finish();
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

