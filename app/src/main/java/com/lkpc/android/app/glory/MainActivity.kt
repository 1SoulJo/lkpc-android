package com.lkpc.android.app.glory

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.lkpc.android.app.glory.constants.Notification.Companion.CHANNEL_ID
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

    private fun setupNavigationView() {
        val navController = findNavController(R.id.nav_host_fragment)

        // setup drawer
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_column, R.id.navigation_sermon,
            R.id.navigation_meditation, R.id.navigation_notifications,
            R.id.drawer_menu1, R.id.drawer_menu2),
            drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        drawer_nav_view.setupWithNavController(navController)

        // setup bottom navigation
        bottom_nav_view.setupWithNavController(navController)
        bottom_nav_view.setOnNavigationItemReselectedListener {
            return@setOnNavigationItemReselectedListener
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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