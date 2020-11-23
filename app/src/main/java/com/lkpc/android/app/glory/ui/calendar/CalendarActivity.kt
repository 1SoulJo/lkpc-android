package com.lkpc.android.app.glory.ui.calendar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lkpc.android.app.glory.R
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
    }
}