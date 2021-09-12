package org.codventure.kinimom.core.data

import org.codventure.kinimom.core.data.request.*
import org.codventure.kinimom.core.data.response.BestCommunitiesResponse
import org.codventure.kinimom.core.data.response.GetAllNoticeResponse
import org.codventure.kinimom.core.data.response.GetMenstruationResponse
import org.codventure.kinimom.core.data.response.TestLastOneResponse
import org.codventure.kinimom.core.domain.Community
import org.codventure.kinimom.core.domain.User

interface KinimomRepository {
    fun signUp(request: SignUpRequest): User?
    fun checkNickname(body: CheckNicknameRequest): Boolean?
    fun userInfoSave(body: UserInfoSaveRequest): Boolean?
    fun getCommunityList(body: CommunityListRequest): ArrayList<Community>?
    fun getCommunity(body: CommunityDetailRequest): Community?
    fun getTestLastOne(body: BasicRequest): TestLastOneResponse?
    fun getBestCommunities(body: BasicRequest): BestCommunitiesResponse?
    fun getMenstruation(body: GetMenstruationRequest): GetMenstruationResponse?
    fun getAllNotice(body: BasicRequest): GetAllNoticeResponse?
}
