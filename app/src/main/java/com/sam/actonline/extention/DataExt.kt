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
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            Function(
                2,
                HomeFunctionType.HOME_SITE,
                "Vào trang WEB",
                R.drawable.img_web
            )
        )
        add(
            Function(
                3,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            Function(
                4,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )

        add(
            Function(
                5,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            Function(
                6,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            Function(
                7,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            Function(
                8,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
    }

    return listFunction
}

fun Fragment.getListHomeFunction(): MutableList<Function> {
    return this.requireActivity().getListHomeFunction()
}