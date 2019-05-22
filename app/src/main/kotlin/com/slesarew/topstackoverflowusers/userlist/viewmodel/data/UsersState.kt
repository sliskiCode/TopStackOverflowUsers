package com.slesarew.topstackoverflowusers.userlist.viewmodel.data

sealed class UsersState {

    data class Data(val users: List<UserPresentationModel>) : UsersState()

    object ServerError : UsersState()

    object NoConnection : UsersState()
}