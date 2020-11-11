package com.lkpc.android.app.glory.repository

import androidx.lifecycle.MutableLiveData
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.api_client.ContentApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object NewsRepository: Callback<List<BaseContent>> {
    private val apiClient: ContentApiClient = ContentApiClient()

    var data: MutableLiveData<List<BaseContent>> = MutableLiveData<List<BaseContent>>()

    fun initData() {
        if (data.value == null || data.value!!.isEmpty()) {
            apiClient.loadNews(0, this)
        }
    }

    fun addData(startId: Int) {
        apiClient.loadNews(startId, this)
    }

    override fun onResponse(
        call: Call<List<BaseContent>>, response: Response<List<BaseContent>>
    ) {
        if (response.isSuccessful) {
            if (data.value == null) {
                data.value = response.body()
            } else {
                val list = (data.value as List<BaseContent>).toMutableList()
                if (list.isNotEmpty()) list.removeLastOrNull()

                list += response.body() as List<BaseContent>
                data.value = list
            }
        }
    }

    override fun onFailure(call: Call<List<BaseContent>>?, t: Throwable?) {
        t?.printStackTrace()
    }
}