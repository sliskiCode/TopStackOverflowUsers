package com.slesarew.topstackoverflowusers.userlist.model.users

import androidx.lifecycle.LiveData
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState

interface UsersProvider {

    val usersState: LiveData<UsersState>

    fun dispose()
}