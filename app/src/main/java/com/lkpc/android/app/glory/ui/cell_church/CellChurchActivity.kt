package com.lkpc.android.app.glory.ui.cell_church

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
import kotlinx.android.synthetic.main.activity_cell_church.*


class CellChurchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cell_church)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        ab_title.text = getString(R.string.cell_church)

        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            finish()
        }

        rv_cell_church.layoutManager = LinearLayoutManager(this)
        rv_cell_church.adapter = CellChurchAdapter()

        // data observation
        val viewModel : CellChurchViewModel by viewModels()

        val observer = Observer<List<BaseContent?>> { data ->
            if (rv_cell_church != null) {
                val adapter = rv_cell_church.adapter as CellChurchAdapter
                if (adapter.isLoading) {
                    (adapter.cellChurchListItems as MutableList<BaseContent?>)
                        .removeAt(adapter.cellChurchListItems.size - 1)
                    adapter.isLoading = false
                }
                adapter.cellChurchListItems = data
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getData().observe(this, observer)

        // setup refresh
        cell_church_layout.setOnRefreshListener {
            viewModel.addData(0)
            cell_church_layout.isRefreshing = false
        }

        // scroll listener
        rv_cell_church.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val adapter = rv_cell_church.adapter as CellChurchAdapter
                if (!rv_cell_church.canScrollVertically(1) && !adapter.isLoading) {
                    (adapter.cellChurchListItems as MutableList).add(null)
                    adapter.notifyItemInserted(adapter.cellChurchListItems.size - 1)
                    rv_cell_church.scrollToPosition(adapter.cellChurchListItems.size - 1)

                    viewModel.addData(adapter.itemCount - 1)

                    adapter.isLoading = true
                }
            }
        })
    }
}