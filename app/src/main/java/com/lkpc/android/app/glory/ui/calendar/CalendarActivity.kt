package com.lkpc.android.app.glory.ui.calendar

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.Event
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.calendar_fragment.*

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_fragment)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        ab_title.text = getString(R.string.church_events)

        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener{
            finish()
        }

        val viewModel : CalendarViewModel by viewModels()

        rv_calendar.layoutManager = LinearLayoutManager(this)
        rv_calendar.adapter = viewModel.adapter
        viewModel.getData().observe(this as LifecycleOwner, { events ->
            (rv_calendar.adapter as CalendarAdapter).items = events
            (rv_calendar.adapter as CalendarAdapter).notifyDataSetChanged()
        })
    }
}