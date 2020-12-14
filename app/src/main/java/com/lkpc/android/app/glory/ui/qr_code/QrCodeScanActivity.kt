package com.lkpc.android.app.glory.ui.qr_code

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.QrInfo
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_qr_code.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class QrCodeScanActivity : AppCompatActivity() {
    val requestCameraPermission = 201

    lateinit var barcodeDetector: BarcodeDetector
    lateinit var cameraSource: CameraSource

    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        ab_title.text = getString(R.string.qr_code_scan_title)
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            finish()
        }

        rv_qr_list.layoutManager = LinearLayoutManager(this)
        rv_qr_list.adapter = QrCodeAdapter()

        btn_qr_code_clear.setOnClickListener {
            showListClearConfirmPopup()
        }

        btn_qr_code_send_mail.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        initializeDetectorsAndSources()
    }

    override fun onPause() {
        super.onPause()
        cameraSource.release()
    }

    private fun initializeDetectorsAndSources() {
        barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(300, 300).setAutoFocusEnabled(true).build()

        surface_view.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            this@QrCodeScanActivity,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surface_view.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            this@QrCodeScanActivity,
                            arrayOf(Manifest.permission.CAMERA), requestCameraPermission)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int, width: Int, height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() { }

            override fun receiveDetections(detections: Detections<Barcode>) {
                if (this@QrCodeScanActivity::dialog.isInitialized && dialog.isShowing) {
                    return
                }

                val barCodes = detections.detectedItems
                if (barCodes.size() > 0) {
                    if (barCodes.valueAt(0) != null) {
                        Log.d("QrCodeScanActivity", barCodes.valueAt(0).displayValue)
                        MainScope().launch {
                            showQrConfirmPopup(barCodes.valueAt(0).displayValue)
                        }
                    }
                }
            }
        })
    }

    private fun showQrConfirmPopup(info: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("QR Code 스캔 완료")

        val infoTextView = TextView(this)
        infoTextView.text = info
        builder.setView(infoTextView)

        builder.setPositiveButton("저장") { dialog, _ ->
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA)
            (rv_qr_list.adapter as QrCodeAdapter).addInfo(
                QrInfo(info, sdf.format(Date())))
            rv_qr_list.smoothScrollToPosition(0)

            dialog.dismiss()
        }

        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.cancel()
        }

        dialog = builder.show()
    }

    private fun showListClearConfirmPopup() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("리스트를 삭제합니다")

        builder.setPositiveButton("확인") { dialog, _ ->
            (rv_qr_list.adapter as QrCodeAdapter).clearList()
            rv_qr_list.smoothScrollToPosition(0)

            dialog.dismiss()
        }

        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.cancel()
        }

        dialog = builder.show()
    }
}