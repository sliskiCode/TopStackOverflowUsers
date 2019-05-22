package com.slesarew.topstackoverflowusers.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.junit.Assert.assertEquals
import org.robolectric.Shadows

fun TextView.hasText(@StringRes resId: Int) {
    assertEquals(context.getString(resId), text)
}

fun TextView.hasText(text: String) {z
    assertEquals(text, this.text)
}

fun View.isVisible() {
    assertEquals(View.VISIBLE, visibility)
}

fun View.isGone() {
    assertEquals(View.GONE, visibility)
}

fun ImageView.hasImage(@DrawableRes resId: Int) {
    val drawableResId = Shadows.shadowOf(drawable).createdFromResId

    assertEquals(resId, drawableResId)
}