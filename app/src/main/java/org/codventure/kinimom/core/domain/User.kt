package org.codventure.kinimom.core.domain

/**
 * Created by abduaziz on 7/13/21 at 11:00 PM.
 */

class User(
    val id: Long,
    val status: String?,
    val role: String?,
    val icon: String?,
    val login: String?,
    val nickname: String?,
    val email: String?,
    val first_name: String?,
    val last_name: String?,
    val gender: String?,
    val birthday: String?,
    val age: String?,
    val is_pregnant: String?,
    val height: String?,
    val weight: String?,
    val weight_before: String?,
    val expected_date: String?,
    val period_end_date: String?,
    val period_term: String?,
    val period_cycle: String?,
    val code: String?,
    val invited_code: String?,
    val password: String?,
    val mb_no: String?,
    val created_at: String?,
    val updated_at: String?,
    val deleted_at: String?
){
    fun shouldFillUpSurvey(): Boolean{
        return  is_pregnant.isNullOrBlank() ||
                expected_date.isNullOrBlank() ||
                nickname.isNullOrBlank() || birthday.isNullOrBlank() || gender.isNullOrBlank() ||
                height.isNullOrBlank() || weight.isNullOrBlank() || weight_before.isNullOrBlank() ||
                period_end_date.isNullOrBlank() || period_cycle.isNullOrBlank() || period_term.isNullOrBlank()
    }
}