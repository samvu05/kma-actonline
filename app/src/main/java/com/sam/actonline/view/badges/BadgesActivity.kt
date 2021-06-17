package com.sam.actonline.view.badges

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.actonline.base.BaseActivity
import com.sam.actonline.databinding.ActivityBadgesBinding
import com.sam.actonline.extention.setOnCustomClick
import com.sam.actonline.model.ItemBadge
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Dinh Sam Vu on 6/12/2021.
 */

@AndroidEntryPoint
class BadgesActivity : BaseActivity<ActivityBadgesBinding>() {
    private val model: BadgesVM by viewModels()
    private lateinit var mAdapter: BadgeAdapter

    override fun initView() {
        mAdapter = BadgeAdapter(mutableListOf(), onClick = onBadgeClick)
        setupRcc()
        obsever()

        binding.btnBack.setOnCustomClick {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun obsever() {
        model.getListBadges()
        model.listBadges.observe(this@BadgesActivity, {
            mAdapter.setData(it as MutableList<ItemBadge>)
        })
    }

    private fun setupRcc() {
        binding.rccMedal.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@BadgesActivity)
        }
    }

    private val onBadgeClick: (badge: ItemBadge) -> Unit = {
    }
}
