package com.lkpc.android.app.glory.ui.meditation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.repository.MeditationRepository

class MeditationViewModel : ViewModel() {
    init {
        initData()
    }

    fun getData(): LiveData<List<BaseContent>> {
        return MeditationRepository.data
    }

    private fun initData() {
        MeditationRepository.initData()
    }

    fun addData(id: Int) {
        MeditationRepository.addData(id)
    }
}