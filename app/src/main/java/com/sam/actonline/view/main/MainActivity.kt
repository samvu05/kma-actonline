package com.sam.actonline.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.sam.actonline.R
import com.sam.actonline.base.BaseActivity
import com.sam.actonline.databinding.ActivityMainBinding
import com.sam.actonline.view.adapter.ScreenSlidePagerAdapter
import com.sam.actonline.view.calendar.CalendarFragment
import com.sam.actonline.view.home.HomeFragment
import com.sam.actonline.view.more.MoreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sam.actonline.extention.showLog
import com.sam.actonline.view.course.CoursesFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
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
        checkListFile()
    }

    private fun setupViewPager() {
        mAdapter = ScreenSlidePagerAdapter(this@MainActivity.supportFragmentManager)
            .apply {
                addFragment(HomeFragment(), "Trang chủ")
                addFragment(CoursesFragment(), "Khoá học")
                addFragment(CalendarFragment(), "Lịch")
                addFragment(MoreFragment(), "Tuỳ chọn")
            }

        mViewPager.apply {
            adapter = mAdapter
            offscreenPageLimit = mAdapter.count
            addOnPageChangeListener(this@MainActivity)
        }

        mBottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_main_home -> mViewPager.currentItem = 0
                R.id.nav_main_course -> mViewPager.currentItem = 1
                R.id.nav_main_calendar -> mViewPager.currentItem = 2
                R.id.navbot_item_more -> mViewPager.currentItem = 3
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


    private fun checkListFile() {
        val ROOT_FOLDER = "ACT Elearning"
        val baseDirectory: String =
            this@MainActivity.getExternalFilesDir(null)!!.path + File.separator + ROOT_FOLDER + File.separator + "Hello"
        val file = File(baseDirectory)

        showLog("Is direc: ${file.isDirectory}")
        showLog("name: ${file.name}")
        showLog("Path: ${file.path}")
        showLog("listFiles: ${file.listFiles()}")
        showLog("absoluteFile: ${file.absoluteFile}")
        showLog("canonicalFile: ${file.canonicalFile}")
        showLog("totalSpace: ${file.totalSpace}")
        showLog("lastModified: ${file.lastModified()}")
        showLog("freeSpace: ${file.freeSpace}")


        if (file.list() == null || file.list().isNullOrEmpty()) {
            showLog("Size Error")
        } else {
            showLog("File Size: ${file.list()?.size ?: ""}")
        }
    }

}