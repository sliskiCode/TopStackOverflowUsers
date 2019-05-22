package com.slesarew.topstackoverflowusers.userlist.model.users

import com.slesarew.topstackoverflowusers.repository.model.User
import io.reactivex.Single

/**
 * Repository contract that is responsible for providing [User] list.
 */
interface UserRepository {

    /**
     * Function for getting [Single] containing list of [User]s.
     *
     * @param limit Integer value that limits the size of emitted list.
     * @return a reactive [Single] type that emits list of user.
     */
    fun getUsers(limit: Int): Single<List<User>>
}