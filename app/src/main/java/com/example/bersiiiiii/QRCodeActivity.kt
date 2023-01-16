package com.example.bersiiiiii

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.graphics.Bitmap
import android.graphics.Point
import androidmads.library.qrgenearator.QRGEncoder
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidx.appcompat.app.AppCompatDelegate
import com.google.zxing.WriterException

class QRCodeActivity : AppCompatActivity() {
    //vaiables for imageview,edittext,button, bitmap and qrencoder.
    private var qrCodeIV: ImageView? = null
    private var generateQrBtn: Button? = null
    var bitmap: Bitmap? = null
    var qrgEncoder: QRGEncoder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode)
        generateQrBtn = findViewById(R.id.idBtnGenerateQR)
        generateQrBtn!!.setOnClickListener {
            val intent = Intent(this@QRCodeActivity, dashboard::class.java)
            startActivity(intent)
            finish()
        }

        val sharedPreference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val email = sharedPreference.getString("email","defaultEmail").toString()
        val password = sharedPreference.getString("password","defaultPassword").toString()

        val dataEdt = "{\"email\":\"$email\", \"password\":$password\"}"

        //intializing onclick listner for button.
        //below line is for getting the windowmanager service.
        val manager = getSystemService(WINDOW_SERVICE) as WindowManager
        //initializing a variable for default display.
        val display = manager.defaultDisplay
        //creating a variable for point which is to be displayed in QR Code.
        val point = Point()
        display.getSize(point)
        //getting width and height of a point
        val width = point.x
        val height = point.y
        //generating dimension from width and height.
        var dimen = if (width < height) width else height
        dimen = dimen * 4 / 6
        //setting this dimensions inside our qr code encoder to generate our qr code.
        qrgEncoder =
            QRGEncoder(dataEdt, null, QRGContents.Type.TEXT, dimen)
        try {
            //getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder!!.encodeAsBitmap()
            // the bitmap is set inside our image view using .setimagebitmap method.
            qrCodeIV!!.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            //this method is called for exception handling.
            Log.e("Tag", e.toString())
        }
    }
}