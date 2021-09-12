package org.codventure.kinimom.framework.network

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.*
import org.codventure.kinimom.core.data.response.BestCommunitiesResponse
import org.codventure.kinimom.core.data.response.GetAllNoticeResponse
import org.codventure.kinimom.core.data.response.GetMenstruationResponse
import org.codventure.kinimom.core.data.response.TestLastOneResponse
import org.codventure.kinimom.core.domain.Comment
import org.codventure.kinimom.core.domain.Community
import org.codventure.kinimom.core.domain.User
import org.codventure.kinimom.framework.settings.Settings
import javax.inject.Inject

class Network
@Inject constructor(private val service: KinimomApiService, private val settings: Settings) : KinimomRepository {
    override fun signUp(request: SignUpRequest): User? {
        val response = service.signUp(request).execute()
        settings.saveUserId(response.body()?.user?.id ?: -1L)
        settings.saveToken(response.body()?.token ?: "")

        settings.saveSocialLoginCredentials(
            request.social_login_type,
            request.social_id,
            request.social_name,
            request.social_phone,
            request.social_photo
        )

        return response.body()?.user
    }

    override fun checkNickname(body: CheckNicknameRequest): Boolean? {
        val token = settings.getToken()
        val response = service.checkNickname(token, body).execute()
        return response.body()?.status
    }

    override fun userInfoSave(body: UserInfoSaveRequest): Boolean? {
        val token = settings.getToken()
        val response = service.userInfoSave(token, body).execute()
        return response.body()?.status
    }

    override fun getCommunityList(body: CommunityListRequest): ArrayList<Community>? {
        val token = settings.getToken()
        val response = service.communityList(token, body).execute()
        return response.body()?.community_list?.apply {
            this.forEach {
                it.isOwnedByUser = it.user_id == settings.getUserId().toString()
            }
        }
    }

    override fun getCommunity(body: CommunityDetailRequest): Community? {
        val token = settings.getToken()
        val response = service.communityDetail(token, body).execute()
        return response.body()?.community_one?.apply {
            this.comments = response.body()?.comments
                ?: arrayListOf() // <- had to do this, becoz of poor design choice in JSON response
            this.isOwnedByUser = this.user_id == settings.getUserId().toString()
        }
    }

    override fun getTestLastOne(body: BasicRequest): TestLastOneResponse? {
        val token = settings.getToken()
        val response = service.getTestLastOne(token, body).execute()
        return response.body()
    }

    override fun getBestCommunities(body: BasicRequest): BestCommunitiesResponse? {
        val token = settings.getToken()
        val response = service.getBestCommunities(token, body).execute()
        return response.body()
    }

    override fun comment(body: CommentRequest): Comment? {
        val token = settings.getToken()
        val response = service.comment(token, body).execute()
        return response.body()?.data?.apply {
            isOwnedByUser = this.user_id == settings.getUserId().toString()
        }
    }

    override fun getMenstruation(body: GetMenstruationRequest): GetMenstruationResponse? {
        val token = settings.getToken()
        val response = service.getMenstruation(token, body).execute()
        return response.body()
    }

    override fun getAllNotice(body: BasicRequest): GetAllNoticeResponse? {
        val token = settings.getToken()
        val response = service.getAllNotice(token, body).execute()
        return response.body()
    }
}
