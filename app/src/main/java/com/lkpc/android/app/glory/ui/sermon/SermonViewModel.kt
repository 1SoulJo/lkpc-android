package com.lkpc.android.app.glory.ui.sermon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.repository.SermonRepository

class SermonViewModel : ViewModel() {
    init {
        initData()
    }

    fun getData(): LiveData<List<BaseContent>> {
        return SermonRepository.data
    }

    private fun initData() {
        SermonRepository.initData()
    }

    fun addData(id: Int) {
        SermonRepository.addData(id)
    }
}