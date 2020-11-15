package com.lkpc.android.app.glory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.lkpc.android.app.glory.ui.note.NoteListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        btn_title_note.setOnClickListener {
            val i = Intent(this, NoteListActivity::class.java)
            startActivity(i)
        }
    }

    fun setActionBarTitle(id: Int) {
        action_bar_title.text = getString(id)
    }
}