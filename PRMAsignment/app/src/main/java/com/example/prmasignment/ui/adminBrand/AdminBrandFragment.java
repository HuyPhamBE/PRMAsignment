package com.example.prmasignment.ui.adminBrand;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prmasignment.R;
import com.example.prmasignment.model.Brand;
import com.example.prmasignment.model.CreateBrandRequest;
import com.example.prmasignment.model.UpdateBrandRequest;
import com.example.prmasignment.repository.AdminBrandRepository;
import com.example.prmasignment.ui.auth.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class AdminBrandFragment extends Fragment {

    private AdminBrandViewModel viewModel;
    private BrandAdapter adapter;
    private RecyclerView recyclerView;
    private MaterialButton buttonAddBrand, buttonAddFirstBrand;
    private ProgressBar progressBar;
    private View layoutEmptyState;
    private TextView textBrandCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_brand, container, false); // dùng layout mới
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerViewBrands);
        progressBar = view.findViewById(R.id.progressBar);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        buttonAddBrand = view.findViewById(R.id.buttonAddBrand);
        buttonAddFirstBrand = view.findViewById(R.id.buttonAddFirstBrand);
        textBrandCount = view.findViewById(R.id.textBrandCount);

        adapter = new BrandAdapter(new ArrayList<>(), new BrandAdapter.OnBrandActionListener() {
            @Override
            public void onEdit(Brand brand) {
                showBrandDialog(brand);
            }

            @Override
            public void onDelete(Brand brand) {
                viewModel.deleteBrand(brand.getBrandId());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        SessionManager sessionManager = new SessionManager(requireContext());
        String token = sessionManager.getToken();
        AdminBrandRepository repository = new AdminBrandRepository(token);
        AdminBrandViewModelFactory factory = new AdminBrandViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(AdminBrandViewModel.class);

        observeData();

        // Gắn listener cho cả hai nút thêm thương hiệu
        if (buttonAddBrand != null)
            buttonAddBrand.setOnClickListener(v -> showBrandDialog(null));

        if (buttonAddFirstBrand != null)
            buttonAddFirstBrand.setOnClickListener(v -> showBrandDialog(null));

        viewModel.loadAllBrands();
    }

    private void observeData() {
        viewModel.getBrands().observe(getViewLifecycleOwner(), brands -> {
            adapter.setBrandList(brands);
            progressBar.setVisibility(View.GONE);

            boolean isEmpty = brands == null || brands.isEmpty();
            layoutEmptyState.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);

            // Cập nhật số lượng thương hiệu
            if (textBrandCount != null) {
                textBrandCount.setText(brands.size() + " brands");
            }
        });

        viewModel.getBrandOperationResult().observe(getViewLifecycleOwner(), brand -> {
            if (brand != null) {
                Toast.makeText(getContext(), "Saved: " + brand.getName(), Toast.LENGTH_SHORT).show();
                viewModel.loadAllBrands();
            }
        });

        viewModel.getDeleteResult().observe(getViewLifecycleOwner(), success -> {
            Toast.makeText(getContext(),
                    success ? "Deleted successfully" : "Delete failed",
                    Toast.LENGTH_SHORT).show();
            if (success) viewModel.loadAllBrands();
        });
    }

    private void showBrandDialog(@Nullable Brand brand) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(brand == null ? "Thêm thương hiệu" : "Chỉnh sửa thương hiệu");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_brand, null);

        EditText editName = dialogView.findViewById(R.id.etBrandName);
        ImageView imageLogo = dialogView.findViewById(R.id.ivBrandLogo);
        Button btnUpload = dialogView.findViewById(R.id.btnUploadLogo);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        final String[] logoUrl = {""};

        if (brand != null) {
            editName.setText(brand.getName());
            logoUrl[0] = brand.getLogoUrl();

            Glide.with(getContext())
                    .load(logoUrl[0])
                    .placeholder(R.drawable.ic_add_photo)
                    .into(imageLogo);
        }

        btnUpload.setOnClickListener(v -> {
            logoUrl[0] = "https://via.placeholder.com/150"; // hardcode giả lập upload
            Glide.with(getContext())
                    .load(logoUrl[0])
                    .into(imageLogo);
            Toast.makeText(getContext(), "Đã chọn logo mẫu", Toast.LENGTH_SHORT).show();
        });

        AlertDialog dialog = builder.setView(dialogView).create();

        btnSave.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String logo = logoUrl[0].trim();

            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Tên thương hiệu không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            if (brand == null) {
                viewModel.createBrand(new CreateBrandRequest(name, logo));
            } else {
                viewModel.updateBrand(new UpdateBrandRequest(brand.getBrandId(), name, logo));
            }

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
