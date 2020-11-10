package com.lkpc.android.app.glory.ui.news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.list_item_news.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_ITEM: Int = 0
    private val TYPE_LOADING: Int = 1

    var isLoading: Boolean = false

    var newsList: List<BaseContent?> = mutableListOf()

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var tvNewsTitle: TextView = view.news_title
        var tvNewsDate: TextView = view.news_date
    }

    class LoadingViewHolder(view: View): RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
            return ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_item_news, parent, false)
            )
        }
        return LoadingViewHolder(
            LayoutInflater.from(context).inflate(R.layout.loading_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val news = newsList[position]!!

            holder.tvNewsTitle.text = news.title

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.CANADA)
            val newFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CANADA)
            holder.tvNewsDate.text = newFormat.format(dateFormat.parse(news.dateCreated!!)!!)

            holder.itemView.setOnClickListener {
                val i = Intent(holder.itemView.context, DetailActivity::class.java)
                i.putExtra("data", Gson().toJson(newsList[position]))
                holder.itemView.context.startActivity(i)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (newsList[position] == null) TYPE_LOADING else TYPE_ITEM
    }
}