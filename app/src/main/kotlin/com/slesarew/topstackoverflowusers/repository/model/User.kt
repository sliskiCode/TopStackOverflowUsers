package com.slesarew.topstackoverflowusers.repository.model

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("account_id") val id: Long = 0L,
    @SerializedName("display_name") val displayName: String = "",
    @SerializedName("profile_image") val profileImage: String = "",
    @SerializedName("reputation") val reputation: String = "",
    @SerializedName("creation_date") val creationDate: Long = 0L,
    @SerializedName("location") val location: String? = ""
)