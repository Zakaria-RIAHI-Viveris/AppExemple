package com.viveris.appexemple.network.retrofit;

import android.support.annotation.NonNull;

import com.viveris.appexemple.AppExempleApplication;
import com.viveris.appexemple.model.Users;
import com.viveris.appexemple.network.INetworkManager;
import com.viveris.appexemple.ui.listener.IListUserListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitNetworkManager implements INetworkManager {

    private IListUserListener listener;

    public RetrofitNetworkManager(IListUserListener listener) {
        this.listener = listener;
    }

    @Override
    public void fetchUserFromNetwork(AppExempleApplication application) {
        RequestInterface requestInterface = application.getRetrofit().create(RequestInterface.class);
        Call<Users> call = requestInterface.getUsers();
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                Users body = response.body();
                if (body != null && body.getUserList() != null) {
                    listener.displayResult(body);
                } else {
                    listener.displayFailure();
                }

            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                listener.displayFailure();
            }
        });
    }
}
