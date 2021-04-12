package com.example.actonline.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actonline.R
import com.example.actonline.base.BaseFragment
import com.example.actonline.databinding.FragmentNotificationBinding
import com.example.actonline.model.ItemNotification
import com.example.actonline.view.adapter.RccNotificationAdapter

/**
 * Created by Dinh Sam Vu on 3/3/2021.
 */

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {
    private lateinit var mRccNotification: RecyclerView

    override fun initView(view: View) {
        mRccNotification = view.findViewById(R.id.rcc_notification)
        val list = mutableListOf<ItemNotification>()
        for (i in 1..40) {
            list.add(ItemNotification("Name $i", "", ""))
        }
        val mAdapter = RccNotificationAdapter(list)

        mRccNotification.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
        }
    }
}