package com.sam.actonline.model

import com.sam.actonline.utils.enum.HomeFunctionType

/**
 * Created by Dinh Sam Vu on 4/14/2021.
 */
data class Function(
    val id: Int,
    var type: HomeFunctionType,
    var title: String,
    var resImg: Int
)