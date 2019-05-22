package com.slesarew.topstackoverflowusers.userlist.model.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.NetworkRequest
import com.slesarew.topstackoverflowusers.App
import com.slesarew.topstackoverflowusers.extension.systemService
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkState.AVAILABLE
import com.slesarew.topstackoverflowusers.userlist.model.connection.NetworkState.UNAVAILABLE
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

private val cellularWifiNetworkRequest = NetworkRequest.Builder()
    .addTransportType(TRANSPORT_CELLULAR)
    .addTransportType(TRANSPORT_WIFI)
    .build()

class SystemNetworkMonitor(
    private val state: PublishSubject<NetworkState> = PublishSubject.create(),
    private val connectivityManager: ConnectivityManager = App.baseContext.systemService(Context.CONNECTIVITY_SERVICE),
    private val networkRequest: NetworkRequest = cellularWifiNetworkRequest
) : NetworkCallback(), NetworkMonitor {

    override val networkState: Observable<NetworkState> = state
        .doOnSubscribe {
            connectivityManager.registerNetworkCallback(networkRequest, this)
        }
        .doOnDispose {
            connectivityManager.unregisterNetworkCallback(this)
        }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)

        state.onNext(AVAILABLE)
    }

    override fun onUnavailable() {
        super.onUnavailable()

        state.onNext(UNAVAILABLE)
    }

    override fun onLost(network: Network) {
        super.onLost(network)

        state.onNext(UNAVAILABLE)
    }
}

enum class NetworkState {
    AVAILABLE,
    UNAVAILABLE
}