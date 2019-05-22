package com.slesarew.topstackoverflowusers.repository

import com.slesarew.topstackoverflowusers.extension.create
import com.slesarew.topstackoverflowusers.repository.model.User
import com.slesarew.topstackoverflowusers.repository.model.WrappedUser
import com.slesarew.topstackoverflowusers.repository.service.StackOverflowService
import com.slesarew.topstackoverflowusers.userlist.model.users.UserRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceInjector {

    val service: StackOverflowService by lazy {
        Retrofit.Builder()
            .baseUrl(StackOverflowService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<StackOverflowService>()
    }
}

class StackOverflowApi(
    private val service: StackOverflowService = ServiceInjector.service,
    private val backgroundScheduler: Scheduler = Schedulers.io()
) : UserRepository {

    /**
     * This function was designed to return a [Single] stream that subscribes on background thread by default.
     * To use emitted value on Main Thread, please change scheduler using [Single.observeOn] function.
     */
    override fun getUsers(limit: Int): Single<List<User>> = service
        .getUsers(limit)
        .map(WrappedUser<User>::users)
        .subscribeOn(backgroundScheduler)
}