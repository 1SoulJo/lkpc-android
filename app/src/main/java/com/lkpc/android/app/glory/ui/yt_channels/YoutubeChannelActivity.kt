package com.lkpc.android.app.glory.ui.yt_channels

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.constants.WebUrls
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_youtube_channel.*

class YoutubeChannelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_channel)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        // setup action bar
        ab_title.text = getString(R.string.lpc_youtube_channels_kr)
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            finish()
        }

        // setup buttons
        channel1.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.LKPC_CHANNEL)))
        }
        channel2.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.YA_CHANNEL)))
        }
        channel3.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.EC_CHANNEL)))
        }
        channel4.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.NHF_CHANNEL)))
        }
        channel5.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.DT_CHANNEL)))
        }
    }
}