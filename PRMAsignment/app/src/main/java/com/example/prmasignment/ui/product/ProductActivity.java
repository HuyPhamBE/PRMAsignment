package com.example.prmasignment.ui.product;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.dtos.request.ProductRequest;
import com.example.prmasignment.model.Product;
import com.example.prmasignment.ui.auth.SessionManager;
import com.example.prmasignment.ui.cart.CartActivity;
import com.example.prmasignment.utils.CartUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ProductViewModel viewModel;
    private ProductAdapter adapter;
    private RecyclerView recyclerViewProducts;
    private Button btnAddProduct;
    private EditText etSearchProduct;
    private TextView tvEmptyProducts;

    private List<Product> allProducts = new ArrayList<>();
    private List<Product> filteredProducts = new ArrayList<>();
    private boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Check user role for admin functionality
        checkUserRole();
        
        initViews();
        setupRecyclerView();
        setupViewModel();
        setupObservers();
        setupSearchFunction();

        btnAddProduct.setOnClickListener(v -> showProductDialog(null));

        viewModel.fetchAllProducts();
    }

    private void checkUserRole() {
        SessionManager sessionManager = new SessionManager(this);
        String role = sessionManager.getRole();
        isAdmin = "ADMIN".equalsIgnoreCase(role);
        Log.d("ProductActivity", "User role: " + role + ", isAdmin: " + isAdmin);
    }

    private void initViews() {
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        etSearchProduct = findViewById(R.id.etSearchProduct);
        tvEmptyProducts = findViewById(R.id.tvEmptyProducts);
        
        // Show/hide Add Product button based on user role
        btnAddProduct.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(filteredProducts, new ProductAdapter.OnProductActionListener() {
            @Override
            public void onView(Product product) {
                Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
                Long productId = product.getProductId();
                if (productId != null) {
                    intent.putExtra("PRODUCT_ID", productId.longValue());
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductActivity.this, "Invalid product ID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onEdit(Product product) {
                if (isAdmin) {
                    showProductDialog(product);
                } else {
                    Toast.makeText(ProductActivity.this, "Only admins can edit products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDelete(Product product) {
                if (isAdmin) {
                    showDeleteConfirmationDialog(product);
                } else {
                    Toast.makeText(ProductActivity.this, "Only admins can delete products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAddToCart(Product product) {
                CartUtils.addProductToCart(ProductActivity.this, product.getProductId(), 1);
            }

            @Override
            public void onViewCart() {
                Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        }, isAdmin); // Pass isAdmin to adapter

        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProducts.setAdapter(adapter);
    }

    private void setupViewModel() {
        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.getToken();

        ProductViewModelFactory factory = new ProductViewModelFactory(token);
        viewModel = new ViewModelProvider(this, factory).get(ProductViewModel.class);
    }

    private void setupObservers() {
        viewModel.productsLiveData.observe(this, products -> {
            Log.d("ProductActivity", "Observed " + (products != null ? products.size() : 0) + " products");
            allProducts.clear();
            if (products != null) {
                allProducts.addAll(products);
            }
            filterProducts(etSearchProduct.getText().toString());
            updateEmptyState();
        });

        viewModel.createProductResult.observe(this, product -> {
            if (product != null) {
                Toast.makeText(this, "Product created: " + product.getName(), Toast.LENGTH_SHORT).show();
                viewModel.fetchAllProducts();
            } else {
                Toast.makeText(this, "Failed to create product", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.updateProductResult.observe(this, product -> {
            if (product != null) {
                Toast.makeText(this, "Product updated: " + product.getName(), Toast.LENGTH_SHORT).show();
                viewModel.fetchAllProducts();
            } else {
                Toast.makeText(this, "Failed to update product", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.deleteProductResult.observe(this, isDeleted -> {
            if (isDeleted) {
                Toast.makeText(this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                viewModel.fetchAllProducts();
            } else {
                Toast.makeText(this, "Failed to delete product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSearchFunction() {
        etSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterProducts(String query) {
        filteredProducts.clear();
        if (query.trim().isEmpty()) {
            filteredProducts.addAll(allProducts);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(lowerCaseQuery) ||
                    product.getDescription().toLowerCase().contains(lowerCaseQuery) ||
                    product.getPetType().toLowerCase().contains(lowerCaseQuery)) {
                    filteredProducts.add(product);
                }
            }
        }
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (filteredProducts.isEmpty()) {
            recyclerViewProducts.setVisibility(View.GONE);
            tvEmptyProducts.setVisibility(View.VISIBLE);
        } else {
            recyclerViewProducts.setVisibility(View.VISIBLE);
            tvEmptyProducts.setVisibility(View.GONE);
        }
    }

    private void showProductDialog(@Nullable Product product) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_product, null);

        EditText etName = dialogView.findViewById(R.id.etProductName);
        EditText etDescription = dialogView.findViewById(R.id.etProductDescription);
        EditText etPrice = dialogView.findViewById(R.id.etProductPrice);
        EditText etStock = dialogView.findViewById(R.id.etProductStock);
        EditText etImageUrl = dialogView.findViewById(R.id.etProductImageUrl);
        EditText etCategoryId = dialogView.findViewById(R.id.etProductCategoryId);
        EditText etBrandId = dialogView.findViewById(R.id.etProductBrandId);
        EditText etPetType = dialogView.findViewById(R.id.etProductPetType);
        EditText etLifeStage = dialogView.findViewById(R.id.etProductLifeStage);
        EditText etSpecialNeeds = dialogView.findViewById(R.id.etProductSpecialNeeds);
        EditText etWeight = dialogView.findViewById(R.id.etProductWeight);

        // Pre-fill if editing
        if (product != null) {
            etName.setText(product.getName());
            etDescription.setText(product.getDescription());
            etPrice.setText(String.valueOf(product.getPrice()));
            etStock.setText(String.valueOf(product.getStockQuantity()));
            etImageUrl.setText(product.getImageUrl());
            etCategoryId.setText(String.valueOf(product.getCategoryId()));
            etBrandId.setText(String.valueOf(product.getBrandId()));
            etPetType.setText(product.getPetType());
            etLifeStage.setText(product.getLifeStage());
            etSpecialNeeds.setText(product.getSpecialNeeds());
            etWeight.setText(String.valueOf(product.getWeightKg()));
        }

        new AlertDialog.Builder(this)
                .setTitle(product == null ? "Add Product" : "Edit Product")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String description = etDescription.getText().toString().trim();
                    String priceStr = etPrice.getText().toString().trim();
                    String stockStr = etStock.getText().toString().trim();
                    String imageUrl = etImageUrl.getText().toString().trim();
                    String categoryIdStr = etCategoryId.getText().toString().trim();
                    String brandIdStr = etBrandId.getText().toString().trim();
                    String petType = etPetType.getText().toString().trim();
                    String lifeStage = etLifeStage.getText().toString().trim();
                    String specialNeeds = etSpecialNeeds.getText().toString().trim();
                    String weightStr = etWeight.getText().toString().trim();

                    if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                        Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        double price = Double.parseDouble(priceStr);
                        int stock = Integer.parseInt(stockStr);
                        int categoryId = categoryIdStr.isEmpty() ? 0 : Integer.parseInt(categoryIdStr);
                        int brandId = brandIdStr.isEmpty() ? 0 : Integer.parseInt(brandIdStr);
                        double weight = weightStr.isEmpty() ? 0.0 : Double.parseDouble(weightStr);

                        ProductRequest request = new ProductRequest(
                                name, description, price, stock, imageUrl,
                                categoryId, brandId, petType, lifeStage, specialNeeds, weight
                        );

                        if (product == null) {
                            viewModel.createProduct(request);
                        } else {
                            viewModel.updateProduct(product.getProductId(), request);
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteConfirmationDialog(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete \"" + product.getName() + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteProduct(product.getProductId());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
