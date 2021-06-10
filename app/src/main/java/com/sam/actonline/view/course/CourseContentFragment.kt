package com.sam.actonline.view.course

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.actonline.base.BaseFragment
import com.sam.actonline.databinding.FragmentCourseContentBinding
import com.sam.actonline.extention.showToast
import com.sam.actonline.extention.startBrowser
import com.sam.actonline.extention.toDownloadLink
import com.sam.actonline.model.coursedetail.ItemSection
import com.sam.actonline.model.coursedetail.Module
import com.sam.actonline.utils.AlertHelper
import com.sam.actonline.utils.FileHelper
import com.sam.actonline.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Dinh Sam Vu on 6/9/2021.
 */
@AndroidEntryPoint
class CourseContentFragment : BaseFragment<FragmentCourseContentBinding>() {
    private var courseID: Int = -1
    private var courseName = ""

    private val model: CourseVM by viewModels()
    private lateinit var fileHelper: FileHelper
    private lateinit var mAdapter: SectionAdapter

    companion object {
        @JvmStatic
        fun newInstance(courseID: Int, courseName: String) = CourseContentFragment().apply {
            this.courseID = courseID
            this.courseName = courseName
        }
    }

    override fun initView(view: View) {
        fileHelper = FileHelper(requireActivity(), courseName) {
            onDownloadFinish(it)
        }
        mAdapter = SectionAdapter(mutableListOf(), onModuleClick = onModuleClick, fileHelper)
        fileHelper.registerDownloadReceiver()

        setupRcc()
        obsever()

        if (courseID != -1) {
            model.getCourseDetail(courseID)
        }
    }

    private fun obsever() {
        model.courseContent.observe(this@CourseContentFragment, {
            mAdapter.setData(it as MutableList<ItemSection>)
        })
    }

    private fun setupRcc() {
        binding.rccSection.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
        }
    }

    private val onDownloadFinish: (filename: String) -> Unit = {
        fileHelper.reloadFileList()
        mAdapter.reload()
    }

    private val onModuleClick: (module: Module) -> Unit = {
        val module = it
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
                if (!module.description.isNullOrBlank()) {
                    AlertHelper.showTipAlert(
                        context = requireContext(),
                        title = "Nội dung LABEL",
                        desc = Utils.getHtmlText(module.description)
                    )
                }
            }
            Module.Type.RESOURCE -> if (content != null) {
                if (fileHelper.isModuleContentDownloaded(content)) {
                    fileHelper.openModuleContent(content)
                } else {
                    showToast("Đang tải xuống File - " + content.filename)
                    fileHelper.downloadModuleContent(content, module)
                }
            }
            else -> startBrowser(module.url.toDownloadLink())
        }
    }

    override fun onDestroy() {
        fileHelper.unregisterDownloadReceiver()
        super.onDestroy()
    }
}