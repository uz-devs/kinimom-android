package org.codventure.kinimom.core.domain

class User(
    var id: Long = 0,
    var status: String? = "",
    var role: String? = "",
    var icon: String? = "",
    var login: String? = "",
    var nickname: String? = "",
    var email: String? = "",
    var first_name: String? = "",
    var last_name: String? = "",
    var gender: String? = "",
    var birthday: String? = "",
    var age: String? = "",
    var is_pregnant: String? = "",
    var height: String? = "",
    var weight: String? = "",
    var weight_before: String? = "",
    var expected_date: String? = "",
    var period_end_date: String? = "",
    var period_term: String? = "",
    var period_cycle: String? = "",
    var code: String? = "",
    var invited_code: String? = "",
    var password: String? = "",
    var mb_no: String? = "",
    var created_at: String? = "",
    var updated_at: String? = "",
    var deleted_at: String? = ""
) {
    fun shouldFillUpSurvey(): Boolean {
        return is_pregnant.isNullOrBlank() ||
                expected_date.isNullOrBlank() ||
                nickname.isNullOrBlank() || birthday.isNullOrBlank() || gender.isNullOrBlank() ||
                height.isNullOrBlank() || weight.isNullOrBlank() || weight_before.isNullOrBlank()
    }
}