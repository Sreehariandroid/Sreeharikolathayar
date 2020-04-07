package com.example.myapplication.loaddataapp.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.example.myapplication.loaddataapp.R;
import com.example.myapplication.loaddataapp.Util.NetworkUtil;
import com.example.myapplication.loaddataapp.model.ItemElement;
import com.example.myapplication.loaddataapp.network.ApiHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends Observable {
    public ObservableInt isProgressBarVisible;
    public ObservableInt isRecycleViewVisible;
    public ObservableInt isShowErrorMessage;
    public ObservableField<String> errorMessage;

    private List<ItemElement> itemElementList;
    private String title;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Context context) {
        this.context = context;
        this.itemElementList = new ArrayList<>();
        errorMessage = new ObservableField<>(context.getString(R.string.error_message));
        isShowErrorMessage = new ObservableInt(View.VISIBLE);
        isProgressBarVisible = new ObservableInt(View.VISIBLE);
        isRecycleViewVisible = new ObservableInt(View.VISIBLE);
    }

   /*
    ** API call to fetch json data
    */
    public void makeApiCall() {
        if (NetworkUtil.isNetworkAvailable(context)) {
            setViewsVisibility(View.VISIBLE, View.GONE, View.GONE);
            getItemData();
        } else {
            setViewsVisibility(View.GONE, View.VISIBLE, View.GONE);
        }
    }

    private void getItemData() {
        Disposable disposable = ApiHelper.getListData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainElement -> {
                    if (mainElement != null) {
                        itemElementList.addAll(mainElement.getItems());
                        this.title = mainElement.getTitle();
                        setChanged();
                        notifyObservers();
                        setViewsVisibility(View.GONE, View.GONE, View.VISIBLE);
                    } else {
                        setViewsVisibility(View.GONE, View.VISIBLE, View.GONE);
                    }
                }, error -> {
                    errorMessage.set(context.getString(R.string.error_message));
                    setViewsVisibility(View.GONE, View.VISIBLE, View.GONE);
                });
        compositeDisposable.add(disposable);
    }

    public String getTitle() {
        return title;
    }

    public List<ItemElement> getItemList() {
        return itemElementList;
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    private void setViewsVisibility(int progBarVisibility, int showErrorMessageVisibility, int recycleViewVisibility) {
        isProgressBarVisible.set(progBarVisibility);
        isShowErrorMessage.set(showErrorMessageVisibility);
        isRecycleViewVisible.set(recycleViewVisibility);
    }
}
