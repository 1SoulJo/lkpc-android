package com.lkpc.android.app.glory.repository

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface YoutubeImgApi {
    @GET("{videoId}/maxresdefault.jpg")
    fun getThumbnail(@Path("videoId") id: String): Call<ResponseBody>
}