package com.slesarew.topstackoverflowusers.userlist.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slesarew.topstackoverflowusers.userlist.model.users.TopUsersUseCase
import com.slesarew.topstackoverflowusers.userlist.model.users.UsersProvider
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState
import com.slesarew.topstackoverflowusers.userlist.viewmodel.state.ViewState
import com.slesarew.topstackoverflowusers.userlist.viewmodel.state.ViewStateReducer
import com.slesarew.topstackoverflowusers.userlist.viewmodel.state.viewStateReducer

class UserListViewModel(
    private val usersProvider: UsersProvider,
    private val stateReducer: ViewStateReducer = viewStateReducer
) : ViewModel() {

    val viewState = ViewState()

    private val observer = Observer<UsersState> {
        viewState.applyChanges(stateReducer(viewState, it))
    }

    fun load() = usersProvider.usersState.observeForever(observer)

    override fun onCleared() {
        usersProvider.usersState.removeObserver(observer)
        usersProvider.dispose()

        super.onCleared()
    }

    companion object {

        fun create(usersProvider: UsersProvider): UserListViewModel =
            UserListViewModel(usersProvider).also(UserListViewModel::load)
    }
}

class UserListViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UserListViewModel.create(TopUsersUseCase()) as T
}