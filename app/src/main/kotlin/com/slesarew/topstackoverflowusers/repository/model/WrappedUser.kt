package com.slesarew.topstackoverflowusers.repository.model

import com.google.gson.annotations.SerializedName

data class WrappedUser<out User>(@SerializedName("items") val users: List<User>)