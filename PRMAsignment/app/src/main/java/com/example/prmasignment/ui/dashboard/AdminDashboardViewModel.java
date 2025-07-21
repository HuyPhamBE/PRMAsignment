package com.example.prmasignment.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.prmasignment.model.DashboardSummary;
import com.example.prmasignment.model.OrderStatus;
import com.example.prmasignment.model.RevenueTrend;
import com.example.prmasignment.model.TopProduct;
import com.example.prmasignment.repository.AdminDashboardRepository;

import java.math.BigDecimal;
import java.util.List;

public class AdminDashboardViewModel extends ViewModel {
    private final AdminDashboardRepository repository;

    public MutableLiveData<Long> ordersToday = new MutableLiveData<>();
    public MutableLiveData<Long> ordersThisMonth = new MutableLiveData<>();
    public MutableLiveData<BigDecimal> totalRevenue = new MutableLiveData<>();
    public MutableLiveData<List<RevenueTrend>> monthlyRevenue = new MutableLiveData<>();
    public MutableLiveData<List<OrderStatus>> orderStatus = new MutableLiveData<>();
    public MutableLiveData<List<TopProduct>> topProducts = new MutableLiveData<>();

    public AdminDashboardViewModel(String token) {
        this.repository = new AdminDashboardRepository(token);
    }

    public void fetchDashboardData(String status, int year) {
        repository.getTotalOrdersToday(ordersToday);
        repository.getOrdersThisMonth(ordersThisMonth);
        repository.getTotalRevenue(totalRevenue, status);
//        repository.getMonthlyRevenue(monthlyRevenue, status, year);
        repository.getOrderStatus(orderStatus);
        repository.getTopProducts(topProducts);
    }
}


