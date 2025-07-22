package com.example.prmasignment.utils;

import android.content.Context;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.example.prmasignment.repository.CartRepository;
import com.example.prmasignment.ui.auth.SessionManager;

/**
 * Utility class for cart operations
 * This can be used by product listings to add items to cart
 */
public class CartUtils {
    
    /**
     * Add a product to cart
     * Call this method from product listing activities when user clicks "Add to Cart"
     */
    public static void addProductToCart(Context context, int productId, int quantity) {
        SessionManager sessionManager = new SessionManager(context);
        String token = sessionManager.getToken();
        String username = sessionManager.getUsername();
        
        if (token == null || username == null) {
            Toast.makeText(context, "Please login to add items to cart", Toast.LENGTH_SHORT).show();
            return;
        }
        
        CartRepository cartRepository = new CartRepository(token);
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        
        // Observer for the result
        resultLiveData.observeForever(success -> {
            if (success != null && success) {
                Toast.makeText(context, "Product added to cart!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Create and execute request
        com.example.prmasignment.dtos.request.AddToCartRequest request = 
            new com.example.prmasignment.dtos.request.AddToCartRequest(username, productId, quantity);
        cartRepository.addToCart(request, resultLiveData);
    }
    
    /**
     * Check if user is logged in before allowing cart operations
     */
    public static boolean isUserLoggedIn(Context context) {
        SessionManager sessionManager = new SessionManager(context);
        return sessionManager.getToken() != null && sessionManager.getUsername() != null;
    }
}
