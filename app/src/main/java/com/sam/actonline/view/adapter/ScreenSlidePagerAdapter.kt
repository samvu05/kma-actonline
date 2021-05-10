package com.sam.actonline.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by Dinh Sam Vu on 3/3/2021.
 */

@Suppress("DEPRECATION")
class ScreenSlidePagerAdapter (fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {

    private val mFragmentList = mutableListOf<Fragment>()
    private val mTitleList = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mTitleList.add(title)
    }

    override fun getCount(): Int = mFragmentList.size

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getPageTitle(position: Int): CharSequence {
        return mTitleList[position]
    }
}