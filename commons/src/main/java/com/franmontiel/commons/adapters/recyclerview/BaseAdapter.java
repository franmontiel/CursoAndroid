package com.franmontiel.commons.adapters.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<VH extends BaseViewHolder, T>
        extends RecyclerView.Adapter<VH> {

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private List<T> items;
    private Context context;

    public BaseAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    public BaseAdapter(Context context, List<T> items) {
        this(context);
        this.items.addAll(items);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        notifyDataSetChanged();
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setOnItemClickListener(onItemClickListener);
        onBindViewHolder(context, holder, position);
    }

    public void setItems(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public boolean updateItem(T item) {
        int position = items.indexOf(item);
        if (position != -1) {
            this.items.set(position, item);
            notifyItemChanged(position);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeItem(T item) {
        int position = this.items.indexOf(item);
        boolean removed = this.items.remove(item);
        if (removed) {
            notifyItemRemoved(position);
        }
        return removed;
    }

    public abstract void onBindViewHolder(Context context, VH holder, int position);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<T> getItems() {
        return items;
    }

    public T getItem(int position) {
        return items.get(position);
    }
}
