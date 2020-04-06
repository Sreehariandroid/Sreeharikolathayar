package com.example.myapplication.loaddataapp.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.loaddataapp.R;
import com.example.myapplication.loaddataapp.databinding.ElementItemBinding;
import com.example.myapplication.loaddataapp.model.ItemElement;

import java.util.Collections;
import java.util.List;

public class ListDataAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<ItemElement> listItems;

    public ListDataAdapter() {
        this.listItems = Collections.emptyList();
    }

    @Override
    @NonNull
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ElementItemBinding elementItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.element_item, parent, false);
        return new ItemViewHolder(elementItemBinding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindRowView(listItems.get(position));

        if (TextUtils.isEmpty(listItems.get(position).getTitle())) {
            listItems.get(position).setTitle("");
        }

        if (TextUtils.isEmpty(listItems.get(position).getDesc())) {
            listItems.get(position).setDesc("Description not available.");
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public void setListItems(List<ItemElement> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }
}
