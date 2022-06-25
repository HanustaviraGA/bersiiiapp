package com.example.bersiiiiii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bersiiiiii.databinding.ActivityMenuappBinding
import com.example.bersiiiiii.ui.dashboard.MessageFragment
import com.example.bersiiiiii.ui.dashboard.navigation_activity
import com.example.bersiiiiii.ui.home.HomeFragment
import com.example.bersiiiiii.ui.notifications.CartFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_menuapp.*

@Suppress("DEPRECATION")
class menuapp : AppCompatActivity() {

    //private lateinit var binding: ActivityMenuappBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuapp)

        val home  = HomeFragment()
        val mess = MessageFragment()
        val acc = navigation_activity()

        makeCurrentFragment(home)
        supportActionBar?.hide()
        val navigationHome = R.id.navigation_home
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_home -> makeCurrentFragment(home)
                R.id.navigation_activity -> makeCurrentFragment(acc)
                R.id.navigation_message -> makeCurrentFragment(mess)
            }
            true
        }
    }
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply{
        replace(R.id.fragmentContainerView2, fragment)
            commit()
    }
}