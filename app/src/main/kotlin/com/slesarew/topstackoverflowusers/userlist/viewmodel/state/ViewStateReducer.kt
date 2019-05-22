package com.slesarew.topstackoverflowusers.userlist.viewmodel.state

import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState

typealias ViewStateReducer = (ViewState, UsersState) -> ViewState

val viewStateReducer: ViewStateReducer = { input, usersState ->
    ViewState()
        .apply {
            when (usersState) {
                is UsersState.Data -> {
                    data = usersState.users
                    hasError = false
                    hasConnection = true
                }
                is UsersState.ServerError -> {
                    data = emptyList()
                    hasError = true
                    hasConnection = input.hasConnection
                }
                is UsersState.NoConnection -> {
                    data = emptyList()
                    hasError = input.hasError
                    hasConnection = false
                }
            }
        }
}