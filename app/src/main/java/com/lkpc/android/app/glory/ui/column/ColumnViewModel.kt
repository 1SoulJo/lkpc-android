package com.lkpc.android.app.glory.ui.column

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.repository.ColumnRepository
import com.lkpc.android.app.glory.repository.MeditationRepository

class ColumnViewModel : ViewModel() {
    init {
        initData()
    }

    fun getData(): LiveData<List<BaseContent>> {
        return ColumnRepository.data
    }

    private fun initData() {
        ColumnRepository.initData()
    }

    fun addData(id: Int) {
        ColumnRepository.addData(id)
    }
}