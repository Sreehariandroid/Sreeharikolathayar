package com.example.myapplication.loaddataapp.viewmodel;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.loaddataapp.R;
import com.example.myapplication.loaddataapp.model.ItemElement;

public class ItemViewModel extends BaseObservable {
    private ItemElement elementItem;

    public ItemViewModel(ItemElement elementItem) {
        this.elementItem = elementItem;
    }
    public String getItemDescription() {
        return elementItem.getDesc();
    }
    public String getItemTitle() {
        return elementItem.getTitle();
    }
    public String getImage() {
        return elementItem.getImageUrl();
    }

    public void setElementItem(ItemElement item) {
        this.elementItem = item;
        notifyChange();
    }

    @BindingAdapter("imageUrl")
    public static void setImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_noimage)
                .dontAnimate()
                .into(imageView);

        Glide.getPhotoCacheDir(imageView.getContext());
    }
}
