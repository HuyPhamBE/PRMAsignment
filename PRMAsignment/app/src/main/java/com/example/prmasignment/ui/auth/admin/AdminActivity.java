package com.example.prmasignment.ui.auth.admin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.example.prmasignment.R;
import com.example.prmasignment.ui.adminBrand.AdminBrandFragment;
import com.example.prmasignment.ui.adminCate.AdminCategoryFragment;
import com.example.prmasignment.ui.dashboard.AdminDashboardFragment;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Ánh xạ views
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Thiết lập toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        // Xử lý sự kiện click menu
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment selectedFragment = null;

            if (id == R.id.nav_dashboard) {
                selectedFragment = new AdminDashboardFragment();
            } else if (id == R.id.nav_categories) {
                selectedFragment = new AdminCategoryFragment();
            } else if (id == R.id.nav_brands) {
                selectedFragment = new AdminBrandFragment();
            }


            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, selectedFragment)
                        .commit();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Load Fragment lần đầu
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new AdminDashboardFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}