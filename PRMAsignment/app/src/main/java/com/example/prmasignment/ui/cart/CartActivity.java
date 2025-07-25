package com.example.prmasignment.ui.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prmasignment.R;
import com.example.prmasignment.model.Cart;
import com.example.prmasignment.model.CartItem;
import com.example.prmasignment.ui.auth.SessionManager;
import com.example.prmasignment.utils.CartUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private CartViewModel viewModel;
    private CartAdapter adapter;
    private RecyclerView recyclerViewCart;
    private TextView textTotalAmount, textTotalItems, textEmptyMessage;
    private MaterialButton buttonClearCart, buttonCheckout;
    private View layoutEmptyCart, layoutCartContent;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupViewModel();
        setupObservers();
        setupClickListeners();

        loadCart();
    }

    private void initViews() {
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        textTotalAmount = findViewById(R.id.textTotalAmount);
        textTotalItems = findViewById(R.id.textTotalItems);
        textEmptyMessage = findViewById(R.id.textEmptyMessage);
        buttonClearCart = findViewById(R.id.buttonClearCart);
        buttonCheckout = findViewById(R.id.buttonCheckout);
        layoutEmptyCart = findViewById(R.id.layoutEmptyCart);
        layoutCartContent = findViewById(R.id.layoutCartContent);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        adapter = new CartAdapter(new ArrayList<>(), new CartAdapter.OnCartItemActionListener() {
            @Override
            public void onQuantityIncrease(CartItem item) {
                viewModel.updateCartItem(item.getCartItemId(), item.getQuantity() + 1);
            }

            @Override
            public void onQuantityDecrease(CartItem item) {
                viewModel.updateCartItem(item.getCartItemId(), item.getQuantity() - 1);
            }

            @Override
            public void onRemoveItem(CartItem item) {
                showRemoveItemDialog(item);
            }
        });

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setAdapter(adapter);
    }

    private void setupViewModel() {
        sessionManager = new SessionManager(this);
        String token = sessionManager.getToken();
        
        CartViewModelFactory factory = new CartViewModelFactory(token);
        viewModel = new ViewModelProvider(this, factory).get(CartViewModel.class);
    }

    private void setupObservers() {
        viewModel.cartLiveData.observe(this, cart -> {
            android.util.Log.d("CartActivity", "Cart received: " + (cart != null ? "not null" : "null"));
            if (cart != null) {
                android.util.Log.d("CartActivity", "Cart items: " + (cart.getCartItems() != null ? cart.getCartItems().size() : "null"));
            }
            
            if (cart != null && cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
                android.util.Log.d("CartActivity", "Showing cart content");
                showCartContent(cart);
            } else {
                android.util.Log.d("CartActivity", "Showing empty cart");
                showEmptyCart();
            }
        });

        viewModel.updateCartItemResult.observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, "Cart updated", Toast.LENGTH_SHORT).show();
                loadCart(); // Reload cart
            } else {
                Toast.makeText(this, "Failed to update cart", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.removeFromCartResult.observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, "Item removed from cart", Toast.LENGTH_SHORT).show();
                loadCart(); // Reload cart
            } else {
                Toast.makeText(this, "Failed to remove item", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.clearCartResult.observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, "Cart cleared", Toast.LENGTH_SHORT).show();
                loadCart(); // Reload cart
            } else {
                Toast.makeText(this, "Failed to clear cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupClickListeners() {
        buttonClearCart.setOnClickListener(v -> showClearCartDialog());
        buttonCheckout.setOnClickListener(v -> {
            // TODO: Implement checkout functionality
            Toast.makeText(this, "Checkout feature coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadCart() {
        String username = sessionManager.getUsername();
        if (username != null) {
            // Use CartUtils to properly convert username to userId and load cart
            CartUtils.loadUserCart(this, username, viewModel.cartLiveData);
        }
    }

    private void showCartContent(Cart cart) {
        layoutEmptyCart.setVisibility(View.GONE);
        layoutCartContent.setVisibility(View.VISIBLE);

        adapter.setCartItems(cart.getCartItems());
        
        // Calculate totals
        int totalItems = 0;
        double totalAmount = 0.0;
        
        if (cart.getCartItems() != null) {
            for (CartItem item : cart.getCartItems()) {
                totalItems += item.getQuantity();
                totalAmount += item.getSubtotal();
            }
        }
        
        textTotalItems.setText(totalItems + " items");
        textTotalAmount.setText(formatCurrency(totalAmount));
    }

    private void showEmptyCart() {
        android.util.Log.d("CartActivity", "Showing empty cart UI");
        layoutEmptyCart.setVisibility(View.VISIBLE);
        layoutCartContent.setVisibility(View.GONE);
    }

    private void showRemoveItemDialog(CartItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Remove Item")
                .setMessage("Are you sure you want to remove " + item.getProductName() + " from your cart?")
                .setPositiveButton("Remove", (dialog, which) -> {
                    viewModel.removeFromCart(item.getCartItemId());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showClearCartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Cart")
                .setMessage("Are you sure you want to remove all items from your cart?")
                .setPositiveButton("Clear", (dialog, which) -> {
                    String username = sessionManager.getUsername();
                    if (username != null) {
                        // Use CartUtils to properly convert username to userId and clear cart
                        CartUtils.clearUserCart(this, username, viewModel.clearCartResult);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }
}
