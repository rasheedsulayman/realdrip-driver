package com.treplabs.android.realdripdriver.realdripdriverapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.treplabs.android.realdripdriver.R
import timber.log.Timber

@BindingAdapter("imageUrl")
fun ImageView.bindNurseAvatar(url: String?) {
    Timber.d("Image url is $url")
    load(R.drawable.nurse_avatar) {
        //This is hardCoded. Todo load the actual url from the api
        transformations(CircleCropTransformation())
        crossfade(true)
    }
}