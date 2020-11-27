package com.lkpc.android.app.glory.ui.location

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.Location
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.tool_bar.*

class LocationActivity : AppCompatActivity() {
    private val locations = listOf(
        Location("미시사가", "6965 Professional Court, Mississauga, ON, L4V 1Y3",
        "Tel: 905-677-7729", "Fax: 905-677-7739", "Email: info@lkpc.org",
        "주일 차량 안내를 참고하십시오.", null, R.drawable.location_mississauga),
        Location("다운타운", "455 Huron Street(Bloor St. W / Huron Street), Toronto, ON, M5R 3P2",
        "Tel: 647-436-1977", null, null, null, null, R.drawable.location_downtown),
        Location("업타운", "3377 Bayview Ave, North York, ON, M2K 3A8",
        "Tel: 647-436-1977", null, null, "Chapel", null, R.drawable.location_uptown),
        Location("노스욕 지역", "틴데일 신학교 3377 Bayview Ave, North York, ON, M2K 3A8",
        null, null, null, "(새벽기도장소: 건물 정문을 사용하여 주십시오)",
        "매주 월~금요일 오전 6시", R.drawable.location_north_york),
        Location("옥빌 지역", "John Knox Christian School, 2232 Sㅔheridan Garden Drive, Oakville, ON",
        null, null, null, null, "매주 월~금요일 오전 6시", R.drawable.location_oakville )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        setSupportActionBar(toolbar)

        toolbar_title.setText(R.string.navigation)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = LocationAdapter()
        adapter.setLocations(locations)

        rv_location.layoutManager = LinearLayoutManager(this)
        rv_location.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}