package com.slesarew.topstackoverflowusers.userlist.view.router

import androidx.appcompat.app.AppCompatActivity
import org.junit.Assert.assertTrue
import org.junit.Test

class UserDetailsRouterFactoryTest {

    @Test
    fun `produces standalone router`() {
        val actual = UserDetailsRouterFactory.create(
            AppCompatActivity(),
            isTablet = false
        )

        assertTrue(
            "Factory does not produce standalone user details router, but it should!",
            actual is ActivityUserDetailsRouter
        )
    }

    @Test
    fun `produces embedded router`() {
        val actual = UserDetailsRouterFactory.create(
            AppCompatActivity(),
            isTablet = true
        )

        assertTrue(
            "Factory does not produce embedded user details router, but it should!",
            actual is FragmentUserDetailsRouter
        )
    }
}