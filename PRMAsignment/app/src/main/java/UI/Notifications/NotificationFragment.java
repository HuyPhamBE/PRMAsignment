package UI.Notifications;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    private PopupWindow notificationPopup;
    List<CartItem> cartItems;
    private RecyclerView noti_recycler;
    private GenericAdapter<CartItem> adapter;
    public NotificationFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartItems = new ArrayList<>();
    }

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // This fragment doesn't need a visible view
        return null;
    }

    public void showNotificationsDropdown(View anchor) {
        View content = LayoutInflater.from(getContext())
                .inflate(R.layout.dropdown_nofication, null);


        // Find RecyclerView in the inflated content, not in the activity
        noti_recycler = content.findViewById(R.id.recylerView_notification);
         adapter = new GenericAdapter<>(cartItems, R.layout.item_list_cart_item, new GenericAdapter.Binder<CartItem>(){
            @Override
            public void bind(CartItem item, View itemView, int position) {
                // Find views in itemView, not in activity
                TextView txtProductId = itemView.findViewById(R.id.txtCart_productId);
                TextView txtQuantity = itemView.findViewById(R.id.txtCart_quantity);

                txtProductId.setText(new StringBuilder().append("Product: ").append(item.getProductId()).toString());
                txtQuantity.setText(new StringBuilder().append("Quantity: ").append(item.getQuantity()).toString());

                txtProductId.setTextColor(0xffFFFFFF);
                txtQuantity.setTextColor(0xffFFFFFF);
            }
        });

        noti_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        noti_recycler.setAdapter(adapter);

        notificationPopup = new PopupWindow(content,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        notificationPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        notificationPopup.setOutsideTouchable(true);
        notificationPopup.showAsDropDown(anchor);
    }
    public void addNotification(CartItem item) {
        if (cartItems != null) {
            cartItems.add(item);
        }
    }

    public void clearNotifications() {
        if (cartItems != null) {
            cartItems.clear();
        }
    }
}
