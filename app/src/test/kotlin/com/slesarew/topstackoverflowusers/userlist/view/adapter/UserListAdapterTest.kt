package com.slesarew.topstackoverflowusers.userlist.view.adapter

import android.view.View
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.slesarew.topstackoverflowusers.userlist.view.router.UserDetailsRouter
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import org.junit.Test
import kotlin.test.assertEquals

class UserListAdapterTest {

    private val userDetailsRouterMock = mock<UserDetailsRouter>()
    private val dataSetChangedNotifierMock = mock<DataSetChangedNotifier>()

    private val tested = UserListAdapter(
        userDetailsRouterMock,
        dataSetChangedNotifierMock
    )

    @Test
    fun `returns correct number of users`() {
        tested.users = listOf(
            UserPresentationModel(),
            UserPresentationModel(),
            UserPresentationModel(),
            UserPresentationModel()
        )

        val actual = tested.itemCount

        assertEquals(4, actual)
    }

    @Test
    fun `notifies about data set changed`() {
        val oldValue = tested.users
        val newValue = listOf(UserPresentationModel())

        tested.users = newValue

        verify(dataSetChangedNotifierMock).invoke(oldValue, newValue, tested)
    }

    @Test
    fun `binds view holder with correct data`() {
        val userPresentationModel = UserPresentationModel()
        tested.users = listOf(userPresentationModel)
        val viewHolder = ViewHolderSpy()

        tested.onBindViewHolder(viewHolder, 0)

        assertEquals(userPresentationModel, viewHolder.userPresentationModel)
    }
}

class ViewHolderSpy : ViewHolder(
    mock {
        on {
            root
        } doReturn (mock<View>())
    },
    mock()
) {

    var userPresentationModel: UserPresentationModel? = null

    override fun bind(userPresentationModel: UserPresentationModel) {
        this.userPresentationModel = userPresentationModel
    }
}