package com.sam.actonline.view.newfeeds

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.actonline.base.BaseActivity
import com.sam.actonline.databinding.ActivityNewfeedsBinding
import com.sam.actonline.extention.setOnCustomClick
import com.sam.actonline.extention.showToast
import com.sam.actonline.extention.startBrowser
import com.sam.actonline.extention.toDownloadLink
import com.sam.actonline.model.coursedetail.Module
import com.sam.actonline.utils.AlertHelper
import com.sam.actonline.utils.FileHelper
import com.sam.actonline.utils.Utils
import com.sam.actonline.view.course.ModuleAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Dinh Sam Vu on 6/12/2021.
 */

@AndroidEntryPoint
class NewsFeedActivity : BaseActivity<ActivityNewfeedsBinding>() {
    private val courseID: Int = 1
    private val courseName = "Tệp trang chủ"

    private val model: NewsFeedVM by viewModels()
    private lateinit var fileHelper: FileHelper
    private lateinit var mAdapter: ModuleAdapter

    override fun initView() {
        fileHelper = FileHelper(this@NewsFeedActivity, courseName) {
            onDownloadFinish(it)
        }
        mAdapter = ModuleAdapter(mutableListOf(), onModuleClick = onModuleClick, fileHelper)
        fileHelper.registerDownloadReceiver()
        setupRcc()
        obsever()
        model.getCourseDetail(courseID)

        binding.btnBack.setOnCustomClick {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun obsever() {
        model.listModule.observe(this@NewsFeedActivity, {
            mAdapter.setData(it as MutableList<Module>)
        })
    }

    private fun setupRcc() {
        binding.rccModule.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@NewsFeedActivity)
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
                        context = this@NewsFeedActivity,
                        title = "Nội dung nhãn",
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
