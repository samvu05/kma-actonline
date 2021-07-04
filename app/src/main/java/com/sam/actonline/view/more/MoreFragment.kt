package com.sam.actonline.view.more

import android.content.Intent
import android.net.Uri
import android.view.View
import com.sam.actonline.base.BaseFragment
import com.sam.actonline.databinding.FragmentMoreBinding
import com.sam.actonline.extention.setOnCustomClickAnimPerson
import com.sam.actonline.extention.startBrowser
import com.sam.actonline.utils.PrefHelper
import com.sam.actonline.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>() {
    @Inject
    lateinit var prefHelper: PrefHelper

    override fun initView(view: View) {
        clickEvent()
    }

    private fun clickEvent() {
        binding.run {
            wraperLogout.setOnCustomClickAnimPerson(layoutLogout) {
                logout()
            }
            wraperExit.setOnCustomClickAnimPerson(layoutExit) {
                requireActivity().finish()
                onDetach()
            }
            wraperWebsite.setOnCustomClickAnimPerson(layoutWebsite) {
                requireActivity().startBrowser("https://rims.fun")
            }
            wraperFeedback.setOnCustomClickAnimPerson(layoutFeedback) {
                Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:samvu.it@gmail.com") // only email apps should handle this
                    putExtra(Intent.EXTRA_SUBJECT, "Góp ý về ứng dụng KMA-Elearning")
                    if (requireActivity().packageManager != null) {
                        startActivity(this)
                    }
                }
            }
        }
    }

    private fun logout() {
        prefHelper.logout()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        requireActivity().finish()
    }

}