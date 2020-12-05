package com.lkpc.android.app.glory.api_client

import com.google.gson.GsonBuilder
import com.lkpc.android.app.glory.constants.WebUrls
import com.lkpc.android.app.glory.entity.Event
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventApiClient {

    private fun setupApi(): EventApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(WebUrls.EVENT_API_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(EventApi::class.java)
    }

    fun getAllEvents(cb: Callback<List<Event>>) {
        setupApi().getAllEvents().enqueue(cb)
    }

    fun getByMonth(month: String, cb: Callback<List<Event>>) {
        setupApi().getByMonth(month).enqueue(cb)
    }

    fun getByWeek(week: String, cb: Callback<List<Event>>) {
        setupApi().getByWeek(week).enqueue(cb)
    }
}