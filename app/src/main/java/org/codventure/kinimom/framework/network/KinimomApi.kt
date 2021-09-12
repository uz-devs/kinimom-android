package org.codventure.kinimom.framework.network

import org.codventure.kinimom.core.data.request.*
import org.codventure.kinimom.core.data.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface KinimomApi {
    @POST("socialLogin")
    fun signUp(@Body body: SignUpRequest): Call<SignUpResponse>

    @POST("api/userNicknameCheck")
    fun checkNickname(
        @Header("Authorization") Authorization: String,
        @Body body: CheckNicknameRequest
    ): Call<CheckNicknameResponse>

    @POST("api/userInfoSave")
    fun userInfoSave(
        @Header("Authorization") Authorization: String,
        @Body body: UserInfoSaveRequest
    ): Call<UserInfoSaveResponse>

    @POST("api/communityList")
    fun communityList(
        @Header("Authorization") Authorization: String,
        @Body body: CommunityListRequest
    ): Call<CommunityListResponse>

    @POST("api/communityGetOne")
    fun communityDetail(
        @Header("Authorization") Authorization: String,
        @Body body: CommunityDetailRequest
    ): Call<CommunityDetailResponse>

    @POST("api/testLastOne")
    fun getTestLastOne(
        @Header("Authorization") Authorization: String,
        @Body body: BasicRequest
    ): Call<TestLastOneResponse>

    @POST("api/bestCommunities")
    fun getBestCommunities(
        @Header("Authorization") Authorization: String,
        @Body body: BasicRequest
    ): Call<BestCommunitiesResponse>

    @POST("api/communityCommentSave")
    fun comment(
        @Header("Authorization") Authorization: String,
        @Body body: CommentRequest
    ): Call<CommentResponse>

    @POST("api/getMenstruation")
    fun getMenstruation(
        @Header("Authorization") Authorization: String,
        @Body body: GetMenstruationRequest
    ): Call<GetMenstruationResponse>

    @POST("api/getAllNotice")
    fun getAllNotice(
        @Header("Authorization") Authorization: String,
        @Body body: BasicRequest
    ): Call<GetAllNoticeResponse>
}