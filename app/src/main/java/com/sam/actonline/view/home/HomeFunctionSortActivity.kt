package com.sam.actonline.view.home

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.eup.jaemy.ui.setting.adapter.FunctionAdapter
import com.eup.jaemy.ui.setting.event.MyItemTouchHelperCallback
import com.sam.actonline.R
import com.sam.actonline.base.BaseActivity
import com.sam.actonline.databinding.ActivityEditFuncHomeBinding
import com.sam.actonline.extention.getListHomeFunction
import com.sam.actonline.model.Function
import com.sam.actonline.utils.AlertHelper

/**
 * Created by Dinh Sam Vu on 4/6/2021.
 */
class HomeFunctionSortActivity : BaseActivity<ActivityEditFuncHomeBinding>() {
    private var mAdapter: FunctionAdapter? = null
    private var itemTouchHelper: ItemTouchHelper? = null
    private var isEditMode = false
    private var functionListAll: MutableList<Function> = mutableListOf()
    private var sorted = ""

    override fun initView() {
        onClick()
        setupAdapter()
    }

    private fun onClick() {
        binding.apply {
            btnBack.setOnClickListener {
                if (isEditMode) {
                    onEditMode(false)
                } else {
                    finish()
                }
            }
            btnEditFunctions.setOnClickListener {
                if (isEditMode) {
                    onSaveSortedListFunction()
                } else {
                    onEditMode(true)
                }
            }
            btnReset.setOnClickListener {
                onResetSortedList()
            }

            binding.viewFiter.setOnClickListener { }
        }
    }

    private fun onResetSortedList() {
        AlertHelper.showYesNoAlert(
            this@HomeFunctionSortActivity,
            R.drawable.ic_alert,
            getString(R.string.careful),
            getString(R.string.restore_setting_decs),
            onAgreeCallback = {
                sorted = "1234"
//                preferencesHelper.sortedFunctions = sorted
                updateAdapterData()
            },
            onCancelCallback = {
            }
        )
    }

    private fun onEditMode(editMode: Boolean) {
        isEditMode = editMode
        disableRccTouch(isTouch = isEditMode)
        if (isEditMode) {
            binding.btnEditFunctions.text = getString(R.string.save)
            AlertHelper.showTipAlert(
                this@HomeFunctionSortActivity,
                R.drawable.ic_tips,
                getString(R.string.tips),
                getString(R.string.tips_editsetting_decs)
            ) {
            }
        } else {
            binding.btnEditFunctions.text = getString(R.string.edit_function)
        }
        mAdapter?.switchEditMode(isEditMode)
    }

    private fun onSaveSortedListFunction() {
        if (sorted != "") {
//            preferencesHelper.sortedFunctions = sorted
            onEditMode(false)
            AlertHelper.showTipAlert(
                this@HomeFunctionSortActivity,
                R.drawable.ic_done_48,
                getString(R.string.success),
                getString(R.string.saved_edited)
            ) {
            }
        }
    }

    private fun setupAdapter() {
        mAdapter = FunctionAdapter(sortedCallback = sortedCallback)
        val lm = GridLayoutManager(this@HomeFunctionSortActivity, 2)

        binding.rccFunctions.apply {
            layoutManager = lm
            adapter = mAdapter
            setHasFixedSize(true)
        }
        functionListAll = this@HomeFunctionSortActivity.getListHomeFunction()
        updateAdapterData()
        val callback = MyItemTouchHelperCallback(adapter = mAdapter!!)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper!!.attachToRecyclerView(binding.rccFunctions)
    }

    private val sortedCallback = fun(sortedID: String) {
        this@HomeFunctionSortActivity.sorted = sortedID
    }

    private fun updateAdapterData() {
        val sortedFunctionList = mutableListOf<Function>()
//        val sortedFunctionID: List<String> = preferencesHelper.sortedFunctions.split("")
//        for (ID in sortedFunctionID) {
//            if (ID == "") continue
//            val item = functionListAll.first { it.id.toString() == ID }
//            if (!sortedFunctionList.contains(item)) {
//                sortedFunctionList.add(item)
//            }
//        }
        sortedFunctionList.addAll(functionListAll.minus(sortedFunctionList))
        mAdapter?.updateData(sortedFunctionList)
    }

    private fun disableRccTouch(isTouch: Boolean) {
        binding.viewFiter.visibility = if (isTouch) View.GONE else View.VISIBLE
    }
}


