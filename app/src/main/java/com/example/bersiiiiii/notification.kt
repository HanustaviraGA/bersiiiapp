package com.example.bersiiiiii

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bersiiiiii.ui.dashboard.MessageFragment

class notification : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        val updtbtn : TextView = view.findViewById(R.id.txtUpdt)
        updtbtn.setOnClickListener{
            val fragment = MessageFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView2,fragment)?.commit()
        }
        return view
    }
}