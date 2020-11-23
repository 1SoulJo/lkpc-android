package com.lkpc.android.app.glory.api_client

import com.lkpc.android.app.glory.entity.AdContent
import com.lkpc.android.app.glory.entity.BaseContent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ContentApi {
    @GET("board/column/{startId}")
    fun loadColumns(@Path("startId") id: Int): Call<List<BaseContent>>

    @GET("board/sermon/{startId}")
    fun loadSermons(@Path("startId") id: Int): Call<List<BaseContent>>

    @GET("board/meditation/{startId}")
    fun loadMeditations(@Path("startId") id: Int): Call<List<BaseContent>>

    @GET("board/news/{startId}")
    fun loadNews(@Path("startId") id: Int): Call<List<BaseContent>>

    @GET("board/bulletin/{startId}")
    fun loadBulletins(@Path("startId") id: Int): Call<List<BaseContent>>

    @GET("page/service")
    fun loadService(): Call<List<BaseContent>>

    @GET("page/location")
    fun loadLocation(): Call<List<BaseContent>>

    @GET("link/main")
    fun loadAdContents(): Call<List<AdContent>>
}