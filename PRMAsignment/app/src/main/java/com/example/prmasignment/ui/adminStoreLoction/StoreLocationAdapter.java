package com.example.prmasignment.ui.adminStoreLoction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmasignment.R;
import com.example.prmasignment.model.StoreLocationResponse;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class StoreLocationAdapter extends RecyclerView.Adapter<StoreLocationAdapter.LocationViewHolder> {

    private List<StoreLocationResponse> locationList;
    private final OnLocationActionListener listener;

    public interface OnLocationActionListener {
        void onDelete(StoreLocationResponse location);
        void onEdit(StoreLocationResponse location);
    }

    public StoreLocationAdapter(List<StoreLocationResponse> locationList, OnLocationActionListener listener) {
        this.locationList = locationList;
        this.listener = listener;
    }

    public void setLocationList(List<StoreLocationResponse> list) {
        this.locationList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        StoreLocationResponse location = locationList.get(position);
        holder.textName.setText(location.getName());
        holder.textAddress.setText(location.getAddress());
        holder.textPhone.setText(location.getPhoneNumber());
        holder.textOpeningHours.setText("🕒 " + location.getOpeningHours());

        holder.buttonDelete.setOnClickListener(v -> listener.onDelete(location));
        holder.buttonUpdate.setOnClickListener(v -> listener.onEdit(location));
    }

    @Override
    public int getItemCount() {
        return locationList != null ? locationList.size() : 0;
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textAddress, textPhone, textOpeningHours;
        MaterialButton buttonDelete;

        MaterialButton buttonUpdate;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textStoreName);
            textAddress = itemView.findViewById(R.id.textStoreAddress);
            textPhone = itemView.findViewById(R.id.textStorePhone);
            textOpeningHours = itemView.findViewById(R.id.textStoreHours);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteStore);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdateStore);
        }
    }
}

