package com.lkpc.android.app.glory.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseContent {
    @SerializedName("files")
    @Expose
    var files: List<String>? = null

    @SerializedName("comments")
    @Expose
    var comments: List<Any>? = null

    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("category")
    @Expose
    var category: String? = null

    @SerializedName("userName")
    @Expose
    var userName: String? = null

    @SerializedName("userEmail")
    @Expose
    var userEmail: String? = null

    @SerializedName("videoLink")
    @Expose
    var videoLink: String? = null

    @SerializedName("videoLinkAuthor")
    @Expose
    var videoLinkAuthor: String? = null

    @SerializedName("audioLink")
    @Expose
    var audioLink: String? = null

    @SerializedName("dateCreated")
    @Expose
    var dateCreated: String? = null

    @SerializedName("viewCount")
    @Expose
    var viewCount: Int? = null

    @SerializedName("boardContent")
    @Expose
    var boardContent: String? = null

    @SerializedName("chapter")
    @Expose
    var chapter: String? = null
}