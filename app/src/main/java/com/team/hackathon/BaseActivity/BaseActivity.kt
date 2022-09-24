package com.team.hackathon.BaseActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.team.hackathon.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBaseBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
    fun startFragment(fragment: Fragment, addToBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        while (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
        }
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(binding.fragmentHolder.id, fragment, fragment.javaClass.simpleName)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }
}