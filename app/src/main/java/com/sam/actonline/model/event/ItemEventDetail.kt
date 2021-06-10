package com.sam.actonline.model.event

/**
 * Created by Dinh Sam Vu on 5/25/2021.
 */

data class ItemEventDetail(
    val id: Int,
    val name: String?,
    val description: String?,
    val location: String?,
    val eventtype: String?,
    val timestart: Int?,
    val formattedtime: String?,
    val timeusermidnight: Int?,
    val viewurl: String?,
    val course: EventCourse?
)