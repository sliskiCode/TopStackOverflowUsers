package com.slesarew.topstackoverflowusers.userlist.model.connection

import io.reactivex.Observable

interface NetworkMonitor {

    val networkState: Observable<NetworkState>
}