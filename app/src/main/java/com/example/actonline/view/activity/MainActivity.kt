package com.example.actonline.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.actonline.R
import com.example.actonline.base.BaseActivity
import com.example.actonline.databinding.ActivityMainBinding
import com.example.actonline.view.adapter.ScreenSlidePagerAdapter
import com.example.actonline.view.fragment.CalendarFragment
import com.example.actonline.view.fragment.HomeFragment
import com.example.actonline.view.fragment.NotificationFragment
import com.example.actonline.view.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>(), ViewPager.OnPageChangeListener {
    private lateinit var mViewPager: ViewPager
    private lateinit var mBottomNavigation: BottomNavigationView
    private lateinit var mAdapter: ScreenSlidePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun initView() {
        mViewPager = findViewById(R.id.viewpaper_home)
        mBottomNavigation = findViewById(R.id.nav_bottom_home)
        setupViewPager()
    }

    private fun setupViewPager() {
        mAdapter = ScreenSlidePagerAdapter(this@MainActivity.supportFragmentManager)
        mAdapter.addFragment(HomeFragment(), "Trang chủ")
        mAdapter.addFragment(CalendarFragment(), "Thời khoá biểu")
        mAdapter.addFragment(NotificationFragment(), "Thông báo")
        mAdapter.addFragment(ProfileFragment(), "Cá nhân")
        mViewPager.adapter = mAdapter
        mViewPager.offscreenPageLimit = mAdapter.count
        mViewPager.addOnPageChangeListener(this)

        mBottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navbot_item_home -> mViewPager.currentItem = 0
                R.id.nav_bottom_calendar -> mViewPager.currentItem = 1
                R.id.navbot_item_notification -> mViewPager.currentItem = 2
                R.id.navbot_item_profile -> mViewPager.currentItem = 3
            }
            true
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mBottomNavigation.menu.getItem(position).isChecked = true
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}