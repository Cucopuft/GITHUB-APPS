package com.example.may_githubapp.Adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.may_githubapp.FragmentFollower
import com.example.may_githubapp.FragmentFollowing

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, data: Bundle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var fragmentBundle: Bundle = data

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragmentFollower = FragmentFollower()
                fragmentFollower.arguments = this.fragmentBundle
                fragmentFollower
            }
            1 -> {
                val fragmentFollowing = FragmentFollowing()
                fragmentFollowing.arguments = this.fragmentBundle
                fragmentFollowing
            }
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = 2
}

