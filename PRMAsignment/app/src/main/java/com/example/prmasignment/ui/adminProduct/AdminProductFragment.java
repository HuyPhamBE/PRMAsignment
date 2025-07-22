package com.example.prmasignment.ui.adminProduct;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.prmasignment.model.ProductRequest;
import com.example.prmasignment.repository.AdminProductRepository;
import com.example.prmasignment.ui.auth.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AdminProductFragment extends Fragment {
    private AdminProductViewModel viewModel;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyStateLayout;
    private TextView textProductCount;
    private MaterialButton buttonAddProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        textProductCount = view.findViewById(R.id.textProductCount);
        emptyStateLayout = view.findViewById(R.id.layoutEmptyState);
        buttonAddProduct = view.findViewById(R.id.buttonAddProduct);

        adapter = new ProductAdapter(new ArrayList<>(), new ProductAdapter.OnProductActionListener() {
            @Override
            public void onEdit(Product product) {
                showCreateOrUpdateDialog(true, product);
            }

            @Override
            public void onDelete(Product product) {
                viewModel.deleteProduct(product.getId());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Setup ViewModel
        String token = new SessionManager(requireContext()).getToken();
        AdminProductRepository repository = new AdminProductRepository(token);
        viewModel = new ViewModelProvider(this, new AdminProductViewModelFactory(repository)).get(AdminProductViewModel.class);

        // Observe LiveData
        observeData();

        // Add new product dialog
        buttonAddProduct.setOnClickListener(v -> {
            showCreateOrUpdateDialog(false, null); // SHOW create dialog
        });

        // Load data
        viewModel.fetchProducts();
    }

    private void observeData() {
        viewModel.productsLiveData.observe(getViewLifecycleOwner(), products -> {
            adapter.setProductList(products);
            textProductCount.setText(products != null ? products.size() + " products" : "0 products");
            emptyStateLayout.setVisibility((products == null || products.isEmpty()) ? View.VISIBLE : View.GONE);
        });

        viewModel.createProductResult.observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                Toast.makeText(getContext(), "Created: " + product.getName(), Toast.LENGTH_SHORT).show();
                viewModel.fetchProducts();
            }
        });

        viewModel.updateProductResult.observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                Toast.makeText(getContext(), "Updated: " + product.getName(), Toast.LENGTH_SHORT).show();
                viewModel.fetchProducts();
            }
        });

        viewModel.deleteProductResult.observe(getViewLifecycleOwner(), success -> {
            Toast.makeText(getContext(), success ? "Deleted" : "Failed to delete", Toast.LENGTH_SHORT).show();
            if (success) viewModel.fetchProducts();
        });
    }

    private void showCreateOrUpdateDialog(boolean isEditMode, @Nullable Product existingProduct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_product, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText name = dialogView.findViewById(R.id.editProductName);
        EditText desc = dialogView.findViewById(R.id.editProductDescription);
        EditText price = dialogView.findViewById(R.id.editProductPrice);
        EditText stock = dialogView.findViewById(R.id.editProductStock);
        EditText imageUrl = dialogView.findViewById(R.id.editProductImageUrl);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        if (isEditMode && existingProduct != null) {
            name.setText(existingProduct.getName());
            desc.setText(existingProduct.getDescription());
            price.setText(String.valueOf(existingProduct.getPrice()));
            stock.setText(String.valueOf(existingProduct.getStockQuantity()));
            imageUrl.setText(existingProduct.getImageUrl());
            btnSave.setText("Update Product");
        }

        btnSave.setOnClickListener(v -> {
            try {
                String n = name.getText().toString().trim();
                String d = desc.getText().toString().trim();
                BigDecimal p = new BigDecimal(price.getText().toString().trim());
                int s = Integer.parseInt(stock.getText().toString().trim());
                String img = imageUrl.getText().toString().trim();

                // Đúng thứ tự: name, imageUrl, stockQuantity, price, description
                ProductRequest request = new ProductRequest(n, img, s, p, d);

                if (isEditMode && existingProduct != null) {
                    viewModel.updateProduct(existingProduct.getId(), request);
                } else {
                    viewModel.createProduct(request);
                }
                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
            }
        });


        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
