package com.lkpc.android.app.glory.ui.bulletin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.repository.BulletinRepository

class BulletinViewModel : ViewModel() {
    init {
        initData()
    }

    fun getData(): LiveData<List<BaseContent>> {
        return BulletinRepository.data
    }

    private fun initData() {
        BulletinRepository.initData()
    }

    fun addData(id: Int) {
        BulletinRepository.addData(id)
    }
}