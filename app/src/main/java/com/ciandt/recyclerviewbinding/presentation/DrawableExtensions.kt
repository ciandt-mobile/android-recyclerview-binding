package com.ciandt.recyclerviewbinding.presentation

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable

fun Drawable.startAnimation() {
    (this as? AnimationDrawable)?.start()
}