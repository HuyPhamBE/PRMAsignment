package com.example.prmasignment.ui.adminBrand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prmasignment.R;
import com.example.prmasignment.model.Brand;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private List<Brand> brandList;
    private final OnBrandActionListener listener;

    public interface OnBrandActionListener {
        void onEdit(Brand brand);
        void onDelete(Brand brand);
    }

    public BrandAdapter(List<Brand> brandList, OnBrandActionListener listener) {
        this.brandList = brandList;
        this.listener = listener;
    }

    public void setBrandList(List<Brand> brands) {
        this.brandList = brands;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.textBrandName.setText(brand.getName());
        holder.textBrandUrl.setText(brand.getLogoUrl());

        Glide.with(holder.itemView.getContext())
                .load(brand.getLogoUrl())
                .placeholder(R.drawable.ic_brand_placeholder)
                .into(holder.imageBrandLogo);

        holder.buttonEdit.setOnClickListener(v -> listener.onEdit(brand));
        holder.buttonDelete.setOnClickListener(v -> listener.onDelete(brand));
    }

    @Override
    public int getItemCount() {
        return (brandList != null) ? brandList.size() : 0;
    }

    static class BrandViewHolder extends RecyclerView.ViewHolder {
        TextView textBrandName, textBrandUrl;
        ImageView imageBrandLogo;
        MaterialButton buttonEdit, buttonDelete;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            textBrandName = itemView.findViewById(R.id.textBrandName);
            textBrandUrl = itemView.findViewById(R.id.textBrandUrl);
            imageBrandLogo = itemView.findViewById(R.id.imageBrandLogo);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
