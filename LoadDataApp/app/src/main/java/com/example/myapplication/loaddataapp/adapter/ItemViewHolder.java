package com.example.myapplication.loaddataapp.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.loaddataapp.databinding.ElementItemBinding;
import com.example.myapplication.loaddataapp.model.ItemElement;
import com.example.myapplication.loaddataapp.viewmodel.ItemViewModel;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private ElementItemBinding elementItemBinding;

    public ItemViewHolder(ElementItemBinding elementItemBinding) {
        super(elementItemBinding.itemRowParent);
        this.elementItemBinding = elementItemBinding;
    }

    void bindRowView(ItemElement itemElement) {
        if (elementItemBinding.getItemViewModel() == null) {
            elementItemBinding.setItemViewModel(new ItemViewModel(itemElement));
        } else {
            elementItemBinding.getItemViewModel().setElementItem(itemElement);
        }
    }
}