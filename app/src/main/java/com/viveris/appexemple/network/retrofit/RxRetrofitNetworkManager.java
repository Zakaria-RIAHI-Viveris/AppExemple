package com.viveris.appexemple.network.retrofit;

import com.viveris.appexemple.AppExempleApplication;
import com.viveris.appexemple.model.Users;
import com.viveris.appexemple.network.INetworkManager;
import com.viveris.appexemple.ui.listener.IListUserListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RxRetrofitNetworkManager implements INetworkManager {

    private IListUserListener listener;

    public RxRetrofitNetworkManager(IListUserListener listener) {
        this.listener = listener;
    }

    @Override
    public void fetchUserFromNetwork(AppExempleApplication application) {
        RequestInterface requestInterface = application.getRetrofit().create(RequestInterface.class);
        requestInterface.getRxUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Users>() {
                    @Override
                    public void onSuccess(Users users) {
                        if (users != null && users.getUserList() != null) {
                            listener.displayResult(users);
                        } else {
                            listener.displayFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.displayFailure();
                    }
                });
    }
}
