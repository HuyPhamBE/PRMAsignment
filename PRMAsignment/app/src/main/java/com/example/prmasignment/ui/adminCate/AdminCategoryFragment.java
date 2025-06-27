package com.example.prmasignment.ui.adminCate;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.model.Category;
import com.example.prmasignment.ui.auth.SessionManager;


import java.util.ArrayList;

public class AdminCategoryFragment extends Fragment {

    private AdminCategoryViewModel viewModel;
    private CategoryAdapter adapter;
    private RecyclerView recyclerViewCategories;
    private Button buttonAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_category, container, false);

        recyclerViewCategories = view.findViewById(R.id.recyclerViewCategories);
        buttonAdd = view.findViewById(R.id.buttonAddCategory);

        adapter = new CategoryAdapter(new ArrayList<>(), new CategoryAdapter.OnCategoryActionListener() {
            @Override
            public void onEdit(Category category) {
                showCategoryDialog(category); // ✅ Gọi dialog edit
            }

            @Override
            public void onDelete(Category category) {
                viewModel.deleteCategory(category.getId());
            }
        });

        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCategories.setAdapter(adapter);

        // ✅ Lấy token từ SessionManager
        SessionManager sessionManager = new SessionManager(requireContext());
        String token = sessionManager.getToken();

        // ✅ Truyền token vào ViewModel thông qua Factory
        AdminCategoryViewModelFactory factory = new AdminCategoryViewModelFactory(token);
        viewModel = new ViewModelProvider(this, factory).get(AdminCategoryViewModel.class);

        setupObservers();

        buttonAdd.setOnClickListener(v -> {
            showCategoryDialog(null); // ✅ Gọi dialog tạo mới
        });

        viewModel.fetchCategories();

        return view;
    }

    private void setupObservers() {
        viewModel.categoriesLiveData.observe(getViewLifecycleOwner(), categories -> {
            Log.d("AdminCategoryFragment", "Observed " + (categories != null ? categories.size() : 0) + " categories");
            adapter.setCategoryList(categories);
        });


        viewModel.createCategoryResult.observe(getViewLifecycleOwner(), category -> {
            if (category != null) {
                Toast.makeText(getContext(), "Created: " + category.getName(), Toast.LENGTH_SHORT).show();
                viewModel.fetchCategories();
            }
        });

        viewModel.updateCategoryResult.observe(getViewLifecycleOwner(), category -> {
            if (category != null) {
                Toast.makeText(getContext(), "Updated: " + category.getName(), Toast.LENGTH_SHORT).show();
                viewModel.fetchCategories();
            }
        });

        viewModel.deleteCategoryResult.observe(getViewLifecycleOwner(), isDeleted -> {
            if (isDeleted) {
                Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                viewModel.fetchCategories();
            } else {
                Toast.makeText(getContext(), "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ Show dialog để tạo / cập nhật Category
    public void showCategoryDialog(@Nullable Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(category == null ? "Add Category" : "Edit Category");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_category, null);
        EditText editName = dialogView.findViewById(R.id.editCategoryName);
        EditText editDescription = dialogView.findViewById(R.id.editCategoryDescription);

        if (category != null) {
            editName.setText(category.getName());
            editDescription.setText(category.getDescription());
        }

        builder.setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = editName.getText().toString().trim();
                    String desc = editDescription.getText().toString().trim();

                    if (name.isEmpty()) {
                        Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (category == null) {
                        // Create
                        Category newCategory = new Category(0, name, desc);
                        viewModel.createCategory(newCategory);
                    } else {
                        // Update
                        category.setName(name);
                        category.setDescription(desc);
                        viewModel.updateCategory(category);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
