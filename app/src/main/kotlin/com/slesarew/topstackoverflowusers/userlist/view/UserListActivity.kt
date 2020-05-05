package com.slesarew.topstackoverflowusers.userlist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.slesarew.topstackoverflowusers.R
import com.slesarew.topstackoverflowusers.databinding.RootUserListBinding
import com.slesarew.topstackoverflowusers.userlist.view.adapter.UserListAdapter
import com.slesarew.topstackoverflowusers.userlist.view.router.UserDetailsRouterFactory
import com.slesarew.topstackoverflowusers.userlist.viewmodel.UserListViewModel
import com.slesarew.topstackoverflowusers.userlist.viewmodel.UserListViewModelFactory
import kotlinx.android.synthetic.main.app_bar.*

class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView<RootUserListBinding>(this, R.layout.root_user_list).apply {
            state = ViewModelProvider(this@UserListActivity, UserListViewModelFactory())
                .get<UserListViewModel>()
                .viewState

            userList.list.adapter = UserListAdapter(
                UserDetailsRouterFactory.create(
                    this@UserListActivity,
                    userList.userDetailContainer != null
                )
            ).apply {
                setHasStableIds(true)
            }
        }

        setSupportActionBar(toolbar)
    }
}