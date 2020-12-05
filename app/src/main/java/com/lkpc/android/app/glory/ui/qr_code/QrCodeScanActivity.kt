package com.lkpc.android.app.glory.ui.qr_code

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.lkpc.android.app.glory.R
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_qr_code.*
import java.io.IOException


class QrCodeScanActivity : AppCompatActivity() {
    val requestCameraPermission = 201

    lateinit var barcodeDetector: BarcodeDetector
    lateinit var cameraSource: CameraSource

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
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource.start(surface_view.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            this@QrCodeScanActivity,
                            arrayOf(Manifest.permission.CAMERA), requestCameraPermission
                        )
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
                val barCodes = detections.detectedItems
                if (barCodes.size() > 0) {
                    if (barCodes.valueAt(0) != null) {
                        Log.d("HANSOL", barCodes.valueAt(0).displayValue)
                    }
                }
            }
        })
    }
}