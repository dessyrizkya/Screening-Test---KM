package com.dessy.screeningtest_intern.api

import com.dessy.screeningtest_intern.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

public interface ApiService {

    @GET("api/users")
    fun getUsers(@QueryMap param: HashMap<String, String>): Call<UserResponse>
}