package com.slesarew.topstackoverflowusers.userlist.viewmodel.state

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.slesarew.topstackoverflowusers.BR
import com.slesarew.topstackoverflowusers.userlist.viewmodel.data.UserPresentationModel

class ViewState : BaseObservable() {

    @Bindable
    var data = emptyList<UserPresentationModel>()
        set(value) {
            if (field != value) {
                field = value

                notifyPropertyChanged(BR.data)
            }
        }

    @Bindable
    var hasError = false
        set(value) {
            if (field != value) {
                field = value

                notifyPropertyChanged(BR.hasError)
            }
        }

    @Bindable
    var hasConnection = true
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.hasConnection)
            }
        }

    fun applyChanges(viewState: ViewState) {
        data = viewState.data
        hasError = viewState.hasError
        hasConnection = viewState.hasConnection
    }
}