package com.lkpc.android.app.glory.ui.sermon

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
import kotlinx.android.synthetic.main.fragment_sermon.*

class SermonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sermon, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // page title
        val a = activity as MainActivity
        a.setActionBarTitle(R.string.title_sermon)

        rv_sermon.layoutManager = LinearLayoutManager(activity)
        rv_sermon.adapter = SermonAdapter()

        // data observation
        val viewModel: SermonViewModel by viewModels()
        val observer = Observer<List<BaseContent?>> { data ->
            if (rv_sermon != null) {
                val adapter = rv_sermon.adapter as SermonAdapter
                if (adapter.isLoading) {
                    (adapter.sermons as MutableList<BaseContent?>).removeAt(adapter.sermons.size - 1)
                    adapter.isLoading = false
                }
                adapter.sermons = data
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getData().observe(activity as LifecycleOwner, observer)

        // scroll listener
        rv_sermon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val adapter = rv_sermon.adapter as SermonAdapter
                if (!rv_sermon.canScrollVertically(1) && !adapter.isLoading) {
                    (adapter.sermons as MutableList).add(null)
                    adapter.notifyItemInserted(adapter.sermons.size - 1)
                    rv_sermon.scrollToPosition(adapter.sermons.size - 1)

                    viewModel.addData(adapter.itemCount - 1)

                    adapter.isLoading = true
                }
            }
        })
    }

}