package com.sam.actonline.model

/**
 * Created by Dinh Sam Vu on 6/13/2021.
 */


data class BaseBadge(
    val badges: List<ItemBadge>
)

data class ItemBadge(
    val id: Int?,
    val name: String?,
    val description: String?,
    val timecreated: Int?,
    val usercreated: Int?,
    val issuername: String?,
    val issuerurl: String?,
    val expiredate: Int?,
    val courseid: Int?,
    val badgeurl: String?
)