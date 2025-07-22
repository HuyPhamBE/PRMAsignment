package com.example.prmasignment.ui.cart;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.example.prmasignment.R;
import com.example.prmasignment.utils.CartUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Custom FloatingActionButton for adding products to cart
 * Usage: Include this in product detail layouts and set productId
 */
public class AddToCartFab extends FloatingActionButton {
    
    private Long productId = -1L;
    private int quantity = 1;
    
    public AddToCartFab(Context context) {
        super(context);
        init();
    }
    
    public AddToCartFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public AddToCartFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        setImageResource(R.drawable.ic_cart);
        setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark)));
        setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), android.R.color.white)));
        
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productId != -1L) {
                    if (CartUtils.isUserLoggedIn(getContext())) {
                        CartUtils.addProductToCart(getContext(), productId, quantity);
                    } else {
                        // Handle not logged in case
                        android.widget.Toast.makeText(getContext(), 
                            "Please login to add items to cart", 
                            android.widget.Toast.LENGTH_SHORT).show();
                    }
                } else {
                    android.widget.Toast.makeText(getContext(), 
                        "Product ID not set", 
                        android.widget.Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public int getQuantity() {
        return quantity;
    }
}
