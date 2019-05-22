package com.slesarew.topstackoverflowusers.userlist.viewmodel.state

import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UsersState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class ViewStateReducerTest {

    private val viewState = ViewState()

    private val tested = viewStateReducer

    @Test
    fun `reduces state to data`() {
        val users = listOf(
            UserPresentationModel()
        )
        val actual = tested(
            viewState,
            UsersState.Data(
                users
            )
        )

        assertEquals(users, actual.data)
        assertFalse(
            "State has error, but should not!",
            actual.hasError
        )
        assertTrue(
            "State has no connection, but should!",
            actual.hasConnection
        )
    }

    @Test
    fun `reduces state to error`() {
        val actual = tested(
            viewState,
            UsersState.ServerError
        )

        assertEquals(0, actual.data.size)
        assertTrue(
            "State has no error, but should!",
            actual.hasError
        )
        assertTrue(
            "State has no connection, but should!",
            actual.hasConnection
        )
    }

    @Test
    fun `reduces state to no connection`() {
        val actual = tested(
            viewState,
            UsersState.NoConnection
        )

        assertEquals(0, actual.data.size)
        assertFalse(
            "State has error, but should not!",
            actual.hasError
        )
        assertFalse(
            "State has connection, but should not!",
            actual.hasConnection
        )
    }
}