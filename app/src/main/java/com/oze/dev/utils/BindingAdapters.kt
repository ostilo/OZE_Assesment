package com.oze.dev.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.oze.dev.MainApplication
import com.oze.dev.R

object BindingAdapters : BaseObservable() {
    @BindingAdapter("loadGroupImage")
    @JvmStatic
    fun loadGroupImage(imageView: AppCompatImageView, url: String?) {
        if (!url.isNullOrEmpty()) {

            val circleProgress = CircularProgressDrawable(imageView.context)
            circleProgress.strokeWidth = 5f
            circleProgress.centerRadius = 40f
            circleProgress.start()

            MainApplication.instance?.applicationContext?.let {
                Glide
                    .with(it)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(imageView);
            }
//            imageView.load(url){
//                crossfade(true)
//                placeholder(circleProgress)
//                error(R.drawable.loading)
//            }
        }
    }


    @BindingAdapter("loadShortString")
    @JvmStatic
    fun loadShortString(txt: AppCompatTextView, name: String?) {
        txt.text = name?.shortStringLength(15)?.convertStringToUpperCase()
    }

    @BindingAdapter("loadLongString")
    @JvmStatic
    fun loadLongString(txt: AppCompatTextView, name: String?) {
        txt.text = name?.shortStringLength(30)?.convertStringToUpperCase()
    }

}