package com.example.actonline.base

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

@Suppress("DEPRECATION")
open class BaseFragment : Fragment() {
    protected lateinit var mContext: FragmentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity as FragmentActivity
    }
}