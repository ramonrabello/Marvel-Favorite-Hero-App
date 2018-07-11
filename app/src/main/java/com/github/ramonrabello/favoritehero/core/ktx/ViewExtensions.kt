package com.github.ramonrabello.favoritehero.core.ktx

import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.ramonrabello.favoritehero.R

fun TextView.toTypeface(fontName: String) {
    val customTypeface = Typeface.createFromAsset(this.context.resources.assets, "fonts/$fontName.ttf")
    typeface = customTypeface
}

fun ImageView.load(url: String) {
    Glide.with(context).load(url)
            .apply(RequestOptions()
                    .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.darkerGray)))
                    .centerCrop())
            .transition(DrawableTransitionOptions.withCrossFade(300))
            .into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(@LayoutRes layoutResId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(layoutResId, this, attachToRoot)
}


fun ImageView.tint(@DrawableRes resId: Int, @ColorInt tint: Int) {
    //val wrappedDrawable = Drawable.
}