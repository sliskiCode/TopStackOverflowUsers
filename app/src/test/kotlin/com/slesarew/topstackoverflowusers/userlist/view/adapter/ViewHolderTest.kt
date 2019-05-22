package com.slesarew.topstackoverflowusers.userlist.view.adapter

import android.view.View
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.slesarew.topstackoverflowusers.databinding.UserItemBinding
import com.slesarew.topstackoverflowusers.userlist.view.router.UserDetailsRouter
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import org.junit.Test

class ViewHolderTest {

    private val userDetailsRouterMock = mock<UserDetailsRouter>()
    private val userItemBindingMock = mock<UserItemBinding> {
        on {
            root
        } doReturn (mock<View>())
    }

    private val tested = ViewHolder(
        userItemBindingMock,
        userDetailsRouterMock
    )

    @Test
    fun `binds user`() {
        val user = UserPresentationModel()

        tested.bind(user)

        verify(userItemBindingMock).user = user
    }

    @Test
    fun `binds router`() {
        tested.bind(UserPresentationModel())

        verify(userItemBindingMock).router = userDetailsRouterMock
    }

    @Test
    fun `executes pending bindings`() {
        tested.bind(UserPresentationModel())

        verify(userItemBindingMock).executePendingBindings()
    }
}