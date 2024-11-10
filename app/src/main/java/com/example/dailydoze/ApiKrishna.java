package com.example.dailydoze;

import com.example.dailydoze.Models.CreateMedResponse;
import com.example.dailydoze.Models.FetchedMedicationsResponse;
import com.example.dailydoze.Models.LoginRequest;
import com.example.dailydoze.Models.LoginResponse;
import com.example.dailydoze.Models.CreateMedRequest;
import com.example.dailydoze.Models.MarketModel;
import com.example.dailydoze.Models.SignUpResponse;
import com.example.dailydoze.Models.UserModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiKrishna {




    @GET("data/{param}")
    Call<List<MarketModel>> getDetails(@Path("param") String param, @Query("pincode") String pincode);
}
