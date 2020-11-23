package com.lkpc.android.app.glory.repository

import androidx.lifecycle.MutableLiveData
import com.lkpc.android.app.glory.api_client.ContentApiClient
import com.lkpc.android.app.glory.entity.AdContent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AdContentRepository : Callback<List<AdContent>> {
    private val apiClient: ContentApiClient = ContentApiClient()

    var data: MutableLiveData<List<AdContent>> = MutableLiveData<List<AdContent>>()

    fun initData() {
        if (data.value == null || data.value!!.isEmpty()) {
            apiClient.loadAdContents(this)
        }
    }

    fun addData() {
        apiClient.loadAdContents(this)
    }

    override fun onResponse(
        call: Call<List<AdContent>>, response: Response<List<AdContent>>
    ) {
        if (response.isSuccessful) {
            if (data.value == null) {
                data.value = response.body()
            } else {
                val list = (data.value as List<AdContent>).toMutableList()
                if (list.isNotEmpty()) list.removeLastOrNull()

                list += response.body() as List<AdContent>
                data.value = list
            }
        }
    }

    override fun onFailure(call: Call<List<AdContent>>?, t: Throwable?) {
        t?.printStackTrace()
    }
}