package org.codventure.kinimom.core.data

import org.codventure.kinimom.core.data.request.*
import org.codventure.kinimom.core.domain.Community
import org.codventure.kinimom.core.domain.User

/**
 * Created by abduaziz on 7/17/21 at 9:03 PM.
 */

interface KinimomRepository {
    fun signUp(request: SignUpRequest): User?
    fun checkNickname(body: CheckNicknameRequest): Boolean?
    fun userInfoSave(body: UserInfoSaveRequest): Boolean?
    fun getCommunityList(body: CommunityListRequest): ArrayList<Community>?
    fun getCommunity(body: CommunityDetailRequest): Community?
}