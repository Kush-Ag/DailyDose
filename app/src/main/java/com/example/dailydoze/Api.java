package com.example.dailydoze;

import com.example.dailydoze.Models.CreateMedResponse;
import com.example.dailydoze.Models.FetchedMedicationsResponse;
import com.example.dailydoze.Models.LoginRequest;
import com.example.dailydoze.Models.LoginResponse;
import com.example.dailydoze.Models.CreateMedRequest;
import com.example.dailydoze.Models.SignUpResponse;
import com.example.dailydoze.Models.UpdateQtyRequest;
import com.example.dailydoze.Models.UpdateQtyResponse;
import com.example.dailydoze.Models.UserModel;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @POST("signup")
    Call<SignUpResponse> register(@Body UserModel request);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/medication/create")
    Call<CreateMedResponse> create(@Body CreateMedRequest request);

    @GET("api/medication/user/{param}")
    Call<FetchedMedicationsResponse> getMedications(@Path("param") String param);

    @PUT("api/medication/inventory/edit")
    Call<UpdateQtyResponse> updateQty(@Body UpdateQtyRequest request);
}
