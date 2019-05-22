package com.slesarew.topstackoverflowusers.preferences

interface UserPreferences {

    fun followedIds(ids: List<Long>): List<Pair<Long, Boolean>>

    fun saveFollowChanged(id: Long, isFollowed: Boolean)
}