package com.lkpc.android.app.glory.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.constants.WebUrls
import com.lkpc.android.app.glory.ui.basic_webview.BasicWebviewActivity
import com.lkpc.android.app.glory.ui.bulletin.BulletinActivity
import com.lkpc.android.app.glory.ui.yt_channels.YoutubeChannelActivity
import kotlinx.android.synthetic.main.fragment_home.*


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

        // setup view pager
        val viewModel: HomeViewModel by viewModels()
        viewModel.init(this)
        viewModel.getData().observe(requireActivity(), { ads ->
            viewModel.adapter.setData(ads.toMutableList())
        })
        home_view_pager.adapter = viewModel.adapter
        TabLayoutMediator(tab_layout, home_view_pager) { _, _ ->
            tab_layout.bringToFront()
        }.attach()

        // setup grids
        grid_center_layout_1.setOnClickListener {
            // LPC Live
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.LKPC_LIVE_VIDEO)))
        }
        grid_center_layout_2.setOnClickListener {
            startActivity(Intent(requireContext(), YoutubeChannelActivity::class.java))
        }
        grid_center_layout_3.setOnClickListener {
            // Newcomer registration
            val i = Intent(requireContext(), BasicWebviewActivity::class.java)
            i.putExtra("title", R.string.newcomer_reg_kr)
            i.putExtra("url", WebUrls.NEWCOMER_REG)
            startActivity(i)
        }
        grid_center_layout_4.setOnClickListener {
            // Bulletin
            val i = Intent(requireContext(), BulletinActivity::class.java)
            startActivity(i)
        }
        grid_center_layout_5.setOnClickListener {
            // Worship pre-registration
            val i = Intent(requireContext(), BasicWebviewActivity::class.java)
            i.putExtra("title", R.string.worship_pre_reg_kr)
            i.putExtra("url", WebUrls.VISIT_REG)
            startActivity(i)
        }
        grid_center_layout_6.setOnClickListener {
            // Online offering
            val i = Intent(requireContext(), BasicWebviewActivity::class.java)
            i.putExtra("title", R.string.online_offering_kr)
            i.putExtra("url", WebUrls.ONLINE_OFFERING)
            startActivity(i)
        }

        // setup youtube live area
//        val yf = childFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerSupportFragmentX
//        yf.initialize(
//            BuildConfig.YOUTUBE_API,
//            object : YouTubePlayer.OnInitializedListener {
//                override fun onInitializationSuccess(
//                    provider: YouTubePlayer.Provider,
//                    youTubePlayer: YouTubePlayer, b: Boolean) {
//
//                    // do any work here to cue video, play video, etc.
//                    youTubePlayer.cueVideo(WebUrls.LKPC_LIVE_ID)
//                }
//
//                override fun onInitializationFailure(
//                    provider: YouTubePlayer.Provider,
//                    youTubeInitializationResult: YouTubeInitializationResult
//                ) {
//                }
//            }
//        )

    }
}