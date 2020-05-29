package com.viveris.appexemple.network.retrofit;

import com.viveris.appexemple.model.Users;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("users?order=desc&sort=reputation&site=stackoverflow")
    Call<Users> getUsers();

    @GET("users?order=desc&sort=reputation&site=stackoverflow")
    Single<Users> getRxUsers();
}
