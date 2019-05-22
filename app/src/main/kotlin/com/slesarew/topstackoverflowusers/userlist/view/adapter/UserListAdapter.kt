package com.slesarew.topstackoverflowusers.userlist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slesarew.topstackoverflowusers.databinding.UserItemBinding
import com.slesarew.topstackoverflowusers.userlist.view.router.UserDetailsRouter
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel

class UserListAdapter(
    private val router: UserDetailsRouter,
    private val dataSetChangedNotifier: DataSetChangedNotifier = diffUtilNotifier
) : RecyclerView.Adapter<ViewHolder>() {

    var users: List<UserPresentationModel> = emptyList()
        set(value) = dataSetChangedNotifier(field, value, this).also {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            router
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(users[position])

    override fun getItemCount(): Int = users.size
}

open class ViewHolder(
    private val userItemBinding: UserItemBinding,
    private val userDetailsRouter: UserDetailsRouter
) : RecyclerView.ViewHolder(userItemBinding.root) {

    open fun bind(userPresentationModel: UserPresentationModel) =
        with(userItemBinding) {
            user = userPresentationModel
            router = userDetailsRouter
            executePendingBindings()
        }
}