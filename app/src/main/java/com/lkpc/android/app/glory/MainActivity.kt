package com.lkpc.android.app.glory

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.lkpc.android.app.glory.constants.Notification.Companion.CHANNEL_ID
import com.lkpc.android.app.glory.constants.WebUrls
import com.lkpc.android.app.glory.ui.basic_webview.BasicWebviewActivity
import com.lkpc.android.app.glory.ui.calendar.CalendarActivity
import com.lkpc.android.app.glory.ui.column.ColumnFragment
import com.lkpc.android.app.glory.ui.home.HomeFragment
import com.lkpc.android.app.glory.ui.meditation.MeditationFragment
import com.lkpc.android.app.glory.ui.news.NewsFragment
import com.lkpc.android.app.glory.ui.note.NoteListActivity
import com.lkpc.android.app.glory.ui.qr_code.QrCodeGeneratorActivity
import com.lkpc.android.app.glory.ui.sermon.SermonFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.tool_bar.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val CURRENT_FRAGMENT = "current_fragment"
        const val TAG_HOME = "home"
        const val TAG_COLUMN = "column"
        const val TAG_SERMON = "sermon"
        const val TAG_MEDITATION = "meditation"
        const val TAG_NEWS = "news"
    }

    private val homeFragment : HomeFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_HOME)
        if (fr != null) {
            fr as HomeFragment
        } else {
            HomeFragment()
        }
    }

    private val columnFragment : ColumnFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_COLUMN)
        if (fr != null) {
            fr as ColumnFragment
        } else {
            ColumnFragment()
        }
    }

    private val sermonFragment : SermonFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_SERMON)
        if (fr != null) {
            fr as SermonFragment
        } else {
            SermonFragment()
        }
    }

    private val meditationFragment : MeditationFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_MEDITATION)
        if (fr != null) {
            fr as MeditationFragment
        } else {
            MeditationFragment()
        }
    }

    private val newsFragment : NewsFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_NEWS)
        if (fr != null) {
            fr as NewsFragment
        } else {
            NewsFragment()
        }
    }

    private var selectedFragment : Int = R.id.navigation_home
    private var activeFragment  : Fragment? = null

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        toolbar_title.setText(R.string.title_home)

        // retrieve current fragment from savedInstanceState
        savedInstanceState?.let {
            selectedFragment = it.getInt(CURRENT_FRAGMENT, R.id.navigation_home)
        }

        when (selectedFragment) {
            R.id.navigation_home -> activeFragment = homeFragment
            R.id.navigation_column -> activeFragment = columnFragment
            R.id.navigation_sermon -> activeFragment = sermonFragment
            R.id.navigation_meditation -> activeFragment = meditationFragment
            R.id.navigation_news -> activeFragment = newsFragment
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().detach(nav_host_fragment).commitNow()
            //add all fragments but show only active fragment
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment, homeFragment, TAG_HOME).hide(homeFragment)
                .add(R.id.nav_host_fragment, columnFragment, TAG_COLUMN).hide(columnFragment)
                .add(R.id.nav_host_fragment, sermonFragment, TAG_SERMON).hide(sermonFragment)
                .add(R.id.nav_host_fragment, meditationFragment , TAG_MEDITATION).hide(meditationFragment)
                .add(R.id.nav_host_fragment, newsFragment , TAG_NEWS).hide(newsFragment)
                .show(activeFragment!!)
                .commit()
        }

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
            super.onBackPressed()
        }
    }

    private fun setupNavigationView() {
        val navController = findNavController(R.id.nav_host_fragment)

        // setup drawer
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_column, R.id.navigation_sermon,
            R.id.navigation_meditation, R.id.navigation_news,
            R.id.nav_menu_qr_code, R.id.nav_menu_my_note, R.id.nav_menu_online_meet,
            R.id.nav_menu_church_events, R.id.nav_menu_service_info,
            R.id.nav_menu_nav_guide), drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        drawer_nav_view.setupWithNavController(navController)
        drawer_nav_view.setNavigationItemSelectedListener { item ->
            drawer_layout.closeDrawer(GravityCompat.START)

            when (item.itemId) {
                R.id.nav_menu_qr_code -> {
                    startActivity(Intent(this, QrCodeGeneratorActivity::class.java))
                    true
                }
                R.id.nav_menu_my_note -> {
                    startActivity(Intent(this, NoteListActivity::class.java))
                    true
                }
                R.id.nav_menu_online_meet -> {
                    val i = Intent(this, BasicWebviewActivity::class.java)
                    i.putExtra("title", R.string.online_meet)
                    i.putExtra("url", WebUrls.ONLINE_MEET_REG)
                    startActivity(i)
                    true
                }
                R.id.nav_menu_church_events -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                    true
                }
                R.id.nav_menu_service_info -> {
                    val i = Intent(this, BasicWebviewActivity::class.java)
                    i.putExtra("type", BasicWebviewActivity.TYPE_SERVICE_INFO)
                    i.putExtra("title", R.string.service_info)
                    startActivity(i)
                    true
                }
                R.id.nav_menu_nav_guide -> {
//                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WebUrls.NAV_GUIDE)))
                    val i = Intent(this, BasicWebviewActivity::class.java)
                    i.putExtra("url", WebUrls.NAV_GUIDE)
                    i.putExtra("title", R.string.navigation)
                    startActivity(i)
                    true
                }
                else -> true
            }
        }

        // setup bottom navigation
        bottom_nav_view.setupWithNavController(navController)
        bottom_nav_view.setOnNavigationItemSelectedListener {
            setFragment(it.itemId)
        }
        bottom_nav_view.setOnNavigationItemReselectedListener {
            return@setOnNavigationItemReselectedListener
        }
    }

    private fun setupNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText
            channel.enableVibration(false)
            channel.setSound(null, null)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setFragment(itemId: Int): Boolean {
        selectedFragment = itemId
        when (itemId){
            R.id.navigation_home -> {
                if (activeFragment is HomeFragment) {
                    return false
                }
                supportFragmentManager.beginTransaction().hide(activeFragment!!)
                    .show(homeFragment)
                    .commit()
                activeFragment = homeFragment
                toolbar_title.setText(R.string.title_home)
            }
            R.id.navigation_column  ->{
                if (activeFragment is ColumnFragment) {
                    return false
                }
                supportFragmentManager.beginTransaction().hide(activeFragment!!)
                    .show(columnFragment)
                    .commit()
                activeFragment = columnFragment
                toolbar_title.setText(R.string.title_column)
            }
            R.id.navigation_sermon ->{
                if (activeFragment is SermonFragment) {
                    return false
                }
                supportFragmentManager.beginTransaction().hide(activeFragment!!)
                    .show(sermonFragment)
                    .commit()
                activeFragment = sermonFragment
                toolbar_title.setText(R.string.title_sermon)
            }
            R.id.navigation_meditation ->{
                if (activeFragment is MeditationFragment) {
                    return false
                }
                supportFragmentManager.beginTransaction().hide(activeFragment!!)
                    .show(meditationFragment)
                    .commit()
                activeFragment = meditationFragment
                toolbar_title.setText(R.string.title_meditation)
            }
            R.id.navigation_news ->{
                if (activeFragment is NewsFragment) {
                    return false
                }
                supportFragmentManager.beginTransaction().hide(activeFragment!!)
                    .show(newsFragment).commit()
                activeFragment = newsFragment
                toolbar_title.setText(R.string.title_notifications)
            }
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_FRAGMENT, selectedFragment)
    }
}