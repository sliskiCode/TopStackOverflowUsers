package com.slesarew.topstackoverflowusers.userlist.view.router

import androidx.appcompat.app.AppCompatActivity
import com.slesarew.topstackoverflowusers.R
import com.slesarew.topstackoverflowusers.userdetails.view.UserItemDetailActivity
import com.slesarew.topstackoverflowusers.userdetails.view.UserItemDetailFragment
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel

object UserDetailsRouterFactory {

    fun create(root: AppCompatActivity, isTablet: Boolean): UserDetailsRouter = if (isTablet) {
        FragmentUserDetailsRouter(root)
    } else {
        ActivityUserDetailsRouter(root)
    }
}

interface UserDetailsRouter {

    fun route(user: UserPresentationModel)
}

class FragmentUserDetailsRouter(private val root: AppCompatActivity) : UserDetailsRouter {

    override fun route(user: UserPresentationModel) {
        UserItemDetailFragment
            .instance(user)
            .run {
                root.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.user_detail_container, this)
                    .commit()
            }
    }
}

class ActivityUserDetailsRouter(private val root: AppCompatActivity) : UserDetailsRouter {

    override fun route(user: UserPresentationModel) {
        UserItemDetailActivity
            .instance(root, user)
            .run {
                root.startActivity(this)
            }
    }
}