package com.lkpc.android.app.glory.api_client

import com.google.gson.GsonBuilder
import com.lkpc.android.app.glory.entity.BaseContent
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ContentApiClient {
    private val BASE_URL = "https://lkpc.org/node/api/board/"

    private fun setupApi(): ContentApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ContentApi::class.java)
    }

    fun loadColumns(startId: Int, cb: Callback<List<BaseContent>>) {
        setupApi().loadColumns(id=startId).enqueue(cb)
    }

    fun loadSermons(startId: Int, cb: Callback<List<BaseContent>>) {
        setupApi().loadSermons(id=startId).enqueue(cb)
    }

    fun loadMeditations(startId: Int, cb: Callback<List<BaseContent>>) {
        setupApi().loadMeditations(id=startId).enqueue(cb)
    }

    fun loadNews(startId: Int, cb: Callback<List<BaseContent>>) {
        setupApi().loadNews(id=startId).enqueue(cb)
    }
}