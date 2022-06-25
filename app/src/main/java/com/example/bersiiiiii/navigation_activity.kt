package com.example.bersiiiiii.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bersiiiiii.Ongoing
import com.example.bersiiiiii.R
import com.example.bersiiiiii.databinding.FragmentDashboardBinding
import com.example.bersiiiiii.notification

class navigation_activity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_activity, container, false)
        val updtbtn : TextView = view.findViewById(R.id.txtOngoing)
        updtbtn.setOnClickListener{
            val fragment = Ongoing()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView2,fragment)?.commit()
        }
        return view
    }
}