package com.slesarew.topstackoverflowusers.userdetails.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.slesarew.topstackoverflowusers.R
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import kotlinx.android.synthetic.main.standalone_user_detail.detail_toolbar as detailToolbar

class UserItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.standalone_user_detail)
        setSupportActionBar(detailToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            UserItemDetailFragment
                .instance(intent.getParcelableExtra(ARG_ITEM))
                .run {
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.user_detail_container, this)
                        .commit()
                }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {

        private const val ARG_ITEM = "item"

        fun instance(
            context: Context,
            userPresentationModel: UserPresentationModel
        ): Intent =
            Intent(context, UserItemDetailActivity::class.java).putExtra(
                ARG_ITEM, userPresentationModel
            )
    }
}