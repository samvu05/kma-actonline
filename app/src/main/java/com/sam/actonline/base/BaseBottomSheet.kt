package com.sam.actonline.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sam.actonline.extention.getBinding

/**
 * Created by Dinh Sam Vu on 5/25/2021.
 */
abstract class BaseBottomSheet<V : ViewBinding> : BottomSheetDialogFragment() {
    private var _binding: V? = null

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    fun isSafe(): Boolean {
        return !(this.isRemoving || this.activity == null || this.isDetached || !this.isAdded || this.view == null)
    }

    abstract fun initView(view: View)

}