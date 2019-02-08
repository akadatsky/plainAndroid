package com.company.plainandroid

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("p24api/exchange_rates")
    fun pbRequest(@Query("json") json: Boolean, @Query("date") date: String): Call<PbResponse>

}