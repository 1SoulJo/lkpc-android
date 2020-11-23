package com.lkpc.android.app.glory.ui.home

import android.content.Intent
import android.graphics.Bitmap
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
import com.lkpc.android.app.glory.constants.WebUrls
import com.lkpc.android.app.glory.entity.AdContent
import com.lkpc.android.app.glory.ui.basic_webview.BasicWebviewActivity
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
        return fragments[position]
    }

    fun setData(ads : MutableList<AdContent>) {
        adContents = ads
        for (ad in adContents) {
            val f = AdItemFragment(ad)
            val client = BasicRequestClient()
            client.getImage(WebUrls.IMG_ASSET.format(ad.linkImg!!),
                cb = object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, res: Response<ResponseBody>) {
                        if (res.isSuccessful && res.body() != null) {
                            f.img = BitmapFactory.decodeStream(res.body()!!.byteStream())
                            f.refresh()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        t?.printStackTrace()
                    }
                })
            fragments.add(f)
        }
        notifyDataSetChanged()
    }
}

class AdItemFragment(val content: AdContent) : Fragment() {
    var img : Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_ad_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (img != null) {
            home_ad_item_img.setImageBitmap(img)
            home_ad_item_img.visibility = View.VISIBLE
        }
        home_ad_item_layout.setOnClickListener {
            val i = Intent(view.context, BasicWebviewActivity::class.java)
            i.putExtra("title", R.string.banner_title)
            i.putExtra("url", WebUrls.LKPC_HOMEPAGE + content.linkUrl)
            view.context.startActivity(i)
        }
    }

    fun refresh() {
        if (img != null && home_ad_item_img != null) {
            home_ad_item_img.setImageBitmap(img)

            TransitionManager.beginDelayedTransition(
                home_ad_item_layout as ViewGroup, Fade(Fade.IN))
            home_ad_item_img.visibility = View.VISIBLE
        }
    }
}