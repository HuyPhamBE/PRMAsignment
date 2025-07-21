package com.example.prmasignment.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.model.RevenueTrend;
import com.example.prmasignment.model.OrderStatus;
import com.example.prmasignment.model.TopProduct;
import com.example.prmasignment.ui.auth.SessionManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminDashboardFragment extends Fragment {

    private AdminDashboardViewModel viewModel;

    private TextView txtTotalOrders, txtTotalRevenue, txtTotalUsers, txtTotalProducts;
    private LineChart lineChartRevenue;
    private PieChart pieChartOrderStatus;
    private RecyclerView recyclerViewTopProducts;
    private TopProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        // Init UI
        txtTotalOrders = view.findViewById(R.id.textTotalOrders);
        txtTotalRevenue = view.findViewById(R.id.textTotalRevenue);
        txtTotalUsers = view.findViewById(R.id.textTotalUsers);
        txtTotalProducts = view.findViewById(R.id.textTotalProducts);
        lineChartRevenue = view.findViewById(R.id.lineChartRevenue);
        pieChartOrderStatus = view.findViewById(R.id.pieChartOrderStatus);
        recyclerViewTopProducts = view.findViewById(R.id.recyclerViewTopProducts);

        // RecyclerView
        recyclerViewTopProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TopProductAdapter();
        recyclerViewTopProducts.setAdapter(adapter);

        // ViewModel
        SessionManager sessionManager = new SessionManager(requireContext());
        String token = sessionManager.getToken();
        AdminDashboardViewModelFactory factory = new AdminDashboardViewModelFactory(token);
        viewModel = new ViewModelProvider(this, factory).get(AdminDashboardViewModel.class);

        setupObservers();
        viewModel.fetchDashboardData("COMPLETED", Calendar.getInstance().get(Calendar.YEAR));


        return view;
    }

    private void setupObservers() {
        viewModel.ordersToday.observe(getViewLifecycleOwner(), value -> {
            txtTotalOrders.setText(String.valueOf(value));
        });

        viewModel.ordersThisMonth.observe(getViewLifecycleOwner(), value -> {
            txtTotalUsers.setText(String.valueOf(value)); // giả định users
        });

        viewModel.totalRevenue.observe(getViewLifecycleOwner(), value -> {
            txtTotalRevenue.setText("$" + value);
        });

        viewModel.monthlyRevenue.observe(getViewLifecycleOwner(), trendList -> {
            List<Entry> entries = new ArrayList<>();
            for (int i = 0; i < trendList.size(); i++) {
                RevenueTrend trend = trendList.get(i);
                entries.add(new Entry(i, (float) trend.getRevenue()));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Revenue");
            dataSet.setColor(getResources().getColor(R.color.primary_color));
            dataSet.setValueTextColor(getResources().getColor(R.color.text_primary));

            LineData lineData = new LineData(dataSet);
            lineChartRevenue.setData(lineData);
            lineChartRevenue.invalidate();
        });

        viewModel.orderStatus.observe(getViewLifecycleOwner(), this::updatePieChart);

        viewModel.topProducts.observe(getViewLifecycleOwner(), productList -> {
            adapter.submitList(productList);
            txtTotalProducts.setText(String.valueOf(productList.size()));
        });
    }

    private void updatePieChart(List<OrderStatus> statusList) {
        List<PieEntry> entries = new ArrayList<>();
        for (OrderStatus status : statusList) {
            entries.add(new PieEntry(status.getCount(), status.getStatus()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Order Status");
        dataSet.setColors(new int[]{R.color.nav_header_background, R.color.success_color, R.color.error_color}, getContext());

        PieData pieData = new PieData(dataSet);
        pieChartOrderStatus.setData(pieData);
        pieChartOrderStatus.invalidate();
    }
}
