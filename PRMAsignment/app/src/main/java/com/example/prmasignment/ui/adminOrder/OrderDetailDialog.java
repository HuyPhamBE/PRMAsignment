package com.example.prmasignment.ui.adminOrder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.prmasignment.R;
import com.example.prmasignment.model.OrderResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OrderDetailDialog extends DialogFragment {
    private final OrderResponse order;

    public OrderDetailDialog(OrderResponse order) {
        this.order = order;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var inflater = LayoutInflater.from(getContext());
        var view = inflater.inflate(R.layout.dialog_order_detail, null);

        // Ánh xạ các TextView
        TextView tvOrderId = view.findViewById(R.id.tvOrderId);
        TextView tvStatus = view.findViewById(R.id.tvStatus);
        TextView tvAmount = view.findViewById(R.id.tvAmount);
        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvUser = view.findViewById(R.id.tvUser);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        TextView tvPhone = view.findViewById(R.id.tvPhone);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvPaymentStatus = view.findViewById(R.id.tvPaymentStatus);

        tvOrderId.setText("Order ID: " + order.getId());
        tvStatus.setText("Status: " + order.getStatus());
        tvAmount.setText("Total: $" + order.getTotalAmount());

        if (order.getOrderDate() != null) {
            Instant instant = Instant.parse(order.getOrderDate());
            String formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    .withZone(ZoneId.systemDefault())
                    .format(instant);
            tvDate.setText("Date: " + formattedDate);
        }



        if (order.getUserData() != null) {
            tvUser.setText("Customer: " + order.getUserData().getFirstName() + " " + order.getUserData().getLastName());
            tvEmail.setText("Email: " + order.getUserData().getEmail());
            tvPhone.setText("Phone: " + order.getUserData().getPhoneNumber());
        }

        if (order.getShippingAddressData() != null) {
            var addr = order.getShippingAddressData();
            String fullAddress = addr.getAddressLine1() + ", " + addr.getCity() + ", " + addr.getStateProvince() + " " + addr.getPostalCode() + ", " + addr.getCountry();
            tvAddress.setText("Shipping: " + fullAddress);
        }

        tvPaymentStatus.setText("Payment Status: " + order.getPaymentStatus());

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle("Order Details")
                .setPositiveButton("Close", (dialog, which) -> dialog.dismiss())
                .create();
    }
}
