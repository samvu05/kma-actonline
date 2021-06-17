package com.sam.actonline.view.course

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.actonline.base.BaseFragment
import com.sam.actonline.databinding.FragmentCoursesBinding
import com.sam.actonline.extention.showToast
import com.sam.actonline.model.ItemCourse
import com.sam.actonline.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Dinh Sam Vu on 5/12/2021.
 */

@AndroidEntryPoint
class CoursesFragment : BaseFragment<FragmentCoursesBinding>() {
    private val mAdapter by lazy { CoursesAdapter(mutableListOf(), onCourseClick) }
    private val model: CourseVM by viewModels()

    override fun initView(view: View) {
        setupRcc()
        obsever()
        model.getListCourse()
    }

    private fun setupRcc() {
        binding.rccCourse.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
        }
    }

    private fun obsever() {
        model.listCourse.observe(this@CoursesFragment, {
            mAdapter.setData(it)
        })
    }

    private val onCourseClick = { course: ItemCourse ->
        if (course.id != -1) {
            val intent = Intent(requireActivity(), CourseDetailActivity::class.java).apply {
                putExtra(CourseDetailActivity.INTENT_COURSE_ID_KEY, course.id)
                putExtra(CourseDetailActivity.INTENT_COURSE_NAME, course.fullname)

                var imvLink = ""
                if (course.overviewfiles.isNotEmpty()) {
                    for (file in course.overviewfiles) {
                        if (file.fileurl.contains(".jpg") || file.fileurl.contains(".png")) {
                            imvLink = file.fileurl + Constant.ADD_LINK_1
                            break
                        }
                    }
                }
                putExtra(CourseDetailActivity.INTENT_COURSE_IMAGE, imvLink)
                putExtra(CourseDetailActivity.INTENT_COURSE_DESCRIPTION, course.summary)
            }
            startActivity(intent)
        } else {
            showToast("Có lỗi xảy ra ngoài ý muốn, vui lòng thử lại !")
        }
    }
}