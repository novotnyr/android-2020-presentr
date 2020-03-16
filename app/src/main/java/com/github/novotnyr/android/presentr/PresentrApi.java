package com.github.novotnyr.android.presentr;

import java.util.List;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public interface PresentrApi {
    String BASE_URL = "https://ics.upjs.sk/~novotnyr/android/demo/presentr/index.php/";

    @GET("available-users")
    Call<List<User>> getUsers();

    @POST("available-users/{login}")
    Call<Void> login(@Path("login") String login);

    // API Execution Classes
    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PresentrApi API = RETROFIT.create(PresentrApi.class);
}
