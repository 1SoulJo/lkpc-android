package com.lkpc.android.app.glory.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.Event
import com.lkpc.android.app.glory.repository.EventRepository

class CalendarViewModel : ViewModel() {
    val adapter = CalendarAdapter()

    init {
        initData()
    }

    fun getData(): LiveData<List<Event>> {
        return EventRepository.data
    }

    private fun initData() {
        EventRepository.initData()
    }
}