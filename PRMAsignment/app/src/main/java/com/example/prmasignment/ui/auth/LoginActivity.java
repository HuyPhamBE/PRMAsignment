package com.example.prmasignment.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.R;
import com.example.prmasignment.ui.auth.admin.AdminActivity;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private EditText editUsername, editPassword;
    private Button buttonLogin;
    private LoginViewModel viewModel;
    private SessionManager sessionManager;

    private Button buttonGoToRegister;
    public String getRoleFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) return null;

            String payload = new String(android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE));

            // Parse JSON payload
            JSONObject jsonObject = new JSONObject(payload);
            return jsonObject.getString("role"); // giả sử claim tên là "role"
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
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
            if (loginResponse != null && loginResponse.isAuthenticated()) {
                sessionManager.saveToken(loginResponse.getToken());
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();

                // Decode token để lấy role
                String role = getRoleFromToken(loginResponse.getToken());
                if (role == null) {
                    Toast.makeText(this, "Cannot read role", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("ADMIN".equalsIgnoreCase(role)) {
                    startActivity(new Intent(this, AdminActivity.class));
                } else if ("USER".equalsIgnoreCase(role)) {
//                    startActivity(new Intent(this, UserActivity.class));
                } else {
                    Toast.makeText(this, "Unknown role", Toast.LENGTH_SHORT).show();
                }
                finish();
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

