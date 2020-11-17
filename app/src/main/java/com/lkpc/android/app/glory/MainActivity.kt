package com.lkpc.android.app.glory

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.lkpc.android.app.glory.ui.note.NoteListActivity
import com.lkpc.android.app.glory.ui.qr_code.QrCodeGeneratorActivity
import kotlinx.android.synthetic.main.action_bar.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        ab_btn_back.visibility = View.GONE

        ab_btn_qr.visibility = View.VISIBLE
        ab_btn_qr.setOnClickListener {
            val i = Intent(this, QrCodeGeneratorActivity::class.java)
            startActivity(i)
        }

        ab_btn_my_note.visibility = View.VISIBLE
        ab_btn_my_note.setOnClickListener {
            val i = Intent(this, NoteListActivity::class.java)
            startActivity(i)
        }
    }

    fun setActionBarTitle(id: Int) {
        ab_title.text = getString(id)
    }
}