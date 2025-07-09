package com.example.prmasignment.ui.dashboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.R;
import com.example.prmasignment.ui.auth.SessionManager;

public class AdminDashboardFragment extends Fragment {

    private AdminDashboardViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        SessionManager sessionManager = new SessionManager(requireContext());
        String token = sessionManager.getToken();

        AdminDashboardViewModelFactory factory = new AdminDashboardViewModelFactory(token);
        viewModel = new ViewModelProvider(this, factory).get(AdminDashboardViewModel.class);

        setupObservers();
        viewModel.fetchDashboardData(); // chỉ gọi 1 lần

        return view;
    }



    private void setupObservers() {
        viewModel.ordersToday.observe(getViewLifecycleOwner(), value -> {
            // Update UI: txtTotalOrders.setText(value + " đơn");
        });

        viewModel.ordersThisMonth.observe(getViewLifecycleOwner(), value -> {
            // Update UI
        });

        viewModel.totalRevenue.observe(getViewLifecycleOwner(), value -> {
            // txtTotalRevenue.setText(value + " VNĐ");
        });

        viewModel.monthlyRevenue.observe(getViewLifecycleOwner(), revenueTrend -> {
            // Update line chart
        });

        viewModel.orderStatus.observe(getViewLifecycleOwner(), status -> {
            // Update pie chart
        });

        viewModel.topProducts.observe(getViewLifecycleOwner(), product -> {
            // RecyclerView adapter update
        });
    }

}

