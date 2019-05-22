package com.slesarew.topstackoverflowusers.userlist.model.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.slesarew.topstackoverflowusers.preferences.SharedUserPreferences
import com.slesarew.topstackoverflowusers.preferences.UserPreferences
import com.slesarew.topstackoverflowusers.repository.StackOverflowApi
import com.slesarew.topstackoverflowusers.repository.model.User
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkMonitor
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkState.AVAILABLE
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkState.UNAVAILABLE
import com.slesarew.topstackoverflowusers.userlist.model.connection.SystemNetworkMonitor
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState.Data
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState.NoConnection
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState.ServerError
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class TopUsersUseCase(
    userRepository: UserRepository = StackOverflowApi(),
    limitOfUsers: Int = 20,
    private val networkMonitor: NetworkMonitor = SystemNetworkMonitor(),
    private val userPreferences: UserPreferences = SharedUserPreferences()
) : UserProvider {

    private val state = MutableLiveData<UsersState>()

    private var disposable: Disposable? = null

    private val usersStream by lazy {
        userRepository
            .getUsers(limitOfUsers)
            .map { users ->
                val followedIds = userPreferences.followedIds(users.map(User::id))

                Data(users.map { user ->
                    UserPresentationModel(
                        id = user.id,
                        image = user.profileImage,
                        name = user.displayName,
                        reputation = user.reputation,
                        location = user.location,
                        creationDate = user.creationDate,
                        isFollowed = isFollowed(followedIds, user)
                    )
                }) as UsersState
            }
            .toObservable()
            .onErrorReturnItem(ServerError)
    }

    private fun isFollowed(
        followedIds: List<Pair<Long, Boolean>>,
        user: User
    ) = followedIds
        .find { it.first == user.id }
        ?.second
        ?: false

    override val getUsersState: LiveData<UsersState>
        get() {
            if (disposable == null) {
                disposable = networkMonitor.networkState
                    .switchMap { networkState ->
                        when (networkState) {
                            AVAILABLE -> usersStream
                            UNAVAILABLE -> Observable.just(NoConnection)
                        }
                    }
                    .subscribe(state::postValue)
            }

            return state
        }

    override fun dispose() {
        disposable?.dispose()
    }
}