package org.codventure.kinimom.framework.network

import org.codventure.kinimom.core.data.request.*
import org.codventure.kinimom.core.data.response.*
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KinimomApiService
@Inject constructor(retrofit: Retrofit) : KinimomApi {
    private val api by lazy { retrofit.create(KinimomApi::class.java) }

    override fun signUp(body: SignUpRequest) = api.signUp(body)

    override fun checkNickname(
        Authorization: String,
        body: CheckNicknameRequest
    ): Call<CheckNicknameResponse> = api.checkNickname(Authorization, body)

    override fun userInfoSave(
        Authorization: String,
        body: UserInfoSaveRequest
    ): Call<UserInfoSaveResponse> = api.userInfoSave(Authorization, body)

    override fun communityList(
        Authorization: String,
        body: CommunityListRequest
    ): Call<CommunityListResponse> = api.communityList(Authorization, body)

    override fun communityDetail(
        Authorization: String,
        body: CommunityDetailRequest
    ): Call<CommunityDetailResponse> = api.communityDetail(Authorization, body)

    override fun getTestLastOne(
        Authorization: String,
        body: TestLastOneRequest
    ): Call<TestLastOneResponse> = api.getTestLastOne(Authorization, body)

    override fun getBestCommunities(
        Authorization: String,
        body: BestCommunitiesRequest
    ): Call<BestCommunitiesResponse> = api.getBestCommunities(Authorization, body)
    
    override fun getMenstruation(
        Authorization: String,
        body: GetMenstruationRequest
    ): Call<GetMenstruationResponse> = api.getMenstruation(Authorization, body)
}