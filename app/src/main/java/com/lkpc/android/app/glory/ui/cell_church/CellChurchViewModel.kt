package com.lkpc.android.app.glory.ui.cell_church

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.repository.CellChurchRepository

class CellChurchViewModel : ViewModel() {
    init {
        initData()
    }

    fun getData(): LiveData<List<BaseContent>> {
        return CellChurchRepository.data
    }

    private fun initData() {
        CellChurchRepository.initData()
    }

    fun addData(id: Int) {
        CellChurchRepository.addData(id)
    }
}