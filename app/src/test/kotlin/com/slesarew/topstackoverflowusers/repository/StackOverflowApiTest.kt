package com.slesarew.topstackoverflowusers.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.slesarew.topstackoverflowusers.repository.model.User
import com.slesarew.topstackoverflowusers.repository.model.WrappedUser
import com.slesarew.topstackoverflowusers.repository.service.StackOverflowService
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import kotlin.test.assertEquals

const val LIMIT = 5

class StackOverflowApiTest {

    private val users = listOf(
        User(),
        User(),
        User(),
        User(),
        User()
    )

    private val serviceMock = mock<StackOverflowService> {
        on { getUsers(LIMIT) } doReturn Single.just(WrappedUser(users))
    }

    private val tested = StackOverflowApi(serviceMock, TestScheduler())

    @Test
    fun `return users stream`() {
        tested.getUsers(LIMIT)
            .subscribe { users ->
                assertEquals(this.users, users)
            }
    }
}