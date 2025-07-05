package com.example.prmasignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GenericAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface Binder<T>{
        void bind(T item, View itemView, int position);
    }

    private final List<T> items;
    private final @LayoutRes int layoutId;
    private final Binder<T> binder;

    public GenericAdapter(List<T> items, int layoutID, Binder<T> binder) {
        this.items = items;
        this.layoutId = layoutID;
        this.binder = binder;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new RecyclerView.ViewHolder(view){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        T item = items.get(position);
        binder.bind(item, holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
