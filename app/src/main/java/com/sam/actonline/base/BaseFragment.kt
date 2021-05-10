package com.sam.actonline.base

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sam.actonline.extention.getBinding
import com.sam.actonline.extention.showToast
import com.sam.actonline.utils.PrefHelper

/**
 * Created by Dinh Sam Vu on 4/3/2021.
 */

@Suppress("DEPRECATION")
abstract class BaseFragment<V : ViewBinding> : Fragment() {
    var _binding: V? = null

    val binding: V
        get() = _binding
            ?: throw RuntimeException("Chỉ nên sử dụng ràng buộc sau onCreateView và trước onDestroyView")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        "".isEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    abstract fun initView(view: View)

    private fun hasPermissions(
        activityContext: Context?,
        vararg permissions: String
    ): Boolean {
        if (activityContext != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        activityContext,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        } else {
            showToast("Context null")
            return false
        }
        return true
    }

    fun checkAndRequestPermission(
        activityContext: Context,
        reqCode: Int,
        callback: () -> Unit,
        vararg arrPermission: String
    ) {
        if (activityContext is Activity) {
            if (!hasPermissions(activityContext, *arrPermission)) {
                ActivityCompat.requestPermissions(
                    activityContext,
                    arrPermission,
                    reqCode
                )
            } else {
                callback()
            }
        } else {
            showToast("Error null context permission ! ")
        }
    }

}