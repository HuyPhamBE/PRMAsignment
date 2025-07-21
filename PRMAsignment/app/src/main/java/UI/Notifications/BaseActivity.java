package UI.Notifications;


import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prmasignment.model.CartItem;

public class BaseActivity extends AppCompatActivity {

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize notification manager - this handles everything automatically
        notificationManager = new NotificationManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Let notification manager handle the menu
        boolean handled = notificationManager.onCreateOptionsMenu(menu);

        // Call super to allow child activities to add their own menu items
        super.onCreateOptionsMenu(menu);

        return handled;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle notification menu item first
        if (notificationManager.onOptionsItemSelected(item)) {
            return true;
        }

        // Let child activities handle their menu items
        return super.onOptionsItemSelected(item);
    }

    // Helper methods for easy access
    protected void addNotification(CartItem item) {
        notificationManager.addNotification(item);
    }

    protected void clearNotifications() {
        notificationManager.clearNotifications();
    }

}
