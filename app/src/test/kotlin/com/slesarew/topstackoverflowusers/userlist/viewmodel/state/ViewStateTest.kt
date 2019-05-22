package com.slesarew.topstackoverflowusers.userlist.viewmodel.state

import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel
import org.junit.Test
import kotlin.test.assertEquals

class ViewStateTest {

    @Test
    fun `applies changes to view state`() {
        val reducedViewState = ViewState().apply {
            data = listOf(
                UserPresentationModel(),
                UserPresentationModel()
            )
            hasError = true
            hasConnection = false
        }

        val viewState = ViewState().apply {
            applyChanges(reducedViewState)
        }

        assertEquals(reducedViewState.data, viewState.data)
        assertEquals(reducedViewState.hasError, viewState.hasError)
        assertEquals(reducedViewState.hasConnection, viewState.hasConnection)
    }
}