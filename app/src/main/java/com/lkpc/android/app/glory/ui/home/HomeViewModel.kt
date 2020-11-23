package com.lkpc.android.app.glory.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.AdContent
import com.lkpc.android.app.glory.repository.AdContentRepository

class HomeViewModel : ViewModel() {
    private var _adItems: MutableList<AdContent> = mutableListOf()
    lateinit var adapter: HomeSlidePagerAdapter

    fun init(f: Fragment) {
        adapter = HomeSlidePagerAdapter(f)
        initData()
//
//        getData().observe(f.viewLifecycleOwner, { adItems ->
//            _adItems = adItems.toMutableList()
//
//            adapter = HomeSlidePagerAdapter(f)
//            adapter.adContents = _adItems
//        })
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