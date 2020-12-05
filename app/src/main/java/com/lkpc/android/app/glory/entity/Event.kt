package com.lkpc.android.app.glory.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Event {
    @SerializedName("starttime")
    @Expose
    var startTime: String? = null

    @SerializedName("endtime")
    @Expose
    var endTime: String? = null

    @SerializedName("summary")
    @Expose
    var summary: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null
}