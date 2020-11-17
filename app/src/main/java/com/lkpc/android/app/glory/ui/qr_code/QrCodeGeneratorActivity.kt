package com.lkpc.android.app.glory.ui.qr_code

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputType
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lkpc.android.app.glory.BuildConfig
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.constants.SharedPreference
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_qr_code_generator.*
import net.glxn.qrgen.android.QRCode
import java.io.ByteArrayOutputStream


class QrCodeGeneratorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code_generator)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        // get data from shared preference
        val sp = getSharedPreferences(SharedPreference.QR_PREFERENCE, Context.MODE_PRIVATE)
        val name = sp.getString(SharedPreference.QR_KEY_NAME, "")
        val phone = sp.getString(SharedPreference.QR_KEY_PHONE, "")
        val imgStr = sp.getString(SharedPreference.QR_KEY_IMAGE, "")
        qr_name.setText(name)
        qr_phone.setText(phone)
        if (imgStr != null && imgStr.isNotEmpty()) {
            val imageAsBytes: ByteArray = Base64.decode(imgStr.encodeToByteArray(), Base64.DEFAULT)
            img_qr_code.setImageBitmap(
                BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size))
        }

        ab_title.text = getString(R.string.qr_code_gen_title)
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            finish()
        }

        ab_qr_scanner.visibility = View.VISIBLE
        ab_qr_scanner.setOnClickListener {
            showPasswordAlert()
        }

        btn_qr_code_generate.setOnClickListener {
            if (qr_name.text.isEmpty() || qr_phone.text.isEmpty()) {
                Toast.makeText(this, "이름 및 휴대폰 번호를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bm: Bitmap = QRCode.from("${qr_name.text} ${qr_phone.text}")
                .withCharset("UTF-8").withSize(250, 250).bitmap()
            img_qr_code.setImageBitmap(bm)

            // update sharedPrefrences
            val outputStream = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val b: ByteArray = outputStream.toByteArray()
            sp.edit().putString(
                SharedPreference.QR_KEY_IMAGE,
                Base64.encodeToString(b, Base64.DEFAULT)
            ).apply()

            sp.edit().putString(SharedPreference.QR_KEY_NAME, "${qr_name.text}").apply()
            sp.edit().putString(SharedPreference.QR_KEY_PHONE, "${qr_phone.text}").apply()
        }
    }

    private fun showPasswordAlert() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Enter password")

        val sp = getSharedPreferences(SharedPreference.QR_PREFERENCE, Context.MODE_PRIVATE)
        val password = sp.getString(SharedPreference.QR_KEY_PASSWORD, "")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        if (password != null && password.isNotEmpty()) {
            input.setText(password)
        }
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            if (BuildConfig.QR_PASS.equals(input.text.toString(), false)) {

                sp.edit().putString(SharedPreference.QR_KEY_PASSWORD, BuildConfig.QR_PASS).apply()
                val i = Intent(this, QrCodeScanActivity::class.java)
                startActivity(i)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}