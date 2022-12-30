package com.example.bersiiiiii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.bersiiiiii.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONArray
import java.util.HashMap

class SignUp : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding

    private var nama: String? = null
    private var email: String? = null
    private var pass: String? = null

    private val url = "https://bersii.my.id/api/register_mobile"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.signupbtnregis.setOnClickListener{
            nama = binding.nameregis.text.toString().trim { it <= ' ' }
            email = binding.emailregis.text.toString().trim { it <= ' ' }
            pass = binding.passregis.text.toString().trim { it <= ' ' }
            if (nama!!.isNotEmpty() && email!!.isNotEmpty()  && pass!!.isNotEmpty()){
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
                                val intent = Intent(this@SignUp, LogIn::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(this@SignUp, status, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(
                            this@SignUp,
                            error.toString().trim { it <= ' ' },
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val data: MutableMap<String, String> = HashMap()
                        data["nama"] = nama!!
                        data["email"] = email!!
                        data["password"] = pass!!
                        return data
                    }
                }
                val requestQueue = Volley.newRequestQueue(applicationContext)
                requestQueue.add(stringRequest)
            }
            else{
                Toast.makeText(this,"Empty field are not allowed!!", Toast.LENGTH_SHORT).show()
            }
        }
        loginbtnregis.setOnClickListener{
            val intent = Intent (this, LogIn::class.java)
            startActivity(intent)
        }
    }
}