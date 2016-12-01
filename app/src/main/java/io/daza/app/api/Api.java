package io.daza.app.api;

import io.daza.app.model.Result;
import io.daza.app.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("/account/register")
    Call<Result<User>> register(@Field("username") String username,
                                @Field("email") String email,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("/account/login")
    Call<Result<User>> login(@Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("/account/logout")
    Call<Result> logout();

}
