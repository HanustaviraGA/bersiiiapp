package com.example.bersiiiiii

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.bersiiiiii.databinding.ActivityLogInBinding
import org.json.JSONArray
import java.util.HashMap

class LogIn : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private var email: String? = null
    private var pass: String? = null

    private val url = "https://bersii.my.id/api/login_mobile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val signbtn = findViewById<Button>(R.id.signupbtnlogin)
        signbtn.setOnClickListener{
            val intent = Intent (this, SignUp::class.java)
            startActivity(intent)
        }

        binding.loginbtnlogin.setOnClickListener{
            email = binding.emaillogin.text.toString().trim { it <= ' ' }
            pass = binding.passwordlogin.text.toString().trim { it <= ' ' }

            if (email!!.isNotEmpty()  && pass!!.isNotEmpty()){
                val stringRequest: StringRequest = object : StringRequest(
                    Method.POST,
                    url,
                    Response.Listener { response ->
                        val data = response.toString()
                        val jArray = JSONArray(data)
                        for(i in 0 until jArray.length()){
                            val jobject = jArray.getJSONObject(i)
                            val status = jobject.getString("message")
                            if(status == "Sukses"){
                                val id = jobject.getString("id")
                                val nama = jobject.getString("nama")
                                val email = jobject.getString("email")
                                val nomor_telepon = jobject.getString("nomor_telepon")
                                val alamat = jobject.getString("alamat")
                                val token = jobject.getString("token")
                                val intent = Intent(this@LogIn, dashboard::class.java)
                                val sharedPreference =  getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                                val editor = sharedPreference.edit()
                                editor.putString("id",id)
                                editor.putString("nama",nama)
                                editor.putString("email",email)
                                editor.putString("nomor_telepon",nomor_telepon)
                                editor.putString("alamat", alamat)
                                editor.putString("token", token)
                                editor.apply()
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(this@LogIn, status, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(
                            this@LogIn,
                            error.toString().trim { it <= ' ' },
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val data: MutableMap<String, String> = HashMap()
                        data["email"] = email!!
                        data["password"] = pass!!
                        return data
                    }
                }
                val requestQueue = Volley.newRequestQueue(applicationContext)
                requestQueue.add(stringRequest)
            }else{
                Toast.makeText(this,"Empty field are not allowed!!", Toast.LENGTH_SHORT).show()
            }
            //return@setOnClickListener bind.root
        }

        binding.forgotpasslogin.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_change_password, null)
            val email = view.findViewById<EditText>(R.id.etEmail)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener{_,_ ->
//                forgotpassword(email)
                Toast.makeText(this,"Dalam pengembangan", Toast.LENGTH_SHORT).show()
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener{_,_ -> })
            builder.show()
        }

        supportActionBar?.hide()
    }

//    private fun forgotpassword(email: EditText) {
//        if (email.text.toString().isEmpty()){
//            return
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
//            return
//        }
//        firebaseAuth.sendPasswordResetEmail(email.text.toString())
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful){
//                    Toast.makeText(this,"Email sent",Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent (this, menuapp::class.java)
//            startActivity(intent)
//        }
//    }
}