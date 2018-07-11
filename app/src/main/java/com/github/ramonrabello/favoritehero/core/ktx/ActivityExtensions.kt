package com.github.ramonrabello.favoritehero.core.ktx

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
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

fun <T : ViewModel> FragmentActivity.obtainViewModel(viewModelFactory:ViewModelProvider.Factory, viewModelClass: Class<T>) =
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)