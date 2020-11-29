package com.lkpc.android.app.glory.ui.fellow_news

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
import kotlinx.android.synthetic.main.activity_fellow_news.*

class FellowNewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fellow_news)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        ab_title.text = getString(R.string.fellow_news)

        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            finish()
        }

        rv_fellow_news.layoutManager = LinearLayoutManager(this)
        rv_fellow_news.adapter = FellowNewsAdapter()

        // data observation
        val viewModel : FellowNewsViewModel by viewModels()

        val observer = Observer<List<BaseContent?>> { data ->
            if (rv_fellow_news != null) {
                val adapter = rv_fellow_news.adapter as FellowNewsAdapter
                if (adapter.isLoading) {
                    (adapter.fellowNews as MutableList<BaseContent?>)
                        .removeAt(adapter.fellowNews.size - 1)
                    adapter.isLoading = false
                }
                adapter.fellowNews = data
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getData().observe(this, observer)

        // setup refresh
        fellow_news_layout.setOnRefreshListener {
            viewModel.addData(0)
            fellow_news_layout.isRefreshing = false
        }

        // scroll listener
        rv_fellow_news.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val adapter = rv_fellow_news.adapter as FellowNewsAdapter
                if (!rv_fellow_news.canScrollVertically(1) && !adapter.isLoading) {
                    (adapter.fellowNews as MutableList).add(null)
                    adapter.notifyItemInserted(adapter.fellowNews.size - 1)
                    rv_fellow_news.scrollToPosition(adapter.fellowNews.size - 1)

                    viewModel.addData(adapter.itemCount - 1)

                    adapter.isLoading = true
                }
            }
        })
    }
}