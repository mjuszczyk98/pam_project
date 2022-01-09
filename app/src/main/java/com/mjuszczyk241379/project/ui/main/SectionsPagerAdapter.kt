package com.mjuszczyk241379.project.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mjuszczyk241379.project.R


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val _tabTitles = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3
    )

    private val _fragments = arrayOf(
        PasswordFragment.newInstance(),
        SynchroniseFragment.newInstance(),
        SettingsFragment.newInstance()
    )

    override fun getItem(position: Int): Fragment {
        return _fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(_tabTitles[position])
    }

    override fun getCount(): Int {
        return _tabTitles.size
    }
}