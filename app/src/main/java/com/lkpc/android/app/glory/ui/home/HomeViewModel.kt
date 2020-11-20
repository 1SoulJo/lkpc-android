package com.lkpc.android.app.glory.ui.home

import androidx.fragment.app.Fragment
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
                imgUrl = "https://www.lkpc.org/assets/images/lkpc_1605025477184.jpg", bmp = null),
            AdContent(title = "Text Ad2", url = "https://lkpc.org",
                imgUrl = "https://www.lkpc.org/assets/images/lkpc_1605025477184.jpg", bmp = null)
        )

        adapter.adContents = _adItems
    }
}