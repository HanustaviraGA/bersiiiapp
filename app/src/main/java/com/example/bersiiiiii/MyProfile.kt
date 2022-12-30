package com.example.bersiiiiii

import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.bersiiiiii.databinding.ActivityMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_my_profile.*
import org.json.JSONArray
import java.util.HashMap

class MyProfile : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private val url = "https://bersii.my.id/api/logout_mobile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val sharedPreference = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val nama = sharedPreference.getString("nama","defaultName").toString()
        val emailss = sharedPreference.getString("email","defaultEmail").toString()
        val token = sharedPreference.getString("token","defaultToken").toString().trim { it <= ' ' }
        val token_subs = token.substringBefore(delimiter = "|", missingDelimiterValue = "Token Tidak Terdeteksi").trim { it <= ' ' }

        val bckbtn = findViewById<ImageView>(R.id.bckbtnprofile)
        bckbtn.setOnClickListener{
            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)
        }
        val logoutbtn = findViewById<Button>(R.id.logoutbtnprfl)
        logoutbtn.setOnClickListener{
//            Logout disini
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    val data = response.toString()
                    val jArray = JSONArray(data)
                    for(i in 0 until jArray.length()){
                        val jobject = jArray.getJSONObject(i)
                        val status = jobject.getString("message")
                        if(status == "Token Dicabut"){
                            val intent = Intent(this@MyProfile, LogIn::class.java)
                            val editor = sharedPreference.edit()
                            editor.clear()
                            editor.remove("id")
                            editor.remove("nama")
                            editor.remove("email")
                            editor.remove("nomor_telepon")
                            editor.remove("alamat")
                            editor.remove("token")
                            editor.apply()
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this@MyProfile, status, Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "my Message")
                        }
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        this@MyProfile,
                        error.toString().trim { it <= ' ' },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val data: MutableMap<String, String> = HashMap()
                    data["token"] = token_subs
                    return data
                }
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer $token"
                    return headers
                }
            }
            val requestQueue = Volley.newRequestQueue(applicationContext)
            requestQueue.add(stringRequest)
        }
        val privacybtn1 = findViewById<TextView>(R.id.PrivaccyPolicyTxt)
        privacybtn1.setOnClickListener {
            val intent = Intent(this, PolicyPrivacy::class.java)
            startActivity(intent)
        }
        val privacybtn2 = findViewById<ImageView>(R.id.privacyico)
        privacybtn2.setOnClickListener {
            val intent = Intent(this, PolicyPrivacy::class.java)
            startActivity(intent)
        }
        val privacybtn3 = findViewById<ImageView>(R.id.arrowbtnprivacy)
        privacybtn3.setOnClickListener {
            val intent = Intent(this, PolicyPrivacy::class.java)
            startActivity(intent)
        }
        binding.ChangePassTxt.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Change Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_change_password, null)
            val email = view.findViewById<EditText>(R.id.etEmail)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener{ _, _ ->
                forgotpassword(email)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener{ _, _ -> })
            builder.show()
        }
        binding.changepassico.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Change Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_change_password, null)
            val email = view.findViewById<EditText>(R.id.etEmail)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener{ _, _ ->
                forgotpassword(email)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener{ _, _ -> })
            builder.show()
        }
        binding.arrowbtnchangepass.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Change Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_change_password, null)
            val email = view.findViewById<EditText>(R.id.etEmail)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener{ _, _ ->
                forgotpassword(email)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener{ _, _ -> })
            builder.show()
        }
        binding.txtWelcomeMyProfile.text = "Welcome, $nama"
        binding.emailprofile.text = emailss
    }

    private fun forgotpassword(email: EditText?) {
        if (email!!.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
    }
}