package com.lkpc.android.app.glory.ui.bulletin

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
import kotlinx.android.synthetic.main.activity_bulletin.*

class BulletinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulletin)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        val isDowntown = intent.getBooleanExtra("isDowntown", false)

        if (isDowntown) {
            ab_title.setText(R.string.downtown_bulletin_kr)
        } else {
            ab_title.setText(R.string.bulletin_kr)
        }
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            finish()
        }

        rv_bulletin.layoutManager = LinearLayoutManager(this)
        rv_bulletin.adapter = BulletinAdapter()

        // data observation
        val viewModel : BulletinViewModel by viewModels { BulletinViewModelFactory(isDowntown) }

        val observer = Observer<List<BaseContent?>> { data ->
            if (data.isNullOrEmpty()) {
                bulletin_empty_text.visibility = View.VISIBLE
                return@Observer
            }
            if (rv_bulletin != null) {
                val adapter = rv_bulletin.adapter as BulletinAdapter
                if (adapter.isLoading) {
                    (adapter.bulletins as MutableList<BaseContent?>).removeAt(adapter.bulletins.size - 1)
                    adapter.isLoading = false
                }
                adapter.bulletins = data
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getData().observe(this, observer)

        // setup refresh
        bulletin_layout.setOnRefreshListener {
            viewModel.addData(0)
            bulletin_layout.isRefreshing = false
        }

        // scroll listener
        rv_bulletin.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val adapter = rv_bulletin.adapter as BulletinAdapter
                if (!rv_bulletin.canScrollVertically(1) && !adapter.isLoading) {
                    (adapter.bulletins as MutableList).add(null)
                    adapter.notifyItemInserted(adapter.bulletins.size - 1)
                    rv_bulletin.scrollToPosition(adapter.bulletins.size - 1)

                    viewModel.addData(adapter.itemCount - 1)

                    adapter.isLoading = true
                }
            }
        })
    }
}