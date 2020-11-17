package com.lkpc.android.app.glory.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.gson.Gson
import com.lkpc.android.app.glory.BuildConfig
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.ui.note.NoteDetailActivity
import com.lkpc.android.app.glory.ui.note.NoteEditActivity
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_detail.*
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        ab_btn_qr.visibility = View.GONE

        val data = intent.getStringExtra("data")
        val content = Gson().fromJson(data, BaseContent::class.java)
        fillContent(content)

        // back button
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener { finish() }

        // note button
        if (intent.getBooleanExtra("noteBtn", true)) {
            ab_btn_new_note.visibility = View.VISIBLE

            val i: Intent
            val noteId = intent.getIntExtra("noteId", -1)
            if (noteId > -1) {
                ab_btn_new_note_text.text = "노트보기"
                i = Intent(this, NoteDetailActivity::class.java)
                i.putExtra("noteId", noteId)
                ab_btn_new_note.setOnClickListener {
                    startActivity(i)
                }
            } else {
                i = Intent(this, NoteEditActivity::class.java)
                i.putExtra("type", content.category)
                i.putExtra("title", content.title)
                i.putExtra("contentId", content.id)
                ab_btn_new_note.setOnClickListener {
                    startActivityForResult(i, 123)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            ab_btn_new_note_text.text = "노트보기"
            val i = Intent(this, NoteDetailActivity::class.java)
            i.putExtra("noteId", resultCode)
            ab_btn_new_note.setOnClickListener {
                startActivity(i)
            }
        }
    }

    private fun fillContent(content: BaseContent) {
        // title
        ab_title.text = content.title

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
        detail_youtube_layout.visibility = View.VISIBLE

        val yf = supportFragmentManager.findFragmentById(R.id.detail_youtube_fragment) as YouTubePlayerSupportFragmentX
        yf.initialize(
            BuildConfig.YOUTUBE_API,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean) {

                    // do any work here to cue video, play video, etc.
                    youTubePlayer.cueVideo(id)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            }
        )
    }
}