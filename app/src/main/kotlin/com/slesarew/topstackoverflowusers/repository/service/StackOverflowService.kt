package com.slesarew.topstackoverflowusers.repository.service

import com.slesarew.topstackoverflowusers.repository.model.User
import com.slesarew.topstackoverflowusers.repository.model.WrappedUser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowService {

    @GET("users?order=desc&sort=reputation&site=stackoverflow")
    fun getUsers(@Query("pagesize") limit: Int): Single<WrappedUser<User>>

    companion object {

        const val BASE_URL = "https://api.stackexchange.com/2.2/"
    }
}