package com.sam.actonline.view.home

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.sam.actonline.base.BaseFragment
import com.sam.actonline.databinding.FragmentHomeBinding
import com.sam.actonline.extention.getListHomeFunction
import com.sam.actonline.model.ItemFunction
import com.sam.actonline.utils.PrefHelper
import com.sam.actonline.view.adapter.HomeFuncAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_func_home.*
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 3/3/2021.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    @Inject
    lateinit var prefHelper: PrefHelper
    private var funcAdapter: HomeFuncAdapter? = null
    private val homeVM: HomeVM by viewModels()

    override fun initView(view: View) {
        setupFuncAdapter()
        onClick()
        obsever()

    }

    private fun onClick() {
        binding.apply {
            btnFuncEdit.setOnClickListener {
                startActivity(Intent(requireContext(), HomeFunctionSortActivity::class.java))
            }
        }
    }

    private fun obsever() {
        homeVM.site.observe(this@HomeFragment, {
            binding.apply {
                tvFullname.text = it.fullName
                Glide.with(requireContext()).load(it.userPicture).centerCrop().into(imvAvatar);
            }
        })
    }

    private fun setupFuncAdapter() {
        funcAdapter = HomeFuncAdapter(onClick = {

        })
        val lm = GridLayoutManager(requireContext(), 2)
        binding.rcvFuncHome.apply {
            layoutManager = lm
            adapter = funcAdapter
            setHasFixedSize(true)
        }
        setupAdapterData()
    }

    private fun setupAdapterData() {
        val listFuncAll = this@HomeFragment.getListHomeFunction()
        val listFuncSorted: MutableList<ItemFunction> = mutableListOf()
        val listIDSorted: List<String> = prefHelper.sortedFunctions.split("")
        for (ID in listIDSorted) {
            if (ID == "") continue
            val item = listFuncAll.firstOrNull { it.id.toString() == ID }
            if (item != null) {
                if (!listFuncSorted.contains(item)) {
                    listFuncSorted.add(item)
                }
            }
        }
        if (listFuncSorted.size < 4) {
            for (i in 0..(4 - listIDSorted.size)) {
                listFuncSorted.add(listFuncAll.minus(listFuncSorted).first())
            }
        }
        funcAdapter?.updateData(listFuncSorted)
    }

    override fun onResume() {
        super.onResume()
        setupAdapterData()
    }


}