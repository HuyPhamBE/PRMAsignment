package com.example.prmasignment.ui.adminStoreLoction;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.prmasignment.model.StoreLocationRequest;
import com.example.prmasignment.model.StoreLocationResponse;
import com.example.prmasignment.model.UpdateStoreLocationRequest;
import com.example.prmasignment.repository.AdminStoreLocationRepository;
import com.example.prmasignment.ui.auth.SessionManager;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AdminStoreLocationFragment extends Fragment {

    private AdminStoreLocationViewModel viewModel;
    private RecyclerView recyclerView;
    private TextView textLocationCount;
    private View layoutEmptyState;
    private StoreLocationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_store_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.buttonAddStoreLocation).setOnClickListener(v -> {
            showStoreLocationDialog(null); // null nghĩa là tạo mới
        });

        recyclerView = view.findViewById(R.id.recyclerViewStoreLocations);
        textLocationCount = view.findViewById(R.id.textLocationCount);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);


        adapter = new StoreLocationAdapter(new ArrayList<>(), new StoreLocationAdapter.OnLocationActionListener() {
            @Override
            public void onDelete(StoreLocationResponse location) {
                viewModel.deleteStoreLocation(location.getId());
            }

            @Override
            public void onEdit(StoreLocationResponse location) {
                showStoreLocationDialog(location);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        String token = new SessionManager(requireContext()).getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(getContext(), "Token is missing. Please log in again.", Toast.LENGTH_LONG).show();
            return;
        }

        AdminStoreLocationRepository repo = new AdminStoreLocationRepository(token);
        viewModel = new ViewModelProvider(this, new AdminStoreLocationViewModelFactory(repo)).get(AdminStoreLocationViewModel.class);

        observeData();
        viewModel.fetchStoreLocations(); // You may want to implement a full list fetch
    }

    private void observeData() {
        viewModel.storeLocationsLiveData.observe(getViewLifecycleOwner(), locations -> {
            adapter.setLocationList(locations);
            textLocationCount.setText(locations != null ? locations.size() + " locations" : "0 locations");
            layoutEmptyState.setVisibility((locations == null || locations.isEmpty()) ? View.VISIBLE : View.GONE);
        });

        viewModel.deleteResult.observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(getContext(), "Store location deleted", Toast.LENGTH_SHORT).show();
                viewModel.fetchStoreLocations();
            } else {
                Toast.makeText(getContext(), "Failed to delete location", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.createResult.observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                Toast.makeText(getContext(), "Store location created", Toast.LENGTH_SHORT).show();
                viewModel.fetchStoreLocations(); // 👈 Reload after create
            } else {
                Toast.makeText(getContext(), "Failed to create location", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.updateResult.observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                Toast.makeText(getContext(), "Store location updated", Toast.LENGTH_SHORT).show();
                viewModel.fetchStoreLocations(); // 👈 Reload after update
            } else {
                Toast.makeText(getContext(), "Failed to update location", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showStoreLocationDialog(@Nullable StoreLocationResponse locationToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_store_location, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Gán các view
        EditText editName = dialogView.findViewById(R.id.editName);
        EditText editAddress = dialogView.findViewById(R.id.editAddress);
        EditText editPhone = dialogView.findViewById(R.id.editPhone);
        EditText editLat = dialogView.findViewById(R.id.editLatitude);
        EditText editLng = dialogView.findViewById(R.id.editLongitude);
        EditText editHours = dialogView.findViewById(R.id.editOpeningHours);
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);

        // Nếu là sửa
        if (locationToEdit != null) {
            dialogTitle.setText("Edit Store Location");
            editName.setText(locationToEdit.getName());
            editAddress.setText(locationToEdit.getAddress());
            editPhone.setText(locationToEdit.getPhoneNumber());
            editLat.setText(locationToEdit.getLatitude().toString());
            editLng.setText(locationToEdit.getLongitude().toString());
            editHours.setText(locationToEdit.getOpeningHours());
        }

        dialogView.findViewById(R.id.buttonCancel).setOnClickListener(v -> dialog.dismiss());

        dialogView.findViewById(R.id.buttonSave).setOnClickListener(v -> {
            String name = editName.getText().toString();
            String address = editAddress.getText().toString();
            String phone = editPhone.getText().toString();
            BigDecimal lat = new BigDecimal(editLat.getText().toString());
            BigDecimal lng = new BigDecimal(editLng.getText().toString());
            String hours = editHours.getText().toString();

            if (locationToEdit == null) {
                // Create new
                viewModel.createStoreLocation(new StoreLocationRequest(name, address, lat, lng, phone, hours));
            } else {
                // Update
                viewModel.updateStoreLocation(new UpdateStoreLocationRequest(
                        locationToEdit.getId(), name, address, lat, lng, phone, hours
                ));
            }

            dialog.dismiss();
        });

        dialog.show();
    }

}
