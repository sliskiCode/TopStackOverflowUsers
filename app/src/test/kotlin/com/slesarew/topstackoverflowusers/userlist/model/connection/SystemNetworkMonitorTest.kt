package com.slesarew.topstackoverflowusers.userlist.model.connection

import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkState.AVAILABLE
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkState.UNAVAILABLE
import io.reactivex.subjects.BehaviorSubject
import org.junit.Test

class SystemNetworkMonitorTest {

    private val connectivityManagerMock = mock<ConnectivityManager>()
    private val networkRequest = mock<NetworkRequest>()

    private val state = BehaviorSubject.create<NetworkState>()
    private val tested = SystemNetworkMonitor(
        state = state,
        connectivityManager = connectivityManagerMock,
        networkRequest = networkRequest
    )

    @Test
    fun `registers request when network state subscribed`() {
        tested.networkState.test()

        verify(connectivityManagerMock).registerNetworkCallback(networkRequest, tested)
    }

    @Test
    fun `unregisters request when network state disposed`() {
        tested.networkState.test().dispose()

        verify(connectivityManagerMock).unregisterNetworkCallback(tested)
    }

    @Test
    fun `emits network state available`() {
        val testObserver = tested.networkState.test()

        tested.onAvailable(mock())

        testObserver.assertValue(AVAILABLE)
    }

    @Test
    fun `emits network state unavailable when network lost`() {
        val testObserver = tested.networkState.test()

        tested.onLost(mock())

        testObserver.assertValue(UNAVAILABLE)
    }

    @Test
    fun `emits network state unavailable when network unavailable`() {
        val testObserver = tested.networkState.test()

        tested.onUnavailable()

        testObserver.assertValue(UNAVAILABLE)
    }
}