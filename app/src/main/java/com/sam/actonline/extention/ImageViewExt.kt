package com.sam.actonline.extention

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.sam.actonline.R

/**
 * Created by Dinh Sam Vu on 5/22/2021.
 */

fun ImageView.setImageFromUrl(link: String?) {
    if (link == null || link == "") {
        Glide.with(this)
            .load(R.drawable.img_placeholder_avatar)
            .into(this)
    } else {

        val url = GlideUrl(link)

        Glide.with(this)
            .load(url)
            .centerCrop()
            .error(R.drawable.img_placeholder_view)
            .dontAnimate()
            .into(this)
    }
}


fun ImageView.setAvatarFromUrl(link: String?) {
    if (link == null || link == "") {
        Glide.with(this)
            .load(R.drawable.img_placeholder_avatar)
            .into(this)
    } else {
        Glide.with(this)
            .load(link)
            .centerCrop()
            .error(R.drawable.img_placeholder_avatar)
            .dontAnimate()
            .into(this)
    }
}