package com.lkpc.android.app.glory.api_client

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface BasicRequestApi {
    @GET
    fun getImage(@Url url: String) : Call<ResponseBody>
}