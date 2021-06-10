package com.sam.actonline.model.event

import com.google.gson.annotations.SerializedName

/**
 * Created by Dinh Sam Vu on 5/23/2021.
 */
data class BaseEvent(
    //Used for get Event list (upcoming, monthly..)
    @SerializedName("events")
    val events: List<ItemEvent>,

    //Used for get Event detail by ID
    @SerializedName("event")
    val event: ItemEventDetail
)