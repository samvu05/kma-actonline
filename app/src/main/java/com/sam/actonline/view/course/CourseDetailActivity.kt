package com.sam.actonline.view.course

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.sam.actonline.R
import com.sam.actonline.base.BaseActivity
import com.sam.actonline.databinding.ActivityCourseDetailBinding
import com.sam.actonline.extention.setHtmlTextClickable
import com.sam.actonline.extention.setImageFromUrl
import com.sam.actonline.extention.setTextMarque
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Dinh Sam Vu on 5/15/2021.
 */

@AndroidEntryPoint
class CourseDetailActivity : BaseActivity<ActivityCourseDetailBinding>() {
    private var courseID: Int = -1
    private lateinit var courseName: String

    companion object {
        const val INTENT_COURSE_ID_KEY = "courseId"
        const val INTENT_COURSE_NAME = "courseName"
        const val INTENT_COURSE_TYPE = "courseType"
        const val INTENT_COURSE_IMAGE = "courseImage"
        const val INTENT_COURSE_DESCRIPTION = "courseDescription"
    }

    override fun initView() {
        getIntentData()
        coordinateMotion()
        setCourseContentFragment()
    }

    private fun getIntentData() {
        if (intent == null) return
        courseID = intent.getIntExtra(INTENT_COURSE_ID_KEY, -1)
        courseName = intent.getStringExtra(INTENT_COURSE_NAME) ?: ""

        binding.background.setImageFromUrl(
            intent.getStringExtra(
                INTENT_COURSE_IMAGE
            )
        )
        binding.tvTitle.setTextMarque(courseName)
        binding.tvDescription.setHtmlTextClickable(
            intent.getStringExtra(INTENT_COURSE_DESCRIPTION) ?: "..."
        )
    }

    private fun setCourseContentFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val courseSectionFragment = CourseContentFragment.newInstance(
            courseID, courseName
        )
        fragmentTransaction.replace(
            R.id.frame_container,
            courseSectionFragment,
            "CourseContent"
        ).commit()
    }

    private fun setForumFragment(forumId: Int) {
        setCourseContentFragment()
        supportFragmentManager.executePendingTransactions()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val forumFragment: Fragment = ForumFragment.newInstance(courseID, forumId, courseName)
        fragmentTransaction.addToBackStack(null)
            .replace(R.id.frame_container, forumFragment, "Announcements")
        fragmentTransaction.commit()
    }

    private fun coordinateMotion() {
        val appBarLayout: AppBarLayout = binding.appbarLayout
        val motionLayout: MotionLayout = binding.motionlayoutHeader

        val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }
        appBarLayout.addOnOffsetChangedListener(listener)
        motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })
    }

}