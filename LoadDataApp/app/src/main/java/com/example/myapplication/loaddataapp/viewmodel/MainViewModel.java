package com.example.myapplication.loaddataapp.viewmodel;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.loaddataapp.model.ItemElement;
import com.example.myapplication.loaddataapp.network.ApiHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    public ObservableInt isProgressBarVisible;
    public ObservableInt isRecycleViewVisible;
    public ObservableInt isShowErrorMessage;
    public ObservableField<String> errorMessage;

    private MutableLiveData<List<ItemElement>> itemElementList;

    private String title;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel() {
        this.itemElementList = new MutableLiveData<>();
        isShowErrorMessage = new ObservableInt(View.VISIBLE);
        isProgressBarVisible = new ObservableInt(View.VISIBLE);
        isRecycleViewVisible = new ObservableInt(View.VISIBLE);
    }

    /*
     ** API call to fetch json data
     */
    public void makeApiCall(boolean isNetworkAvailable) {
        if (isNetworkAvailable) {
            setViewsVisibility(View.VISIBLE, View.GONE, View.GONE);
            getItemData();
        } else {
            setViewsVisibility(View.GONE, View.VISIBLE, View.GONE);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setErrorMessage(String message) {
        errorMessage = new ObservableField<>(message);
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public MutableLiveData<List<ItemElement>> getItemsList() {
        return itemElementList;
    }

    private void getItemData() {
        Disposable disposable = ApiHelper.getListData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainElement -> {
                    if (mainElement != null) {
                        itemElementList.setValue(mainElement.getItems());
                        this.title = mainElement.getTitle();
                        setViewsVisibility(View.GONE, View.GONE, View.VISIBLE);
                    } else {
                        setViewsVisibility(View.GONE, View.VISIBLE, View.GONE);
                    }
                }, error -> {
                    setViewsVisibility(View.GONE, View.VISIBLE, View.GONE);
                });
        compositeDisposable.add(disposable);
    }

    private void setViewsVisibility(int progBarVisibility, int showErrorMessageVisibility, int recycleViewVisibility) {
        isProgressBarVisible.set(progBarVisibility);
        isShowErrorMessage.set(showErrorMessageVisibility);
        isRecycleViewVisible.set(recycleViewVisibility);
    }
}
