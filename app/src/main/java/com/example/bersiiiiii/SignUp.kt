package com.example.bersiiiiii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.bersiiiiii.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        val lgnbtnsignup = findViewById<Button>(R.id.loginbtnregis)
        val signupbtn = findViewById<Button>(R.id.signupbtnregis)
        binding.signupbtnregis.setOnClickListener{
            val email = binding.emailregis.text.toString()
            val pass = binding.passregis.text.toString()
            val repass = binding.repassregis.text.toString()
            if (email.isNotEmpty()  && pass.isNotEmpty() && repass.isNotEmpty()){
                if (pass == repass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            firebaseAuth.signOut()
                            val intent = Intent (this, LogIn::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this,"Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Empty field are not allowed!!", Toast.LENGTH_SHORT).show()
            }
        }
        loginbtnregis.setOnClickListener{
            val intent = Intent (this, LogIn::class.java)
            startActivity(intent)
        }
        supportActionBar?.hide()
    }
}