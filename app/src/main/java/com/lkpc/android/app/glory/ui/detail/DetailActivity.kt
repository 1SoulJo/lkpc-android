package com.lkpc.android.app.glory.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.gson.Gson
import com.lkpc.android.app.glory.BuildConfig
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.BaseContent
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : YouTubeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getStringExtra("data")
        val content = Gson().fromJson(data, BaseContent::class.java)
        fillContent(content)

        // back button
        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        // note button
        findViewById<ImageView>(R.id.btn_note).setOnClickListener {
            TODO()
        }
    }

    private fun fillContent(content: BaseContent) {
        // title
        val actionBarTitle = findViewById<TextView>(R.id.action_bar_title)
        actionBarTitle.text = content.title

        // content title
        val contentTitle = findViewById<TextView>(R.id.content_title)
        contentTitle.text = content.title

        // date
        val contentDate = findViewById<TextView>(R.id.content_date)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.CANADA)
        val newFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CANADA)
        contentDate.text = newFormat.format(dateFormat.parse(content.dateCreated!!)!!)

        // setup youtube video is available
        val doc = Jsoup.parse(content.boardContent)
        val frames = doc.getElementsByTag("iframe")
        if (frames.isNotEmpty()) {
            val src = frames[0].attr("src").split("/")
            setupYoutubeView(src.last())
        }

        // setup audio if available
        val audios = doc.getElementsByTag("audio")
        for (audio in audios) {
            val src = audio.getElementsByTag("source")
            for (s in src)
                Log.d("Detail", s.attr("src"))
        }

        // remove video and audio file
        val whitelist = Whitelist();
        whitelist.addTags("b", "em", "div", "p", "h1", "h2", "strong", "ol", "li", "ul", "u", "br")
        val newDoc = Jsoup.clean(doc.toString(), whitelist)

        // content body
        val contentBody = findViewById<TextView>(R.id.content_body)
        contentBody.text = HtmlCompat.fromHtml(newDoc, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }

    private fun setupYoutubeView(id: String) {
        val youTubePlayerView = findViewById<View>(R.id.youtube_player) as YouTubePlayerView
        youTubePlayerView.visibility = View.VISIBLE

        youTubePlayerView.initialize(BuildConfig.YOUTUBE_API,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean) {

                    // do any work here to cue video, play video, etc.
                    youTubePlayer.cueVideo(id)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult) {
                }
            })
    }
}