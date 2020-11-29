package com.lkpc.android.app.glory.ui.detail

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.PlayerNotificationManager.BitmapCallback
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.gson.Gson
import com.lkpc.android.app.glory.BuildConfig
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.api_client.ContentApi
import com.lkpc.android.app.glory.api_client.ContentApiClient
import com.lkpc.android.app.glory.constants.ContentType
import com.lkpc.android.app.glory.constants.Notification.Companion.CHANNEL_ID
import com.lkpc.android.app.glory.constants.Notification.Companion.SERMON_AUDIO_ID
import com.lkpc.android.app.glory.constants.WebUrls.Companion.SERMON_AUDIO_SRC
import com.lkpc.android.app.glory.entity.BaseContent
import com.lkpc.android.app.glory.ui.note.NoteDetailActivity
import com.lkpc.android.app.glory.ui.note.NoteEditActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.tool_bar.*
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity() {
    private val _editNodeActivityResultCode = 1
    private lateinit var playerNotificationManager: PlayerNotificationManager

    private var noteId : Int = -1
    private lateinit var content : BaseContent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        noteId = intent.getIntExtra("noteId", -1)

        val contentId = intent.getStringExtra("singleContentId")
        if (contentId.isNullOrEmpty()) {
            val data = intent.getStringExtra("data")
            content = Gson().fromJson(data, BaseContent::class.java)

            // fill main content area
            fillContent(content)
            invalidateOptionsMenu()

            ContentApiClient().increaseViewCount(content.id!!)
        } else {
            ContentApiClient().loadSingleContent(contentId, object: Callback<BaseContent> {
                override fun onResponse(call: Call<BaseContent>, response: Response<BaseContent>) {
                    content = response.body()!!
                    fillContent(content)
                    invalidateOptionsMenu()

                    ContentApiClient().increaseViewCount(content.id!!)
                }

                override fun onFailure(call: Call<BaseContent>, t: Throwable) { }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // returning from NoteEditActivity
        if (requestCode == _editNodeActivityResultCode) {
            noteId = resultCode
            invalidateOptionsMenu()
        }
    }

    override fun onPause() {
        super.onPause()
        detail_youtube_fragment.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (detail_audio.player != null) {
            playerNotificationManager.setPlayer(null)
            detail_audio.player!!.release()
            detail_audio.player = null
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val newNoteMenu = menu!!.findItem(R.id.detail_menu_new_note)
        val openNoteMenu = menu.findItem(R.id.detail_menu_open_note)

        if (noteId > -1) {
            openNoteMenu.isVisible = true
            newNoteMenu.isVisible = false
        } else {
            openNoteMenu.isVisible = false
            newNoteMenu.isVisible = true
        }

        if (this::content.isInitialized) {
            if (content.category == ContentType.SERMON
                || content.category == ContentType.CELL_CHURCH) {
                menu.findItem(R.id.detail_menu_share).isVisible = false
            }

            if (content.category == ContentType.NEWS
                || content.category == ContentType.CELL_CHURCH
                || content.category == ContentType.FELLOW_NEWS) {
                newNoteMenu.isVisible = false
                openNoteMenu.isVisible = false
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.content_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

            R.id.detail_menu_new_note -> {
                val i = Intent(this, NoteEditActivity::class.java)
                i.putExtra("type", content.category)
                i.putExtra("title", content.title)
                i.putExtra("contentId", content.id)
                startActivityForResult(i, _editNodeActivityResultCode)
            }

            R.id.detail_menu_open_note -> {
                val i = Intent(this, NoteDetailActivity::class.java)
                i.putExtra("noteId", noteId)
                startActivity(i)
            }

            R.id.detail_menu_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "${content.title} \n ${content_body.text}")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun fillContent(content: BaseContent) {
        // title
        toolbar_title.text = content.title

        // file area
        if (content.files!!.isNotEmpty()) {
            rv_files.layoutManager = LinearLayoutManager(this)
            rv_files.adapter = FileAdapter(content.files!!)
        }

        // content title
        content_title.text = content.title

        // chapter
        if (content.category == ContentType.SERMON) {
            content_chapter.visibility = View.VISIBLE
            content_chapter.text = content.chapter
        }

        // date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.CANADA)
        val newFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.CANADA)
        content_date.text = newFormat.format(dateFormat.parse(content.dateCreated!!)!!)

        // setup youtube video is available
        if (content.videoLink != null) {
            setupYoutubeView(content.videoLink!!)
        }

        // setup audio if available
        if (content.audioLink != null) {
            playerNotificationManager = PlayerNotificationManager(
                this, CHANNEL_ID, SERMON_AUDIO_ID,
                DescriptionAdapter(getString(R.string.title_sermon), content.title!!))

            val audioPlayer = SimpleExoPlayer.Builder(this).build()
            val url = SERMON_AUDIO_SRC.format(content.audioLink)
            val mediaItem: MediaItem = MediaItem.fromUri(Uri.parse(url))
            audioPlayer.setMediaItem(mediaItem)
            audioPlayer.prepare()
            audioPlayer.addListener(object : Player.EventListener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    if (isPlaying) {
                        playerNotificationManager.setPlayer(audioPlayer)
                    }
                }
            })

            val audioAttributes: AudioAttributes = AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .build()
            audioPlayer.setAudioAttributes(audioAttributes, true)

            detail_audio.showTimeoutMs = -1
            detail_audio.player = audioPlayer

            btn_audio.visibility = View.VISIBLE
        }

        // remove video and audio file
        val whitelist = Whitelist()
        whitelist.addTags("b", "em", "div", "p", "h1", "h2", "strong", "ol", "li", "ul", "u", "br")
        val doc = Jsoup.parse(content.boardContent)
        val newDoc = Jsoup.clean(doc.toString(), whitelist)

        // content body
        if (content.category == ContentType.SERMON) {
            // setup video/audio buttons
            buttons_area.visibility = View.VISIBLE
            btn_video.setOnClickListener {
                detail_youtube_layout.visibility = View.VISIBLE
                (detail_audio as PlayerControlView).hide()
                if (detail_audio.player != null) {
                    (detail_audio.player as SimpleExoPlayer).pause()
                }
            }
            btn_audio.setOnClickListener {
                detail_youtube_layout.visibility = View.GONE
                (detail_audio as PlayerControlView).show()
            }
        } else {
            content_body.text =
                HtmlCompat.fromHtml(newDoc, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        }
    }

    private fun setupYoutubeView(id: String) {
        val yf = detail_youtube_fragment as YouTubePlayerSupportFragmentX
        yf.initialize(
            BuildConfig.YOUTUBE_API,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                                     youTubePlayer: YouTubePlayer, b: Boolean) {
                    youTubePlayer.cueVideo(id)
                    btn_video.visibility = View.VISIBLE
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult) {
                }
            }
        )
    }

    private class DescriptionAdapter(val title: String, val contentText: String) :
        PlayerNotificationManager.MediaDescriptionAdapter {

        override fun getCurrentContentTitle(player: Player): String {
            return title
        }

        @Nullable
        override fun getCurrentContentText(player: Player): String? {
            return contentText
        }

        @Nullable
        override fun getCurrentLargeIcon(
            player: Player,
            callback: BitmapCallback
        ): Bitmap? {
            return null
        }

        @Nullable
        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return null
        }
    }
}