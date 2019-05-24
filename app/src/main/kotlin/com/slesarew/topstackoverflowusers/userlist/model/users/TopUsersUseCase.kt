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
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

class TopUsersUseCase(
    userRepository: UserRepository = StackOverflowApi(),
    limitOfUsers: Int = 20,
    private val networkMonitor: NetworkMonitor = SystemNetworkMonitor(),
    private val userPreferences: UserPreferences = SharedUserPreferences()
) : UsersProvider {

    private val state = MutableLiveData<UsersState>()

    private var disposable: Disposable = Disposables.disposed()

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
                        isFollowed = user.isFollowed(followedIds)
                    )
                }) as UsersState
            }
            .onErrorReturnItem(ServerError)
    }

    private fun User.isFollowed(
        followedIds: List<Pair<Long, Boolean>>
    ) = followedIds.contains(id to true)

    override val usersState: LiveData<UsersState>
        get() {
            if (disposable.isDisposed) {
                disposable = networkMonitor
                    .networkState
                    .flatMapSingle { networkState ->
                        when (networkState) {
                            AVAILABLE -> usersStream
                            UNAVAILABLE -> Single.just(NoConnection)
                        }
                    }
                    .subscribe(state::postValue)
            }

            return state
        }

    override fun dispose() {
        disposable.dispose()
    }
}