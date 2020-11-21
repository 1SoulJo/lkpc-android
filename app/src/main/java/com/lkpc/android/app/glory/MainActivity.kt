package com.lkpc.android.app.glory

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.lkpc.android.app.glory.constants.Notification.Companion.CHANNEL_ID
import com.lkpc.android.app.glory.constants.WebUrls
import com.lkpc.android.app.glory.ui.calendar.CalendarActivity
import com.lkpc.android.app.glory.ui.note.NoteListActivity
import com.lkpc.android.app.glory.ui.qr_code.QrCodeGeneratorActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        setupNavigationView()
        setupNotification()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isOpen) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed();
        }
    }

    private fun setupNavigationView() {
        val navController = findNavController(R.id.nav_host_fragment)

        // setup drawer
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_column, R.id.navigation_sermon,
            R.id.navigation_meditation, R.id.navigation_notifications,
            R.id.nav_menu_online_service, R.id.nav_menu_my_note),
            drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        drawer_nav_view.setupWithNavController(navController)
        drawer_nav_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_menu_online_service -> {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.LKPC_LIVE_VIDEO)))
                    true
                }

                R.id.nav_menu_my_note -> {
                    startActivity(Intent(this, NoteListActivity::class.java))
                    true
                }

                R.id.nav_menu_qr_code -> {
                    startActivity(Intent(this, QrCodeGeneratorActivity::class.java))
                    true
                }

                R.id.nav_menu_calendar -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                    true
                }

                R.id.nav_menu_online_giving -> {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.ONLINE_DONATE)))
                    true
                }

                R.id.nav_menu_homepage -> {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.LKPC_HOMEPAGE)))
                    true
                }
                else -> true
            }
        }

        // setup bottom navigation
        bottom_nav_view.setupWithNavController(navController)
        bottom_nav_view.setOnNavigationItemReselectedListener {
            return@setOnNavigationItemReselectedListener
        }
    }

    private fun setupNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            mChannel.enableVibration(false)
            mChannel.setSound(null, null)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}