package com.slesarew.topstackoverflowusers.extension

import android.os.Bundle
import android.os.Parcelable

inline fun <reified T> Bundle.putValue(key: String, value: T): Bundle = apply {
    when (value) {
        is Parcelable -> putParcelable(key, value)
        else -> throw UnsupportedOperationException("Value of type ${T::class.java} is not supported")
    }
}