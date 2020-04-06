package com.example.myapplication.loaddataapp.viewmodel;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_noimage);
        requestOptions.error(R.drawable.ic_noimage);
        requestOptions.centerCrop();

        requestOptions.dontAnimate();

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(imageView);

        Glide.getPhotoCacheDir(imageView.getContext());
    }
}
