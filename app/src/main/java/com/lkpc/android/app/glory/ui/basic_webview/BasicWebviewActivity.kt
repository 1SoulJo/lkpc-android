package com.lkpc.android.app.glory.ui.basic_webview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.api_client.ContentApiClient
import com.lkpc.android.app.glory.entity.BaseContent
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_basic_webview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasicWebviewActivity : AppCompatActivity() {
    companion object {
        const val TYPE_URL = 0
        const val TYPE_SERVICE_INFO = 1
        const val TYPE_NAV_GUIDE = 2
    }

    private val apiCallback = object : Callback<List<BaseContent>> {
        override fun onResponse(
            call: Call<List<BaseContent>>,
            response: Response<List<BaseContent>>
        ) {
            if (response.isSuccessful) {
                val list = response.body() as List<BaseContent>
                var htmlData = list[0].boardContent!!
                htmlData = htmlData.replace('↵', 0.toChar())
                webview.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
            }
        }

        override fun onFailure(call: Call<List<BaseContent>>, t: Throwable) {
            t?.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_webview)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        // title
        ab_title.setText(intent.getIntExtra("title", R.string.lpc))

        // back button
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener { finish() }

        // web view setting
        webview.settings.javaScriptEnabled = true
        webview.settings.domStorageEnabled = true
        webview.settings.builtInZoomControls = true
        webview.settings.displayZoomControls = false

        // type
        val type = intent.getIntExtra("type", 0)
        when(type) {
            TYPE_URL -> {
                val url = intent.getStringExtra("url")!!
                webview.loadUrl(url)
            }

            TYPE_SERVICE_INFO -> {
                val apiClient = ContentApiClient()
                apiClient.loadServices(apiCallback)
            }

            TYPE_NAV_GUIDE -> {
                val apiClient = ContentApiClient()
                apiClient.loadLocations(apiCallback)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        webview.onResume()
    }

    override fun onPause() {
        super.onPause()
        webview.onPause()
    }
}