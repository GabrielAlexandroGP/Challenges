package com.example.codechallenge

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getOrders(@Url url:String):Response<OrderResponse>

    @GET
    suspend fun getExchanges(@Url url:String):Response<ExchangesResponse>
}