package com.example.prmasignment.ui.adminOrder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.model.OrderResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderAdminAdapter extends RecyclerView.Adapter<OrderAdminAdapter.OrderViewHolder> {
    private List<OrderResponse> orders = new ArrayList<>();

    public void setOrders(List<OrderResponse> orderList) {
        this.orders = orderList;
        notifyDataSetChanged(); // Ensure UI refresh
    }

    public interface OnOrderClickListener {
        void onViewDetails(OrderResponse order);
    }

    private OnOrderClickListener listener;

    public void setOnOrderClickListener(OnOrderClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_admin, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderResponse order = orders.get(position);

        // Order ID
        holder.textOrderId.setText("Order #" + order.getId());

        // User Name (check null)
        if (order.getUserData() != null) {
            String fullName = order.getUserData().getFirstName() + " " + order.getUserData().getLastName();
            holder.textUserName.setText("Customer: " + fullName);
        } else {
            holder.textUserName.setText("Customer: N/A");
        }

        // Payment Status
        holder.textPaymentStatus.setText("Payment: " +
                (order.getPaymentStatus() != null ? order.getPaymentStatus() : "N/A"));

        // Total
        holder.textTotalAmount.setText("Total: $" + order.getTotalAmount());

        // Order Date
        if (order.getOrderDate() != null) {
            Instant instant = Instant.parse(order.getOrderDate());
            String formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    .withZone(ZoneId.systemDefault())
                    .format(instant);
            holder.textOrderDate.setText("Date: " + formattedDate);
        } else {
            holder.textOrderDate.setText("Date: Unknown");
        }
        holder.btnViewDetails.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewDetails(order);
            }
        });


    }


    @Override
    public int getItemCount() {
        return orders != null ? orders.size() : 0;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textOrderId, textUserName, textPaymentStatus, textTotalAmount, textOrderDate;
        Button btnViewDetails;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textOrderId = itemView.findViewById(R.id.textOrderId);
            textUserName = itemView.findViewById(R.id.textUserName);
            textPaymentStatus = itemView.findViewById(R.id.textPaymentStatus);
            textTotalAmount = itemView.findViewById(R.id.textTotalAmount);
            textOrderDate = itemView.findViewById(R.id.textOrderDate);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);

        }

    }
}
