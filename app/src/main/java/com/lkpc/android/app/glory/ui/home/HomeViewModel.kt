package com.lkpc.android.app.glory.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lkpc.android.app.glory.entity.AdContent

class HomeViewModel : ViewModel() {
    private var _adItems: MutableList<AdContent> = mutableListOf()
    lateinit var adapter: HomeSlidePagerAdapter

    fun init(f: Fragment) {
        adapter = HomeSlidePagerAdapter(f)
    }

    fun fetchAdContent() {
        _adItems = mutableListOf(
            AdContent(title = "Text Ad", url = "https://lkpc.org",
                imgUrl = "https://cdn.pixabay.com/photo/2020/11/11/08/54/meadow-5731886_1280.jpg", bmp = null),
            AdContent(title = "Text Ad2", url = "https://lkpc.org",
                imgUrl = "https://cdn.pixabay.com/photo/2020/11/08/09/41/deer-5723225_1280.jpg", bmp = null),
            AdContent(title = "Text Ad2", url = "https://lkpc.org",
                imgUrl = "https://cdn.pixabay.com/photo/2020/07/27/02/30/hands-5441201_1280.jpg", bmp = null)
        )

        adapter.adContents = _adItems
    }
}