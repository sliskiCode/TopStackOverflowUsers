package com.slesarew.topstackoverflowusers.userdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.slesarew.topstackoverflowusers.databinding.UserDetailBinding
import com.slesarew.topstackoverflowusers.extension.putValue
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import kotlinx.android.synthetic.main.standalone_user_detail.toolbar_layout as toolbarLayout
import kotlinx.android.synthetic.main.user_detail.view.creation_date as creationDate

class UserItemDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UserDetailBinding
        .inflate(inflater, container, false)
        .apply {
            val userPresentationModel = unzip(arguments)

            activity?.toolbarLayout?.title = userPresentationModel.name
            user = userPresentationModel
        }
        .root

    companion object {

        private const val ARG_USER = "USER"

        fun instance(userPresentationModel: UserPresentationModel): UserItemDetailFragment =
            UserItemDetailFragment().apply {
                arguments = Bundle().putValue(ARG_USER, userPresentationModel)
            }

        private fun unzip(arguments: Bundle?): UserPresentationModel =
            arguments?.getParcelable(ARG_USER)
                ?: throw IllegalStateException("Unzip failed. No UserPresentationModel in bundle!")
    }
}