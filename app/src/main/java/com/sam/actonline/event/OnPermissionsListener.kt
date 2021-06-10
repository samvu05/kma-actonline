package com.sam.actonline.event

/**
 * Created by Dinh Sam Vu on 4/8/2021.
 */

interface OnPermissionsListener {
    fun onPermissions(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
}