package com.lkpc.android.app.glory.api_client

import com.lkpc.android.app.glory.constants.WebUrls.Companion.LKPC_BASE_URL
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Retrofit

class BasicRequestClient {
    private fun setupApi(url: String): BasicRequestApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .build()

        return retrofit.create(BasicRequestApi::class.java)
    }

    fun getImage(url: String, cb: Callback<ResponseBody>) {
        setupApi(LKPC_BASE_URL).getImage(url).enqueue(cb)
    }
}