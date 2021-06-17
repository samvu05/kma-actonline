package com.sam.actonline.extention

import android.app.Activity
import androidx.fragment.app.Fragment
import com.sam.actonline.R
import com.sam.actonline.model.Function
import com.sam.actonline.utils.enum.HomeFunctionType

/**
 * Created by Dinh Sam Vu on 4/8/2021.
 */

fun Activity.getListHomeFunction(): MutableList<Function> {
    val listFunction = mutableListOf<Function>()
    listFunction.apply {
        add(
            Function(
                1,
                HomeFunctionType.DIRECTION,
                getString(R.string.item_maps),
                R.drawable.ic_maps4
            )
        )
        add(
            Function(
                2,
                HomeFunctionType.DOWNLOADED,
                "Tệp ngoại tuyến",
                R.drawable.ic_folder
            )
        )
        add(
            Function(
                3,
                HomeFunctionType.BADGES,
                "Huy hiệu của tôi",
                R.drawable.ic_medal5
            )
        )
        add(
            Function(
                4,
                HomeFunctionType.HOME_SITE,
                "Bảng tin",
                R.drawable.ic_assignment
            )
        )
    }
    return listFunction
}

fun Fragment.getListHomeFunction(): MutableList<Function> {
    return this.requireActivity().getListHomeFunction()
}