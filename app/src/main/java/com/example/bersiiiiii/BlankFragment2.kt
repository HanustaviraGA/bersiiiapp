package com.example.bersiiiiii

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank2, container, false)
        val nxtbtn22 : ImageView = view.findViewById(R.id.onbroadnxt3)
        nxtbtn22.setOnClickListener{
            val fragment = BlankFragment3()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView,fragment)?.commit()
        }
        val skipvw : TextView = view.findViewById(R.id.skiponbrd3)
        skipvw.setOnClickListener{
            val intent = Intent (this@BlankFragment2.requireContext(), LogIn::class.java)
            startActivity(intent)
        }
        val bckvw : TextView = view.findViewById(R.id.bckonboard3)
        bckvw.setOnClickListener{
            val fragment = BlankFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView,fragment)?.commit()
        }
        return view
    }
}