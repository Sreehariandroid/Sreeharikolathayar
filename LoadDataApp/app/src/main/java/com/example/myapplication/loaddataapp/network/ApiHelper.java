package com.example.myapplication.loaddataapp.network;

import androidx.annotation.NonNull;

import com.example.myapplication.loaddataapp.model.MainElement;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHelper {

    public static Single<MainElement> getListData() {
        return Single
                .create((SingleEmitter<MainElement> emitter) -> {
                    ApiInterface apiInterface= RetrofitApi.getRetrofitInstance().create(ApiInterface.class);
                    Call<MainElement> call = apiInterface.getData();
                    call.enqueue(new Callback<MainElement>() {

                        @Override
                        public void onResponse(@NonNull Call<MainElement> call, @NonNull Response<MainElement> response) {
                           if(!emitter.isDisposed()) {
                               emitter.onSuccess(response.body());
                           }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MainElement> call, @NonNull Throwable t) {
                            if(!emitter.isDisposed()) {
                                emitter.onError(t);
                            }
                        }
                    });
                });
    }
}
