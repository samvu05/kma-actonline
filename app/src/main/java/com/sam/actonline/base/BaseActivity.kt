package com.sam.actonline.base

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.sam.actonline.events.OnPermissionsListener
import com.sam.actonline.extention.getBinding
import com.sam.actonline.extention.showToast

/**
 * Created by Dinh Sam Vu on 4/3/2021.
 */

abstract class BaseActivity<V : ViewBinding> : AppCompatActivity() {
    lateinit var binding: V
    private var permissions: OnPermissionsListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding()
        setContentView(binding.root)
        initView()
    }

    abstract fun initView()

    fun setColorStatusBar(color: Int) {
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, color)
        }
    }

    fun setFullStatusBar() {
        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

//    private fun hasPermissions(
//        activityContext: Context?,
//        vararg permissions: String
//    ): Boolean {
//        if (activityContext != null) {
//            for (permission in permissions) {
//                if (ActivityCompat.checkSelfPermission(
//                        activityContext,
//                        permission
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    return false
//                }
//            }
//        } else {
//            showToast("Context null")
//            return false
//        }
//        return true
//    }
//
//    fun checkAndRequestPermission(
//        activityContext: Context,
//        reqCode: Int,
//        callback: () -> Unit,
//        vararg arrPermission: String
//    ) {
//        if (activityContext is Activity) {
//            if (!hasPermissions(activityContext, *arrPermission)) {
//                ActivityCompat.requestPermissions(
//                    activityContext,
//                    arrPermission,
//                    reqCode
//                )
//            } else {
//                callback()
//            }
//        } else {
//            showToast("Error null context permission ! ")
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        this.permissions?.onPermissions(requestCode, permissions, grantResults)
//    }
//
//    fun setOnPermissionsListener(permissions: OnPermissionsListener?) {
//        this.permissions = permissions
//    }

}