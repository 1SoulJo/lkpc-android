package com.lkpc.android.app.glory.ui.news

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.BaseContent
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.fragment_news.*

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        // setup action bar
        ab_title.text = getString(R.string.title_notifications)
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            finish()
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rv_news.layoutManager = LinearLayoutManager(this)
        rv_news.adapter = NewsAdapter()

        // data observation
        val viewModel: NewsViewModel by viewModels()
        val observer = Observer<List<BaseContent?>> { data ->
            if (rv_news != null) {
                val adapter = rv_news.adapter as NewsAdapter
                if (adapter.isLoading) {
                    (adapter.newsList as MutableList<BaseContent?>).removeAt(adapter.newsList.size - 1)
                    adapter.isLoading = false
                }
                adapter.newsList = data
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getData().observe(this, observer)

        // scroll listener
        rv_news.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val adapter = rv_news.adapter as NewsAdapter
                if (!rv_news.canScrollVertically(1) && !adapter.isLoading) {
                    (adapter.newsList as MutableList).add(null)
                    adapter.notifyItemInserted(adapter.newsList.size - 1)
                    rv_news.scrollToPosition(adapter.newsList.size - 1)

                    viewModel.addData(adapter.itemCount - 1)

                    adapter.isLoading = true
                }
            }
        })
    }
}