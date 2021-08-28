package org.codventure.kinimom.core.data.request

import com.google.gson.annotations.SerializedName

/**
 * Created by abduaziz on 7/13/21 at 10:54 PM.
 */

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