package com.example.prmasignment.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prmasignment.R;
import com.example.prmasignment.model.Product;
import com.example.prmasignment.model.TopProduct;

import java.util.ArrayList;
import java.util.List;

public class TopProductAdapter extends RecyclerView.Adapter<TopProductAdapter.ProductViewHolder> {

    private List<TopProduct> productList = new ArrayList<>();


    public void submitList(List<TopProduct> products) {
        this.productList = products;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        TopProduct p = productList.get(position);
        holder.txtName.setText(p.getProductName());
        holder.txtSold.setText("Sold: " + p.getQuantitySold());
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtSold;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtProductName);
            txtSold = itemView.findViewById(R.id.txtProductSold);
        }
    }
}
