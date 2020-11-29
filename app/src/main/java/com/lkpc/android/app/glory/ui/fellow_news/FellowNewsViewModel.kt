package com.lkpc.android.app.glory.ui.fellow_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.repository.FellowNewsRepository

class FellowNewsViewModel : ViewModel() {
    init {
        initData()
    }

    fun getData(): LiveData<List<BaseContent>> {
        return FellowNewsRepository.data
    }

    private fun initData() {
        FellowNewsRepository.initData()
    }

    fun addData(id: Int) {
        FellowNewsRepository.addData(id)
    }
}