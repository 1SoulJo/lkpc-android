package com.lkpc.android.app.glory.ui.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lkpc.android.app.glory.MainActivity
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.BaseContent
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val a = activity as MainActivity
        a.setActionBarTitle(R.string.title_notifications)

        rv_news.layoutManager = LinearLayoutManager(activity)
        rv_news.adapter = NewsAdapter(activity as Context)

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
        viewModel.getData().observe(activity as LifecycleOwner, observer)

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