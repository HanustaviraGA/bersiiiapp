package com.example.bersiiiiii.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bersiiiiii.*
import com.example.bersiiiiii.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        /*
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        //val view = inflater.inflate(R.layout.fragment_home, container, false)
        val bathbtn : ImageView = root.findViewById(R.id.bathkatalog)
        bathbtn.setOnClickListener{
            val intent = Intent (this@HomeFragment.requireContext(), prdctBath::class.java)
            startActivity(intent)
        }
        val babybtn : ImageView = root.findViewById(R.id.babykatalog)
        babybtn.setOnClickListener{
            val intent = Intent (this@HomeFragment.requireContext(), prdctBaby::class.java)
            startActivity(intent)
        }
        val cookbtn : ImageView = root.findViewById(R.id.cookkatalog)
        cookbtn.setOnClickListener{
            val intent = Intent (this@HomeFragment.requireContext(), prdctCook::class.java)
            startActivity(intent)
        }
        val brkfastbtn : ImageView = root.findViewById(R.id.breakfastkatalog)
        brkfastbtn.setOnClickListener{
            val intent = Intent (this@HomeFragment.requireContext(), prdctBreakfast::class.java)
            startActivity(intent)
        }
        val profile : ImageView = root.findViewById(R.id.userprofile)
        profile.setOnClickListener{
            val intent = Intent (this@HomeFragment.requireContext(), MyProfile::class.java)
            startActivity(intent)
        }
        /*
        val brkfastbtn : ImageView = root.findViewById(R.id.breakfastkatalog)
        brkfastbtn.setOnClickListener{
            val fragment = product_breakfast()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView2,fragment)?.commit()
        }
         */
        val prdctbtn : ImageView = root.findViewById(R.id.imageView17)
        prdctbtn.setOnClickListener{
            val intent = Intent (this@HomeFragment.requireContext(), productlist::class.java)
            startActivity(intent)
            //return@setOnClickListener bind.root
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}