package com.slesarew.topstackoverflowusers.userlist.viewmodel.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserPresentationModel(
    val id: Long = 0L,
    val image: String = "",
    val name: String = "",
    val reputation: String = "",
    val location: String? = "",
    val creationDate: Long = 0L,
    var isFollowed: Boolean = false
) : Parcelable