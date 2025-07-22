package com.example.prmasignment.ui.auth.admin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.example.prmasignment.R;
import com.example.prmasignment.ui.adminBrand.AdminBrandFragment;
import com.example.prmasignment.ui.adminCate.AdminCategoryFragment;
import com.example.prmasignment.ui.adminOrder.AdminOrderFragment;
import com.example.prmasignment.ui.adminProduct.AdminProductFragment;
import com.example.prmasignment.ui.adminStoreLoction.AdminStoreLocationFragment;
import com.example.prmasignment.ui.adminUser.AdminUserFragment;
import com.example.prmasignment.ui.auth.LoginActivity;
import com.example.prmasignment.ui.auth.SessionManager;
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
            } else if (id == R.id.nav_product_list) {
                selectedFragment = new AdminProductFragment();
            } else if (id == R.id.nav_users) {
                selectedFragment = new AdminUserFragment();
            } else if (id == R.id.nav_stores) {
                selectedFragment = new AdminStoreLocationFragment();
            }  else if (id == R.id.nav_logout) {
                SessionManager sessionManager = new SessionManager(this);
                sessionManager.clearSession();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa backstack
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_orders) {
                selectedFragment = new AdminOrderFragment();
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