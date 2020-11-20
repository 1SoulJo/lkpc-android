package com.lkpc.android.app.glory.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.lkpc.android.app.glory.BuildConfig
import com.lkpc.android.app.glory.MainActivity
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.constants.WebUrls
import com.lkpc.android.app.glory.entity.AdContent
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_item_donate_homepage.*
import kotlinx.android.synthetic.main.home_item_pre_register.*
import kotlinx.android.synthetic.main.home_item_youtube_channels.*


class HomeFragment : Fragment() {
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

        // setup view pager
        val viewModel: HomeViewModel by viewModels()
        viewModel.init(this)
        home_view_pager.adapter = viewModel.adapter
        viewModel.fetchAdContent()
        TabLayoutMediator(tab_layout, home_view_pager) { _, _ ->
            tab_layout.bringToFront()
        }.attach()

        // setup youtube live area
        val yf = childFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerSupportFragmentX
        yf.initialize(
            BuildConfig.YOUTUBE_API,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean) {

                    // do any work here to cue video, play video, etc.
                    youTubePlayer.cueVideo(WebUrls.LKPC_LIVE_ID)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            }
        )

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