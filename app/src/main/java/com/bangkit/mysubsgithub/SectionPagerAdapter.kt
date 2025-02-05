package com.bangkit.mysubsgithub

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bangkit.mysubsgithub.ui.followersFragment
import com.bangkit.mysubsgithub.ui.followingFragment

class SectionPagerAdapter(private val context: Context, fm: FragmentManager, data: Bundle): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @StringRes
    private val tabTitles = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getCount(): Int = tabTitles.size

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = followersFragment()
            1 -> fragment = followingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(tabTitles[position])
    }
}