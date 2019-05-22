package com.slesarew.topstackoverflowusers.userlist.view.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel

typealias DataSetChangedNotifier =
            (old: List<UserPresentationModel>, new: List<UserPresentationModel>, adapter: RecyclerView.Adapter<ViewHolder>) -> Unit

val diffUtilNotifier: DataSetChangedNotifier = { old, new, adapter ->
    DiffUtil
        .calculateDiff(UserDiffer(old, new))
        .dispatchUpdatesTo(adapter)
}

class UserDiffer(
    private val old: List<UserPresentationModel>,
    private val new: List<UserPresentationModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition].id == new[newItemPosition].id

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]
}