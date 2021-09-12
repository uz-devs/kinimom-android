package org.codventure.kinimom.core.data.request

class SignUpRequest(
    val social_login_type: String,
    val social_id: String,
    val social_name: String,
    val social_phone: String,
    val social_photo: String,
    val age: String = "",
    val email: String = "",
    val gender: String = ""
)