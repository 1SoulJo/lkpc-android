package com.lkpc.android.app.glory.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.AdContent
import com.lkpc.android.app.glory.repository.AdContentRepository

class HomeViewModel : ViewModel() {
    lateinit var adapter: HomeSlidePagerAdapter

    fun init(f: Fragment) {
        adapter = HomeSlidePagerAdapter(f)
        initData()
    }

    fun getData(): LiveData<List<AdContent>> {
        return AdContentRepository.data
    }

    private fun initData() {
        AdContentRepository.initData()
    }

    fun addData() {
        AdContentRepository.addData()
    }
}