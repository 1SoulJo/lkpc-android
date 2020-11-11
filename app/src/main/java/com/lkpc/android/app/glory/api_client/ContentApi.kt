package com.lkpc.android.app.glory.api_client

import com.lkpc.android.app.glory.entity.BaseContent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ContentApi {
    @GET("column/{startId}")
    fun loadColumns(@Path("startId") id: Int): Call<List<BaseContent>>

    @GET("sermon/{startId}")
    fun loadSermons(@Path("startId") id: Int): Call<List<BaseContent>>

    @GET("meditation/{startId}")
    fun loadMeditations(@Path("startId") id: Int): Call<List<BaseContent>>

    @GET("news/{startId}")
    fun loadNews(@Path("startId") id: Int): Call<List<BaseContent>>
}