package com.slesarew.topstackoverflowusers.extension

import retrofit2.Retrofit

inline fun <reified T> Retrofit.create(): T = create(T::class.java)