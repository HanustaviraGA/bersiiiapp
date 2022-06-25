package com.example.bersiiiiii

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.bersiiiiii.databinding.ActivityMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfile : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser

        supportActionBar?.hide()
        val bckbtn = findViewById<ImageView>(R.id.bckbtnprofile)
        bckbtn.setOnClickListener{
            val intent = Intent(this, menuapp::class.java)
            startActivity(intent)
        }
        val logoutbtn = findViewById<Button>(R.id.logoutbtnprfl)
        logoutbtn.setOnClickListener{
            firebaseAuth.signOut()
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
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
        if (user != null) {//set email
            binding.emailprofile.setText(user.email)
        }
    }

    private fun forgotpassword(email: EditText?) {
        if (email!!.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Email sent", Toast.LENGTH_SHORT).show()
                    firebaseAuth.signOut()
                    val intent = Intent(this, LogIn::class.java)
                    startActivity(intent)
                }
            }
    }
}