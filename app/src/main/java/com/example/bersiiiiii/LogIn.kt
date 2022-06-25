package com.example.bersiiiiii

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bersiiiiii.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Tag
import kotlinx.android.synthetic.main.activity_log_in.*
import java.util.regex.Pattern

class LogIn : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val signbtn = findViewById<Button>(R.id.signupbtnlogin)
        signbtn.setOnClickListener{
            val intent = Intent (this, SignUp::class.java)
            startActivity(intent)
        }
        val lgnbtn = findViewById<Button>(R.id.loginbtnlogin)
        binding.loginbtnlogin.setOnClickListener{
            val email = binding.emaillogin.text.toString()
            val pass = binding.passwordlogin.text.toString()

            if (email.isNotEmpty()  && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent (this, menuapp::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
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
                forgotpassword(email)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener{_,_ -> })
            builder.show()
        }

        supportActionBar?.hide()
    }

    private fun forgotpassword(email: EditText) {
        if (email.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Email sent",Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent (this, menuapp::class.java)
            startActivity(intent)
        }
    }
}