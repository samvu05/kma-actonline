package com.sam.actonline.view.notification

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.R
import com.sam.actonline.base.BaseFragment
import com.sam.actonline.databinding.FragmentNotificationBinding
import com.sam.actonline.model.ItemNotification
import com.sam.actonline.view.adapter.RccNotificationAdapter

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