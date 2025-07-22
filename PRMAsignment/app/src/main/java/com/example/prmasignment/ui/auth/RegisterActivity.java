package com.example.prmasignment.ui.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.R;
import com.example.prmasignment.model.RegisterUserRequest;


public class RegisterActivity extends AppCompatActivity {
    private EditText editEmail, editPhoneNumber, editUsername, editFirstName, editLastName, editPassword;
    private Button buttonRegister;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.editEmail);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editUsername = findViewById(R.id.editUsername);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPassword = findViewById(R.id.editPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        buttonRegister.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String phoneNumber = editPhoneNumber.getText().toString().trim();
            String username = editUsername.getText().toString().trim();
            String firstName = editFirstName.getText().toString().trim();
            String lastName = editLastName.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            RegisterUserRequest request = new RegisterUserRequest(email, phoneNumber, username, firstName, lastName, password);
            viewModel.register(request);
        });

        viewModel.registerResult.observe(this, result -> {
            if (result != null && result.isSuccess()) {
                Toast.makeText(this, "Register success!", Toast.LENGTH_SHORT).show();
                finish(); // Quay lại LoginActivity
            } else {
                Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
