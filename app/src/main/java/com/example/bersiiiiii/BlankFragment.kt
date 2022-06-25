package com.example.bersiiiiii

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        val nxtbtn1 : ImageView = view.findViewById(R.id.onbroadnxt2)
        val skipvw : TextView = view.findViewById(R.id.skiponbrd2)
        nxtbtn1.setOnClickListener{
            val fragment = BlankFragment2()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView,fragment)?.commit()
        }
        skipvw.setOnClickListener{
            val intent = Intent (this@BlankFragment.requireContext(), LogIn::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent (this@BlankFragment.requireContext(), menuapp::class.java)
            startActivity(intent)
        }
    }

}