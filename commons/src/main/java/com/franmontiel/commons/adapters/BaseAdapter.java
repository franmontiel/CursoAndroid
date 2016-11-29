package com.franmontiel.commons.adapters;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    private List<T> items;

    public BaseAdapter() {
        this.items = new ArrayList<>();
    }

    public BaseAdapter(List<T> items) {
        this();
        this.items.addAll(items);
    }

    public void setItems(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void add(T item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void removeItem(T item) {
        boolean remove = this.items.remove(item);
        if (remove)
            notifyDataSetChanged();
    }

    protected List<T> getItems() {
        return items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
