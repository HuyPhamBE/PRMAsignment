package com.example.prmasignment.ui.adminOrder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.ui.auth.SessionManager;

import java.util.ArrayList;


public class AdminOrderFragment extends Fragment {

    private AdminOrderViewModel orderViewModel;
    private OrderAdminAdapter orderAdapter;
    private RecyclerView recyclerView;
    private TextView textOrderCount;
    private LinearLayout layoutEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_order, container, false);

        // Ánh xạ view
        recyclerView = view.findViewById(R.id.recyclerViewOrders);
        textOrderCount = view.findViewById(R.id.textOrderCount);
        layoutEmpty = view.findViewById(R.id.layoutEmptyState);

        // Setup RecyclerView
        orderAdapter = new OrderAdminAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.setOnOrderClickListener(order -> {
            OrderDetailDialog dialog = new OrderDetailDialog(order);
            dialog.show(getChildFragmentManager(), "OrderDetailDialog");
        });

        SessionManager sessionManager = new SessionManager(requireContext());
        String token = sessionManager.getToken();

        // ViewModel
        orderViewModel = new ViewModelProvider(
                this,
                new AdminOrderViewModelFactory(token)
        ).get(AdminOrderViewModel.class);

        observeData();

        return view;
    }

    private void observeData() {
        orderViewModel.getOrders().observe(getViewLifecycleOwner(), orders -> {
            Log.d("ORDER_OBSERVER", "Received " + (orders != null ? orders.size() : 0) + " orders");

            if (orders != null && !orders.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
                layoutEmpty.setVisibility(View.GONE);
                orderAdapter.setOrders(orders);
                textOrderCount.setText(orders.size() + " orders");
            } else {
                layoutEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                textOrderCount.setText("0 orders");
            }
        });

    }
}
