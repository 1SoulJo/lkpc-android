package com.lkpc.android.app.glory.ui.sermon

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.repository.YoutubeImgClient
import com.lkpc.android.app.glory.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.list_item_sermon.view.*
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class SermonAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_ITEM: Int = 0
    private val TYPE_LOADING: Int = 1

    var isLoading: Boolean = false

    var sermons: List<BaseContent?> = mutableListOf()

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var clLayout: ConstraintLayout = view as ConstraintLayout
        var tvSermonTitle: TextView = view.sermon_title
        var tvSermonName: TextView = view.sermon_name
        var tvSermonDate: TextView = view.sermon_date
    }

    class LoadingViewHolder(view: View): RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
            return ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_item_sermon, parent, false)
            )
        }
        return LoadingViewHolder(
            LayoutInflater.from(context).inflate(R.layout.loading_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.clLayout.background = ResourcesCompat.getDrawable(
                holder.clLayout.context.resources, R.drawable.thumbnail_youtube_lpc, null)

            val sermon = sermons[position]!!
            holder.tvSermonTitle.text =
                sermon.title!!.replace(")", ")" + System.getProperty("line.separator")!!)
            holder.tvSermonName.text = sermon.videoLinkAuthor

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.CANADA)
            val newFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CANADA)
            holder.tvSermonDate.text = newFormat.format(dateFormat.parse(sermon.dateCreated!!)!!)

            val doc = Jsoup.parse(sermon.boardContent)
            val frames = doc.getElementsByTag("iframe")
            if (frames.isNotEmpty()) {
                val videoId = frames[0].attr("src").split("/").last()
                val client = YoutubeImgClient()
                client.getThumbnail(videoId = videoId, cb = object: Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val bmp = BitmapFactory.decodeStream(response.body()!!.byteStream())
                                holder.clLayout.background =
                                    BitmapDrawable(holder.clLayout.context.resources, bmp)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        t?.printStackTrace()
                    }
                })
            }

            holder.itemView.setOnClickListener {
                val i = Intent(holder.itemView.context, DetailActivity::class.java)
                i.putExtra("data", Gson().toJson(sermons[position]))
                holder.itemView.context.startActivity(i)
            }
        }
    }

    override fun getItemCount(): Int {
        return sermons.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (sermons[position] == null) TYPE_LOADING else TYPE_ITEM
    }
}