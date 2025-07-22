package com.example.prmasignment.ui.adminProduct;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.model.Product;
import com.example.prmasignment.repository.AdminProductRepository;
import com.example.prmasignment.ui.product.ProductAdapter;
import com.example.prmasignment.ui.auth.SessionManager;

import java.util.ArrayList;

public class AdminProductFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private AdminProductViewModel viewModel;
    private Button buttonAddProduct;
    private TextView textProductCount;
    private LinearLayout layoutEmptyState;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_product, container, false);
        
        initViews(view);
        setupRecyclerView();
        setupViewModel();
        setupObservers();
        setupListeners();
        
        // Load initial data
        viewModel.loadProducts();
        
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        buttonAddProduct = view.findViewById(R.id.buttonAddProduct);
        textProductCount = view.findViewById(R.id.textProductCount);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        sessionManager = new SessionManager(requireContext());
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(new ArrayList<>(), new ProductAdapter.OnProductActionListener() {
            @Override
            public void onView(Product product) {
                // Handle view action
                Toast.makeText(getContext(), "Viewing: " + product.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEdit(Product product) {
                showCreateOrUpdateDialog(true, product);
            }

            @Override
            public void onDelete(Product product) {
                showDeleteConfirmationDialog(product);
            }

            @Override
            public void onAddToCart(Product product) {
                // Admin can also add to cart
                Toast.makeText(getContext(), "Added to cart: " + product.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onViewCart() {
                // Admins shouldn't need to view cart, but implementing for interface compliance
                Toast.makeText(getContext(), "Cart viewing not available in admin mode", Toast.LENGTH_SHORT).show();
            }
        }, true); // true for admin mode
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        String token = sessionManager.getToken();
        AdminProductRepository repository = new AdminProductRepository(token);
        viewModel = new ViewModelProvider(this, new AdminProductViewModelFactory(repository))
                .get(AdminProductViewModel.class);
    }

    private void setupObservers() {
        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                adapter.updateProducts(products);
                textProductCount.setText(products.size() + " products");
                layoutEmptyState.setVisibility(products.isEmpty() ? View.VISIBLE : View.GONE);
                recyclerView.setVisibility(products.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            // No progress bar in this layout, could show in other ways if needed
        });
    }

    private void setupListeners() {
        buttonAddProduct.setOnClickListener(v -> showCreateOrUpdateDialog(false, null));
    }

    private void showCreateOrUpdateDialog(boolean isEditMode, @Nullable Product existingProduct) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_product, null);

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

        if (isEditMode && existingProduct != null) {
            etName.setText(existingProduct.getName());
            etDescription.setText(existingProduct.getDescription());
            etPrice.setText(String.valueOf(existingProduct.getPrice()));
            etStock.setText(String.valueOf(existingProduct.getStockQuantity()));
            etImageUrl.setText(existingProduct.getImageUrl());
            etCategoryId.setText(existingProduct.getCategoryId() != null ? existingProduct.getCategoryId().toString() : "");
            etBrandId.setText(existingProduct.getBrandId() != null ? existingProduct.getBrandId().toString() : "");
            etPetType.setText(existingProduct.getPetType());
            etLifeStage.setText(existingProduct.getLifeStage());
            etSpecialNeeds.setText(existingProduct.getSpecialNeeds());
            etWeight.setText(String.valueOf(existingProduct.getWeightKg()));
        }

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(isEditMode ? "Update Product" : "Create Product")
                .setView(dialogView)
                .setPositiveButton(isEditMode ? "Update" : "Create", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
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
                    Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double price = Double.parseDouble(priceStr);
                    int stock = Integer.parseInt(stockStr);
                    Long categoryId = categoryIdStr.isEmpty() ? null : Long.parseLong(categoryIdStr);
                    Long brandId = brandIdStr.isEmpty() ? null : Long.parseLong(brandIdStr);
                    double weight = weightStr.isEmpty() ? 0.0 : Double.parseDouble(weightStr);

                    Product product = new Product();
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(price);
                    product.setStockQuantity(stock);
                    product.setImageUrl(imageUrl);
                    product.setCategoryId(categoryId);
                    product.setBrandId(brandId);
                    product.setPetType(petType);
                    product.setLifeStage(lifeStage);
                    product.setSpecialNeeds(specialNeeds);
                    product.setWeightKg(weight);

                    if (isEditMode && existingProduct != null) {
                        product.setProductId(existingProduct.getProductId());
                        viewModel.updateProduct(product);
                    } else {
                        viewModel.createProduct(product);
                    }

                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid number format", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

    private void showDeleteConfirmationDialog(Product product) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete '" + product.getName() + "'?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteProduct(product.getProductId());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
