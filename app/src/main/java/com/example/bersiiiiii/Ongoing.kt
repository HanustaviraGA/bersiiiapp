package com.example.bersiiiiii

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bersiiiiii.ui.dashboard.MessageFragment
import com.example.bersiiiiii.ui.dashboard.navigation_activity

class Ongoing : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ongoing, container, false)
        val updtbtn : TextView = view.findViewById(R.id.txtDoneMsg)
        updtbtn.setOnClickListener{
            val fragment = navigation_activity()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView2,fragment)?.commit()
        }
        return view
    }
}