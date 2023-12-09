package com.dicoding.myiconnect.ui.home


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dicoding.myiconnect.R
import com.dicoding.myiconnect.databinding.ActivityMainBinding
import com.dicoding.myiconnect.ui.articelfragment.ArticelFragment
import com.dicoding.myiconnect.ui.articelfragment.DetailArticelFragment
import com.dicoding.myiconnect.ui.dictionaryfragment.DictionaryFragment
import com.dicoding.myiconnect.ui.homefragment.HomeFragment
import com.dicoding.myiconnect.ui.profilefragment.ProfileFragment
import com.dicoding.myiconnect.ui.translatefragment.TranslateFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragmentHome: Fragment = HomeFragment()
    private val fragmentArticel: Fragment = ArticelFragment()
    private val fragmentDictionary: Fragment = DictionaryFragment()
    private val fragmentTranslate: Fragment = TranslateFragment()
    private val fragmentProfile: Fragment = ProfileFragment()

    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentHome

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpBottomNav()
    }


    private fun setUpBottomNav() {
        fm.beginTransaction().add(R.id.container, fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentArticel, "2").hide(fragmentArticel).commit()
        fm.beginTransaction().add(R.id.container, fragmentDictionary, "3").hide(fragmentDictionary).commit()
        fm.beginTransaction().add(R.id.container, fragmentProfile, "5").hide(fragmentProfile).commit()

        bottomNavigationView = binding.navView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (active != fragmentHome) {
                        showFragment(fragmentHome)
                        active = fragmentHome
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_articel -> {
                    if (active != fragmentArticel) {
                        showFragment(fragmentArticel)
                        active = fragmentArticel
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_dictionary -> {
                    if (active != fragmentDictionary) {
                        showFragment(fragmentDictionary)
                        active = fragmentDictionary
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_translator -> {
                    if (active != fragmentTranslate) {
                        showFragment(fragmentTranslate)
                        active = fragmentTranslate
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_profile -> {
                    if (active != fragmentProfile) {
                        showFragment(fragmentProfile)
                        active = fragmentProfile
                        return@setOnNavigationItemSelectedListener true
                    }
                }
            }
            false
        }
    }

    private fun showFragment(fragment: Fragment) {
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
}