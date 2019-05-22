package com.slesarew.topstackoverflowusers.binding

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slesarew.topstackoverflowusers.R
import com.slesarew.topstackoverflowusers.userlist.view.adapter.UserListAdapter
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import org.joda.time.DateTime

@BindingAdapter("hasError", "hasConnection")
fun ImageView.setError(hasError: Boolean, hasConnection: Boolean) {
    when {
        hasError -> setImageDrawable(context.getDrawable(R.drawable.ic_server_error_24dp))
        !hasConnection -> setImageDrawable(context.getDrawable(R.drawable.ic_no_connection_24dp))
    }
}

@BindingAdapter("isFollowed")
fun ImageView.isFollowed(isFollowed: Boolean) = setImageResource(
    if (isFollowed)
        R.drawable.ic_followed_24dp
    else
        R.drawable.ic_unfollowed_24dp
)

@BindingAdapter("loadFromUrl")
fun ImageView.loadFromUrl(url: String) = Glide
    .with(context)
    .load(url)
    .into(this)

@BindingAdapter("hasError", "hasConnection")
fun TextView.setError(hasError: Boolean, hasConnection: Boolean) {
    when {
        hasError -> text = context.getString(R.string.server_error_detected)
        !hasConnection -> text = context.getString(R.string.no_internet_connection_detected)
    }
}

@BindingAdapter("formattedDate")
fun TextView.formattedDate(creationDate: Long) {
    text = DateTime()
        .withMillis(creationDate * 1000L)
        .toString("dd-MM-YYYY")
}

@BindingAdapter("hasError", "hasConnection")
fun LinearLayout.setError(hasError: Boolean, hasConnection: Boolean) {
    visibility = if (hasError || !hasConnection) View.VISIBLE else View.GONE
}

@BindingAdapter("setData")
fun RecyclerView.setData(data: List<UserPresentationModel>) {
    (adapter as UserListAdapter).users = data
}