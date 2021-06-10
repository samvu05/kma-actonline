package com.sam.actonline.view.course

import android.view.View
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.actonline.R
import com.sam.actonline.base.BaseFragment
import com.sam.actonline.databinding.FragmentCourseContentBinding
import com.sam.actonline.extention.startBrowser
import com.sam.actonline.model.coursedetail.ItemSection
import com.sam.actonline.model.coursedetail.Module
import com.sam.actonline.utils.AlertHelper
import com.sam.actonline.utils.FileManager
import com.sam.actonline.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Dinh Sam Vu on 6/9/2021.
 */
@AndroidEntryPoint
class CourseContentFragment : BaseFragment<FragmentCourseContentBinding>() {
    private var courseID: Int = -1
    private var coursename = "Hello"
    private lateinit var fileManager: FileManager

    private val model: CourseVM by viewModels()

    private val mAdapter by lazy {
        SectionAdapter(
            mutableListOf(),
            onModuleClick = onModuleClick
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(courseID: Int) = CourseContentFragment().apply {
            this.courseID = courseID
        }
    }

    override fun initView(view: View) {
        setupRcc()
        obsever()
        if (courseID != -1) {
            model.getCourseDetail(courseID)
        }
        fileManager = FileManager(requireActivity(), coursename) {
            setCourseContentsOnAdapter()
        }
        fileManager.registerDownloadReceiver()
    }


    private fun obsever() {
        model.courseContent.observe(this@CourseContentFragment, {
            mAdapter.setData(it as MutableList<ItemSection>)
        })
    }

    private fun setupRcc() {
        binding.rcc.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
        }
    }

    @MainThread
    private fun setCourseContentsOnAdapter() {
        fileManager.reloadFileList()
    }

    private val onModuleClick: (module: Module) -> Unit = {
        val module = it as Module
//        val activity = this@CourseDetailActivity
        val content = if (!module.contents.isNullOrEmpty()) module.contents.first() else null
        when (module.getModuleType()) {
            Module.Type.URL -> if (content != null) {
                val url = content.fileurl
                startBrowser(url)
            }
            Module.Type.FORUM, Module.Type.FOLDER -> {
//                val fragment = if (module.getModuleType() == Module.Type.FORUM)
//                    ForumFragment.newInstance(courseId, module.instance, courseName)
//                else FolderModuleFragment.newInstance(module.instance, courseName)
//                activity.supportFragmentManager
//                    .beginTransaction()
//                    .addToBackStack(null)
//                    .replace(R.id.course_section_enrol_container, fragment, "Announcements")
//                    .commit()
            }
            Module.Type.LABEL -> {
                if (module.description.isNotBlank()) {
                    AlertHelper.showTipAlert(
                        context = requireContext(),
                        icon = R.drawable.ic_notification,
                        title = "Nội dung nhãn",
                        desc = Utils.getHtmlText(module.description)
                    )
                }
            }
            Module.Type.RESOURCE -> if (content != null) {
                if (fileManager.isModuleContentDownloaded(content)) {
                    fileManager.openModuleContent(content)
                } else {
                    Toast.makeText(
                        getActivity(), "Downloading file - " + content.filename,
                        Toast.LENGTH_SHORT
                    ).show()
                    fileManager.downloadModuleContent(content, module)
                }
            }
            else -> startBrowser(module.url)
        }
    }

    override fun onDestroy() {
        fileManager.unregisterDownloadReceiver()
        super.onDestroy()
    }
}