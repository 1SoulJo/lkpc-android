package com.lkpc.android.app.glory.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AdContent {
    @SerializedName("_id")
    @Expose
    val id: String? = null

    @SerializedName("category")
    @Expose
    val category: String? = null

    @SerializedName("linkOrder")
    @Expose
    val linkOrder: Int? = null

    @SerializedName("linkUrl")
    @Expose
    val linkUrl: String? = null

    @SerializedName("linkImg")
    @Expose
    val linkImg: String? = null

    @SerializedName("forMobile")
    @Expose
    var forMobile: String? = null

    @SerializedName("__v")
    @Expose
    val v: Int? = null
}