package com.akshayjadhav.retrofit.API;

import com.akshayjadhav.retrofit.models.DefaultResponse;
import com.akshayjadhav.retrofit.models.LoginResponse;
import com.akshayjadhav.retrofit.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @POST("createUser")
    Call<DefaultResponse> createUser(
            @Field("email") String email ,
            @Field("password") String password ,
            @Field("name") String name ,
            @Field("school") String school
    );


    @FormUrlEncoded
    @POST("userLogin")
    Call<LoginResponse> userLogin(
            @Field("email") String email ,
            @Field("password") String password
    );


    @GET("allUsers")
    Call<UserResponse>getUsers();

    @FormUrlEncoded
    @PUT("updateUser/{id}")
    Call<LoginResponse>updateUser(
            @Path("id") int id,
            @Field("email") String email ,
            @Field("name") String name ,
            @Field("school") String school
    );

    @FormUrlEncoded
    @PUT("updatePassword")
    Call<DefaultResponse>updatePassword(
            @Field("currentPassword") String currentPassword,
            @Field("newPassword") String newPassword,
            @Field("email") String email

    );

    @DELETE("deleteUser/{id}")
    Call<DefaultResponse>deleteUser(
            @Path("id") int id
    );
}
