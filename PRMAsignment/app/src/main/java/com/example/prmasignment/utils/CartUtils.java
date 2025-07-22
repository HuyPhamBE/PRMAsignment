package com.example.prmasignment.utils;

import android.content.Context;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.example.prmasignment.repository.CartRepository;
import com.example.prmasignment.repository.UserRepository;
import com.example.prmasignment.ui.auth.SessionManager;
import com.example.prmasignment.dtos.response.UserResponse;
import com.example.prmasignment.dtos.response.CartResponse;

/**
 * Utility class for cart operations
 * This can be used by product listings to add items to cart
 */
public class CartUtils {
    
    /**
     * Add a product to cart
     * Call this method from product listing activities when user clicks "Add to Cart"
     */
    public static void addProductToCart(Context context, Long productId, int quantity) {
        android.util.Log.d("CartUtils", "addProductToCart called with productId: " + productId + ", quantity: " + quantity);
        
        SessionManager sessionManager = new SessionManager(context);
        String token = sessionManager.getToken();
        String username = sessionManager.getUsername();
        
        android.util.Log.d("CartUtils", "Token: " + (token != null ? "Present" : "null") + ", Username: " + username);
        
        if (token == null || username == null) {
            android.util.Log.e("CartUtils", "Token or username is null - showing login message");
            Toast.makeText(context, "Please login to add items to cart", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // First get user ID from username
        UserRepository userRepository = new UserRepository(token);
        MutableLiveData<UserResponse> userLiveData = new MutableLiveData<>();
        
        userLiveData.observeForever(userResponse -> {
            if (userResponse != null && userResponse.getUserId() != null) {
                String userId = userResponse.getUserId();
                android.util.Log.d("CartUtils", "Got userId: " + userId);
                
                // Now add to cart with the userId
                CartRepository cartRepository = new CartRepository(token);
                MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
                
                // Observer for the result
                resultLiveData.observeForever(success -> {
                    android.util.Log.d("CartUtils", "Cart add result: " + success);
                    if (success != null && success) {
                        Toast.makeText(context, "Product added to cart!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                    }
                });
                
                // Create and execute request
                android.util.Log.d("CartUtils", "Creating AddToCartRequest with userId: " + userId + ", productId: " + productId + ", quantity: " + quantity);
                com.example.prmasignment.dtos.request.AddToCartRequest request = 
                    new com.example.prmasignment.dtos.request.AddToCartRequest(userId, productId, quantity);
                cartRepository.addToCart(request, resultLiveData);
            } else {
                android.util.Log.e("CartUtils", "Failed to get user information - userResponse: " + userResponse);
                Toast.makeText(context, "Unable to get user information", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Get user by username first
        android.util.Log.d("CartUtils", "Calling getUserByUsername with: " + username);
        userRepository.getUserByUsername(username, userLiveData);
    }
    
    /**
     * Load user cart by username (converts username to userId then loads cart)
     */
    public static void loadUserCart(Context context, String username, MutableLiveData<CartResponse> cartLiveData) {
        SessionManager sessionManager = new SessionManager(context);
        String token = sessionManager.getToken();
        
        android.util.Log.d("CartUtils", "Loading cart for username: " + username);
        
        if (token == null || username == null) {
            android.util.Log.e("CartUtils", "Token or username is null - cannot load cart");
            Toast.makeText(context, "Please log in to view your cart", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // First get user ID from username
        UserRepository userRepository = new UserRepository(token);
        MutableLiveData<UserResponse> userLiveData = new MutableLiveData<>();
        
        userLiveData.observeForever(userResponse -> {
            if (userResponse != null && userResponse.getUserId() != null) {
                String userId = userResponse.getUserId();
                android.util.Log.d("CartUtils", "Got userId for cart loading: " + userId);
                
                // Now load cart with the userId
                CartRepository cartRepository = new CartRepository(token);
                cartRepository.getUserCart(userId, cartLiveData);
            } else {
                android.util.Log.e("CartUtils", "Failed to get user information for cart loading - userResponse: " + userResponse);
                Toast.makeText(context, "Unable to get user information", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Get user by username first
        android.util.Log.d("CartUtils", "Calling getUserByUsername for cart loading with: " + username);
        userRepository.getUserByUsername(username, userLiveData);
    }

    /**
     * Clear user cart by username (converts username to userId then clears cart)
     */
    public static void clearUserCart(Context context, String username, MutableLiveData<Boolean> resultLiveData) {
        SessionManager sessionManager = new SessionManager(context);
        String token = sessionManager.getToken();
        
        android.util.Log.d("CartUtils", "Clearing cart for username: " + username);
        
        if (token == null || username == null) {
            android.util.Log.e("CartUtils", "Token or username is null - cannot clear cart");
            Toast.makeText(context, "Please log in to clear your cart", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // First get user ID from username
        UserRepository userRepository = new UserRepository(token);
        MutableLiveData<UserResponse> userLiveData = new MutableLiveData<>();
        
        userLiveData.observeForever(userResponse -> {
            if (userResponse != null && userResponse.getUserId() != null) {
                String userId = userResponse.getUserId();
                android.util.Log.d("CartUtils", "Got userId for cart clearing: " + userId);
                
                // Now clear cart with the userId
                CartRepository cartRepository = new CartRepository(token);
                cartRepository.clearCart(userId, resultLiveData);
            } else {
                android.util.Log.e("CartUtils", "Failed to get user information for cart clearing - userResponse: " + userResponse);
                Toast.makeText(context, "Unable to get user information", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Get user by username first
        android.util.Log.d("CartUtils", "Calling getUserByUsername for cart clearing with: " + username);
        userRepository.getUserByUsername(username, userLiveData);
    }

    /**
     * Check if user is logged in before allowing cart operations
     */
    public static boolean isUserLoggedIn(Context context) {
        SessionManager sessionManager = new SessionManager(context);
        return sessionManager.getToken() != null && sessionManager.getUsername() != null;
    }
}
