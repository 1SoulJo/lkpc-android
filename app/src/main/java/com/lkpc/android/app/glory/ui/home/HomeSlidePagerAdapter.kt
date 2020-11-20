package com.lkpc.android.app.glory.ui.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.api_client.BasicRequestClient
import com.lkpc.android.app.glory.entity.AdContent
import kotlinx.android.synthetic.main.home_ad_item.*
import kotlinx.android.synthetic.main.home_ad_item.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeSlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
    var adContents: MutableList<AdContent> = mutableListOf()
    var fragments: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int {
        return adContents.count()
    }

    override fun createFragment(position: Int): Fragment {
        fragments.add(position, AdItemFragment(adContents[position]))
        return fragments[position]
    }
}

class AdItemFragment(val content: AdContent) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_ad_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val client = BasicRequestClient()
        client.getImage(content.imgUrl, cb = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, res: Response<ResponseBody>) {
                if (res.isSuccessful && res.body() != null) {
                    home_ad_item_img.setImageBitmap(
                        BitmapFactory.decodeStream(
                            res.body()!!.byteStream()
                        )
                    )
                    TransitionManager.beginDelayedTransition(view as ViewGroup, Fade(Fade.IN))
                    view.home_ad_item_img.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }
}