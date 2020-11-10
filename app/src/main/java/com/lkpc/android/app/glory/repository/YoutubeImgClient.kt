package com.lkpc.android.app.glory.repository

import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Retrofit

class YoutubeImgClient {
    private val BASE_URL = "https://img.youtube.com/vi/"

    private fun setupApi(): YoutubeImgApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(YoutubeImgApi::class.java)
    }

    fun getThumbnail(videoId: String, cb: Callback<ResponseBody>) {
        setupApi().getThumbnail(id=videoId).enqueue(cb)
    }
}