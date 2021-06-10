package com.sam.actonline.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.sam.actonline.base.BaseFragment
import com.sam.actonline.databinding.FragmentHomeBinding
import com.sam.actonline.extention.*
import com.sam.actonline.model.Function
import com.sam.actonline.model.event.ItemEvent
import com.sam.actonline.utils.PrefHelper
import com.sam.actonline.utils.enum.HomeFunctionType
import com.sam.actonline.view.eventdetail.EventDetailBSF
import com.sam.actonline.view.home.adapter.HomeFuncAdapter
import com.sam.actonline.view.home.adapter.RccEventAdapter
import com.sam.actonline.view.maps.MapsActivity
import dagger.hilt.android.AndroidEntryPoint
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
    private val eventAdapter: RccEventAdapter by lazy {
        RccEventAdapter(
            mutableListOf(),
            rccEventClick
        )
    }

    override fun initView(view: View) {
        initRccFunction()
        initRccEvent()
        obsever()
        onClick()
        coordinateMotion()
    }

    @SuppressLint("ClickableViewAccessibility")
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
                Glide.with(requireContext()).load(it.userPicture).centerCrop().into(imvAvatar)
            }
        })
        homeVM.listEvent.observe(this@HomeFragment, {
            if (it.isNotEmpty()) {
                eventAdapter.setData(it)
            }
        })
    }

    private fun initRccEvent() {
        binding.rccEvent.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initRccFunction() {
        funcAdapter = HomeFuncAdapter(onClick = rccFunctionClick)
        val lm = GridLayoutManager(requireContext(), 2)
        binding.rcvFuncHome.apply {
            layoutManager = lm
            adapter = funcAdapter
            setHasFixedSize(true)
        }
        setupFuncData()
    }

    private val rccEventClick: (item: ItemEvent) -> Unit = {
        val eventBSF = EventDetailBSF.newInstance(it.id, it.name)
        eventBSF.show(childFragmentManager, eventBSF.tag)
    }

    private val rccFunctionClick: (it: Function) -> Unit = {
        when (it.type) {
            HomeFunctionType.HOME_SITE -> {
                requireActivity().startBrowser("https://rims.fun")
            }
            HomeFunctionType.DIRECTION -> {
                startActivity(Intent(requireContext(), MapsActivity::class.java))
            }
            else -> Unit
        }

    }

    private fun setupFuncData() {
        val listFuncAll = this@HomeFragment.getListHomeFunction()
        val listFuncSorted: MutableList<Function> = mutableListOf()
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

    private fun coordinateMotion() {
        val appBarLayout: AppBarLayout = binding.appbarLayout
        val motionLayout1: MotionLayout = binding.motionlayoutHeader

        val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            motionLayout1.progress = seekPosition
        }
        appBarLayout.addOnOffsetChangedListener(listener)
        motionLayout1.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        setupFuncData()
    }
}
