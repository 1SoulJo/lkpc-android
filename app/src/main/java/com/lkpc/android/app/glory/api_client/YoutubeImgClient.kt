package com.lkpc.android.app.glory.api_client

import com.lkpc.android.app.glory.constants.WebUrls.Companion.YOUTUBE_IMG_BASE
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Retrofit

class YoutubeImgClient {
    private fun setupApi(): YoutubeImgApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(YOUTUBE_IMG_BASE)
            .build()

        return retrofit.create(YoutubeImgApi::class.java)
    }

    fun getThumbnail(videoId: String, cb: Callback<ResponseBody>) {
        setupApi().getThumbnail(id=videoId).enqueue(cb)
    }
}