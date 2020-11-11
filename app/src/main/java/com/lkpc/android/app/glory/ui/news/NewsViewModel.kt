package com.lkpc.android.app.glory.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.repository.NewsRepository

class NewsViewModel : ViewModel() {
    init {
        initData()
    }

    fun getData(): LiveData<List<BaseContent>> {
        return NewsRepository.data
    }

    private fun initData() {
        NewsRepository.initData()
    }

    fun addData(id: Int) {
        NewsRepository.addData(id)
    }
}