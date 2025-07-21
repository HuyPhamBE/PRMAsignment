package com.example.prmasignment.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prmasignment.R;
import com.example.prmasignment.ui.profile.ProfileActivity;

import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu); // file menu_profile.xml
        return true;
    }

    // Handle icon click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            // Show popup menu
            androidx.appcompat.widget.PopupMenu popup = new androidx.appcompat.widget.PopupMenu(this, findViewById(R.id.action_profile));
            popup.getMenuInflater().inflate(R.menu.menu_profile_popup, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_view_profile) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    return true;
                } else if (menuItem.getItemId() == R.id.menu_logout) {
                    SessionManager sessionManager = new SessionManager(this);
                    sessionManager.clearSession();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            });
            popup.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
