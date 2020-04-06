package com.example.myapplication.loaddataapp.viewmodel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.example.myapplication.loaddataapp.R;
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

    public void makeApiCall() {
        isShowErrorMessage.set(View.GONE);
        isRecycleViewVisible.set(View.GONE);
        isProgressBarVisible.set(View.VISIBLE);

        if (isNetworkAvailable()) {
            getItemData();
        } else {
            errorMessage.set(context.getString(R.string.error_message));
            isProgressBarVisible.set(View.GONE);
            isShowErrorMessage.set(View.VISIBLE);
            isRecycleViewVisible.set(View.GONE);
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
                        isProgressBarVisible.set(View.GONE);
                        isShowErrorMessage.set(View.GONE);
                        isRecycleViewVisible.set(View.VISIBLE);
                    } else {
                        errorMessage.set(context.getString(R.string.error_message));
                        isProgressBarVisible.set(View.GONE);
                        isShowErrorMessage.set(View.VISIBLE);
                        isRecycleViewVisible.set(View.GONE);
                    }
                }, error -> {
                    errorMessage.set(context.getString(R.string.error_message));
                    isProgressBarVisible.set(View.GONE);
                    isShowErrorMessage.set(View.VISIBLE);
                    isRecycleViewVisible.set(View.GONE);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
