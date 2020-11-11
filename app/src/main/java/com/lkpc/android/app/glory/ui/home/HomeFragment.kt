package com.lkpc.android.app.glory.ui.home

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lkpc.android.app.glory.MainActivity
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.constants.WebUrls
import com.lkpc.android.app.glory.api_client.YoutubeImgClient
import kotlinx.android.synthetic.main.home_item_donate_homepage.*
import kotlinx.android.synthetic.main.home_item_live_video.*
import kotlinx.android.synthetic.main.home_item_pre_register.*
import kotlinx.android.synthetic.main.home_item_youtube_channels.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private val _youtubeLiveId = "11d9FBX9t-I"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // page title
        val a = activity as MainActivity
        a.setActionBarTitle(R.string.title_home)

        // setup visit registration button
        visit_reg.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.VISIT_REG)))
        }

        // setup youtube live area
        val client = YoutubeImgClient()
        client.getThumbnail(videoId = _youtubeLiveId, cb = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val bmp = BitmapFactory.decodeStream(response.body()!!.byteStream())
                        if (youtube_live_img != null) {
                            youtube_live_img.setImageBitmap(bmp)
                            youtube_live_img.setOnClickListener {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(WebUrls.LKPC_LIVE)
                                    )
                                )
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })

        // donate / homepage buttons
        btn_donate.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.ONLINE_DONATE)))
        }

        btn_homepage.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.LKPC_HOMEPAGE)))
        }

        // setup youtube channel links
        setupYoutubeChannelLinks()

    }

    private fun setupYoutubeChannelLinks() {
        channel1.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.LKPC_CHANNEL)))
        }

        channel2.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.EC_CHANNEL)))
        }

        channel3.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.NHF_CHANNEL)))
        }

        channel4.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.DT_CHANNEL)))
        }

        channel5.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.YA_CHANNEL)))
        }
    }
}