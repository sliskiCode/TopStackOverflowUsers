package com.slesarew.topstackoverflowusers.extension

import android.content.Context

inline fun <reified T> Context.systemService(flag: String): T = getSystemService(flag) as T