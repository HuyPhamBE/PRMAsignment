package com.example.prmasignment.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prmasignment.R;
import com.example.prmasignment.model.CartItem;
import com.google.android.material.button.MaterialButton;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {

    private List<CartItem> cartItems;
    private OnCartItemActionListener listener;

    public interface OnCartItemActionListener {
        void onQuantityIncrease(CartItem item);
        void onQuantityDecrease(CartItem item);
        void onRemoveItem(CartItem item);
    }

    public CartAdapter(List<CartItem> cartItems, OnCartItemActionListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    public void setCartItems(List<CartItem> newItems) {
        this.cartItems = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        
        holder.textProductName.setText(item.getProductName());
        holder.textProductPrice.setText(formatCurrency(item.getProductPrice()));
        holder.textQuantity.setText(String.valueOf(item.getQuantity()));
        holder.textSubtotal.setText(formatCurrency(item.getSubtotal()));

        // For now, use a placeholder for product image
        // When product feature is ready, you can load actual images here
        holder.imageProduct.setImageResource(R.drawable.ic_empty_categories);

        holder.buttonIncrease.setOnClickListener(v -> listener.onQuantityIncrease(item));
        holder.buttonDecrease.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                listener.onQuantityDecrease(item);
            }
        });
        holder.buttonRemove.setOnClickListener(v -> listener.onRemoveItem(item));
    }

    @Override
    public int getItemCount() {
        return (cartItems != null) ? cartItems.size() : 0;
    }

    private String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textProductName, textProductPrice, textQuantity, textSubtotal;
        MaterialButton buttonIncrease, buttonDecrease, buttonRemove;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textProductName = itemView.findViewById(R.id.textProductName);
            textProductPrice = itemView.findViewById(R.id.textProductPrice);
            textQuantity = itemView.findViewById(R.id.textQuantity);
            textSubtotal = itemView.findViewById(R.id.textSubtotal);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }
}
