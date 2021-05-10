package com.sam.actonline.extention

import android.app.Activity
import androidx.fragment.app.Fragment
import com.sam.actonline.R
import com.sam.actonline.model.ItemFunction
import com.sam.actonline.utils.enum.HomeFunctionType

/**
 * Created by Dinh Sam Vu on 4/8/2021.
 */

fun Activity.getListHomeFunction(): MutableList<ItemFunction> {
    val listFunction = mutableListOf<ItemFunction>()
    listFunction.apply {
        add(
            ItemFunction(
                1,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            ItemFunction(
                2,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            ItemFunction(
                3,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            ItemFunction(
                4,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )

        add(
            ItemFunction(
                5,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            ItemFunction(
                6,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            ItemFunction(
                7,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
        add(
            ItemFunction(
                8,
                HomeFunctionType.DIRECTION,
                getString(R.string.maps_direction),
                R.drawable.img_maps_direction
            )
        )
    }

    return listFunction
}

fun Fragment.getListHomeFunction(): MutableList<ItemFunction> {
    return this.requireActivity().getListHomeFunction()
}