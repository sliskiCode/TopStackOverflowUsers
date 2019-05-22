package com.slesarew.topstackoverflowusers.binding

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.slesarew.topstackoverflowusers.R
import com.slesarew.topstackoverflowusers.userlist.view.adapter.UserListAdapter
import com.slesarew.topstackoverflowusers.userlist.view.router.UserDetailsRouter
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class BindingAdapterTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun `sets error for image view`() = with(ImageView(context)) {
        val hasError = true
        val hasConnection = true

        setError(hasError, hasConnection)

        hasImage(R.drawable.ic_server_error_24dp)
    }

    @Test
    fun `sets no connection for image view`() = with(ImageView(context)) {
        val hasError = false
        val hasConnection = false

        setError(hasError, hasConnection)

        hasImage(R.drawable.ic_no_connection_24dp)
    }

    @Test
    fun `hides layout`() = with(LinearLayout(context)) {
        val hasError = false
        val hasConnection = true

        setError(hasError, hasConnection)

        isGone()
    }

    @Test
    fun `shows layout when no connection`() = with(LinearLayout(context)) {
        val hasError = true
        val hasConnection = true

        setError(hasError, hasConnection)

        isVisible()
    }

    @Test
    fun `shows layout when error`() = with(LinearLayout(context)) {
        val hasError = false
        val hasConnection = false

        setError(hasError, hasConnection)

        isVisible()
    }

    @Test
    fun `sets error message`() = with(TextView(context)) {
        val hasError = true
        val hasConnection = true

        setError(hasError, hasConnection)

        hasText(R.string.server_error_detected)
    }

    @Test
    fun `sets no connection message`() = with(TextView(context)) {
        val hasError = false
        val hasConnection = false

        setError(hasError, hasConnection)

        hasText(R.string.no_internet_connection_detected)
    }

    @Test
    fun `sets followed icon`() = with(ImageView(context)) {
        val isFollowed = true

        isFollowed(isFollowed)

        hasImage(R.drawable.ic_followed_24dp)
    }

    @Test
    fun `sets unfollowed icon`() = with(ImageView(context)) {
        val isFollowed = false

        isFollowed(isFollowed)

        hasImage(R.drawable.ic_unfollowed_24dp)
    }

    @Test
    fun `sets formatted date`() = with(TextView(context)) {
        val creationDate = 1222430705L

        formattedDate(creationDate)

        hasText("26-09-2008")
    }

    @Test
    fun `sets adapter data`() = with(RecyclerView(context)) {
        adapter = UserListAdapter(mock())
        val users = listOf(UserPresentationModel())

        setData(users)

        assertEquals(users, (adapter as UserListAdapter).users)
    }
}