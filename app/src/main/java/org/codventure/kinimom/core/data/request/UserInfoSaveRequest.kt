package org.codventure.kinimom.core.data.request

/**
 * Created by abduaziz on 8/17/21 at 7:16 PM.
 */

class UserInfoSaveRequest(
    val user_id: Long,
    val icon: String?,
    val is_pregnant: String?,
    val nickname: String?,
    val expected_date: String?,
    val birthday: String?,
    val gender: String?,
    val height: String?,
    val weight: String?,
    val weight_before: String?,
    val period_cycle: String?,
    val period_term: String?,
    val period_end_date: String?
)