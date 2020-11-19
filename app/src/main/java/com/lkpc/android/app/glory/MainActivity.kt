package com.lkpc.android.app.glory

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lkpc.android.app.glory.constants.Notification.Companion.CHANNEL_ID
import com.lkpc.android.app.glory.ui.more_menu.MoreMenuDialog
import com.lkpc.android.app.glory.ui.note.NoteListActivity
import com.lkpc.android.app.glory.ui.qr_code.QrCodeGeneratorActivity
import kotlinx.android.synthetic.main.action_bar.*


class MainActivity : AppCompatActivity(), DialogInterface.OnDismissListener {
    private var selectedMenuIndex: Int? = 0
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setupNavigationView()
        setupNotification()
    }

    private fun setupNavigationView() {
        val navController = findNavController(R.id.nav_host_fragment)
        navView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener { item ->
            // find current menu id
            val menu: Menu = navView.menu
            for (i in 0 until menu.size()) {
                val mi: MenuItem = menu.getItem(i)
                if (mi.isChecked) {
                    selectedMenuIndex = i
                }
            }

            // show dialog for more menu
            if (item.itemId == R.id.navigation_more) {
//                item.isCheckable = false
                MoreMenuDialog().show(supportFragmentManager, MoreMenuDialog.TAG)
            } else {
                NavigationUI.onNavDestinationSelected(item, navController)
            }
            true
        }
        navView.setOnNavigationItemReselectedListener {
            return@setOnNavigationItemReselectedListener
        }
    }

    @SuppressLint("WrongConstant")
    private fun setupActionBar() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)
        ab_btn_back.visibility = View.GONE
//        ab_btn_qr.visibility = View.VISIBLE
//        ab_btn_qr.setOnClickListener {
//            val i = Intent(this, QrCodeGeneratorActivity::class.java)
//            startActivity(i)
//        }
//        ab_btn_my_note.visibility = View.VISIBLE
//        ab_btn_my_note.setOnClickListener {
//            val i = Intent(this, NoteListActivity::class.java)
//            startActivity(i)
//        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        if (selectedMenuIndex != null) {
            navView.menu[selectedMenuIndex!!].isChecked = true
        }
    }

    fun setActionBarTitle(id: Int) {
        ab_title.text = getString(id)
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