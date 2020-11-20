package com.lkpc.android.app.glory.ui.column

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
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_column.*

class ColumnFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_column, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requireActivity().toolbar_title.setText(R.string.title_column)

        rv_column.layoutManager = LinearLayoutManager(activity)
        rv_column.adapter = ColumnAdapter()

        // data observation
        val viewModel: ColumnViewModel by viewModels()
        val observer = Observer<List<BaseContent?>> { data ->
            if (rv_column != null) {
                val adapter = rv_column.adapter as ColumnAdapter
                if (adapter.isLoading) {
                    (adapter.columns as MutableList<BaseContent?>).removeAt(adapter.columns.size - 1)
                    adapter.isLoading = false
                }
                adapter.columns = data
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getData().observe(activity as LifecycleOwner, observer)

        // scroll listener
        rv_column.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val adapter = rv_column.adapter as ColumnAdapter
                if (!rv_column.canScrollVertically(1) && !adapter.isLoading) {
                    (adapter.columns as MutableList).add(null)
                    adapter.notifyItemInserted(adapter.columns.size - 1)
                    rv_column.scrollToPosition(adapter.columns.size - 1)

                    viewModel.addData(adapter.itemCount - 1)

                    adapter.isLoading = true
                }
            }
        })
    }
}