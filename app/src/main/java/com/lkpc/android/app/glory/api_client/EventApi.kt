package com.lkpc.android.app.glory.api_client

import com.lkpc.android.app.glory.entity.Event
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {
    @GET("/")
    fun getAllEvents(): Call<List<Event>>

    // month value example : 202008
    @GET("/")
    fun getByMonth(@Query("month") month: String): Call<List<Event>>

    // week value example : 2020W48
    @GET("/")
    fun getByWeek(@Query("week") week: String): Call<List<Event>>
}