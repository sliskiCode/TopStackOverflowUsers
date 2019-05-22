package com.slesarew.topstackoverflowusers.userlist.model.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.slesarew.topstackoverflowusers.repository.model.User
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkMonitor
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkState
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkState.AVAILABLE
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkState.UNAVAILABLE
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState.Data
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState.NoConnection
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState.ServerError
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

const val LIMIT = 20

class TopUsersUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun `emits no connection state`() {
        val networkMonitor = networkMonitor(UNAVAILABLE)
        val tested = TopUsersUseCase(
            networkMonitor = networkMonitor,
            userRepository = userRepository(),
            userPreferences = mock()
        )
        val observer = mock<Observer<UsersState>>()

        tested.usersState.observeForever(observer)

        verify(observer).onChanged(NoConnection)
    }

    @Test
    fun `emits state with data`() {
        val networkMonitor = networkMonitor(AVAILABLE)
        val userRepository = userRepository(
            listOf(
                User(displayName = "Mark"),
                User(displayName = "Alison")
            )
        )
        val tested = TopUsersUseCase(
            networkMonitor = networkMonitor,
            userRepository = userRepository,
            userPreferences = mock()
        )
        val observer = mock<Observer<UsersState>>()

        tested.usersState.observeForever(observer)

        verify(observer).onChanged(
            Data(
                listOf(
                    UserPresentationModel(name = "Mark"),
                    UserPresentationModel(name = "Alison")
                )
            )
        )
    }

    @Test
    fun `emits state with correctly mapped data`() {
        val networkMonitor = networkMonitor(AVAILABLE)
        val userRepository = userRepository(
            listOf(
                User(
                    id = 1L,
                    displayName = "John Black",
                    profileImage = "http://image.com/alison",
                    reputation = "120102012",
                    creationDate = 12030102010L,
                    location = "London, UK"
                )
            )
        )
        val tested = TopUsersUseCase(
            networkMonitor = networkMonitor,
            userRepository = userRepository,
            userPreferences = mock()
        )
        val observer = mock<Observer<UsersState>>()

        tested.usersState.observeForever(observer)

        verify(observer).onChanged(
            Data(
                listOf(
                    UserPresentationModel(
                        id = 1L,
                        image = "http://image.com/alison",
                        name = "John Black",
                        reputation = "120102012",
                        location = "London, UK",
                        creationDate = 12030102010L
                    )
                )
            )
        )
    }

    @Test
    fun `emits server error state`() {
        val networkMonitor = networkMonitor(AVAILABLE)
        val tested = TopUsersUseCase(
            networkMonitor = networkMonitor,
            userRepository = userRepositoryError,
            userPreferences = mock()
        )
        val observer = mock<Observer<UsersState>>()

        tested.usersState.observeForever(observer)

        verify(observer).onChanged(ServerError)
    }

    private fun networkMonitor(networkState: NetworkState) = mock<NetworkMonitor> {
        on { this.networkState } doReturn Observable.just(networkState)
    }

    private fun userRepository(users: List<User> = emptyList()) = mock<UserRepository> {
        on { getUsers(LIMIT) } doReturn Single.just(users)
    }

    private val userRepositoryError = mock<UserRepository> {
        on { getUsers(LIMIT) } doReturn Single.error(TimeoutException())
    }
}