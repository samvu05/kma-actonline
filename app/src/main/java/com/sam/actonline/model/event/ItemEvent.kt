package com.sam.actonline.model.event

/**
 * Created by Dinh Sam Vu on 5/22/2021.
 */

data class ItemEvent(
    val id: Int,
    val name: String?,
    val location: String?,
    val eventtype: String?,
    val timestart: Int?,
    val formattedtime: String?, // "<a href=\"https://rims.fun/calendar/view.php?view=day&amp;time=1621911600\">Tuesday, 25 May</a>, 10:00 AM",
    val course: EventCourse?
)