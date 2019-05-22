package com.slesarew.topstackoverflowusers.preferences

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.slesarew.topstackoverflowusers.App

class SharedUserPreferences(
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
        App.baseContext
    )
) : UserPreferences {

    override fun followedIds(ids: List<Long>): List<Pair<Long, Boolean>> = ids
        .map { it to sharedPreferences.getBoolean(it.toString(), false) }
        .toList()

    override fun saveFollowChanged(id: Long, isFollowed: Boolean) = sharedPreferences
        .edit()
        .putBoolean(id.toString(), isFollowed)
        .apply()
}