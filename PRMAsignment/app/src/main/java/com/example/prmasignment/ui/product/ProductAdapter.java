package com.example.prmasignment.ui.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnProductActionListener listener;
    private boolean isAdmin;

    public interface OnProductActionListener {
        void onView(Product product);
        void onEdit(Product product);
        void onDelete(Product product);
        void onAddToCart(Product product);
        void onViewCart();
    }

    public ProductAdapter(List<Product> productList, OnProductActionListener listener) {
        this.productList = productList;
        this.listener = listener;
        this.isAdmin = false; // Default to false
    }

    public ProductAdapter(List<Product> productList, OnProductActionListener listener, boolean isAdmin) {
        this.productList = productList;
        this.listener = listener;
        this.isAdmin = isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public void updateProducts(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductDescription, tvProductPrice, tvProductStock;
        ImageView ivProductImage;
        Button btnView, btnEdit, btnDelete, btnAddToCart, btnViewCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductStock = itemView.findViewById(R.id.tvProductStock);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            btnView = itemView.findViewById(R.id.btnViewProduct);
            btnEdit = itemView.findViewById(R.id.btnEditProduct);
            btnDelete = itemView.findViewById(R.id.btnDeleteProduct);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnViewCart = itemView.findViewById(R.id.btnViewCart);
        }

        public void bind(Product product) {
            tvProductName.setText(product.getName());
            tvProductDescription.setText(product.getDescription());
            tvProductPrice.setText(String.format("$%.2f", product.getPrice()));
            tvProductStock.setText("Stock: " + product.getStockQuantity());

            // For now, use a placeholder image. You can integrate with image loading library like Glide later
            ivProductImage.setImageResource(R.drawable.ic_cart); // placeholder

            // Show/hide admin buttons based on user role
            btnEdit.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
            btnDelete.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
            
            // Hide View Cart button from product items (use navbar cart icon instead)
            btnViewCart.setVisibility(View.GONE);
            
            // Always show View and Add to Cart buttons
            btnView.setVisibility(View.VISIBLE);
            btnAddToCart.setVisibility(View.VISIBLE);

            btnView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onView(product);
                }
            });

            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEdit(product);
                }
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDelete(product);
                }
            });

            btnViewCart.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onViewCart();
                }
            });

            btnAddToCart.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAddToCart(product);
                }
            });
        }
    }
}
