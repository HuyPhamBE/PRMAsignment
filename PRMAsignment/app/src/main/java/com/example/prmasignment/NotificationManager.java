package com.example.prmasignment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.prmasignment.model.CartItem;

public class NotificationManager {
    private static final String NOTIFICATION_FRAGMENT_TAG = "notification_fragment";
    private AppCompatActivity activity;
    private NotificationFragment notificationFragment;

    public NotificationManager(AppCompatActivity activity) {
        this.activity = activity;
        initializeFragment();
    }

    private void initializeFragment() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        notificationFragment = (NotificationFragment) fragmentManager.findFragmentByTag(NOTIFICATION_FRAGMENT_TAG);

        if (notificationFragment == null) {
            // Create new fragment
            notificationFragment = NotificationFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(notificationFragment, NOTIFICATION_FRAGMENT_TAG)
                    .commitNow();
        }
    }

    public void setupNotificationMenu(Menu menu) {

    }

    private void showNotificationDropdown() {
        // Try to find the menu item view as anchor
        View anchor = activity.findViewById(R.id.notificationFragment);

        if (notificationFragment != null) {
            notificationFragment.showNotificationsDropdown(anchor);
        }
    }

    public void addNotification(CartItem item) {
        if (notificationFragment != null) {
            notificationFragment.addNotification(item);
        }
    }

    public void clearNotifications() {
        if (notificationFragment != null) {
            notificationFragment.clearNotifications();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.notification_bell, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notificationFragment) {
            showNotificationDropdown();
            return true;
        }

        return false;
    }
}