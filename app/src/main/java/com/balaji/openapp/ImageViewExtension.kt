package com.balaji.openapp

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.roundedImageView(drawable: Drawable?, radius: Int = 8) {
    Glide.with(context!!).load(drawable)
        .transform(CenterCrop(), RoundedCorners(radius))
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
        .into(this)
}

fun ImageView.roundedImageView(image: Int?, radius: Int = 8) {
    Glide.with(context!!).load(ContextCompat.getDrawable(this.context, image!!))
        .transform(CenterCrop(), RoundedCorners(radius))
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
        .into(this)
}

fun ImageView.roundedImageView(url: String?, radius: Int = 8) {
    Glide.with(context!!).load(if (url.isNullOrBlank()) "error" else url)
        .transform(CenterCrop(), RoundedCorners(radius))
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
        .into(this)
}