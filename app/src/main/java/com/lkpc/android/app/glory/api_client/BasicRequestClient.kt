package com.lkpc.android.app.glory.api_client

import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Retrofit

class BasicRequestClient {
    private fun setupApi(): BasicRequestApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://lkpc.org")
            .build()

        return retrofit.create(BasicRequestApi::class.java)
    }

    fun getImage(url: String, cb: Callback<ResponseBody>) {
        setupApi().getImage(url).enqueue(cb)
    }
}