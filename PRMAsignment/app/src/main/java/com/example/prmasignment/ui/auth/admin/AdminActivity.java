package com.example.prmasignment.ui.auth.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prmasignment.R;
import com.example.prmasignment.ui.adminCate.AdminCategoryFragment;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Load Fragment lần đầu
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new AdminCategoryFragment())
                    .commit();
        }
    }
}
