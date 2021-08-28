package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.SignUpRequest
import javax.inject.Inject

/**
 * Created by abduaziz on 7/13/21 at 11:04 PM.
 */

class SignUp
@Inject constructor(private val kinimomRepository: KinimomRepository) {
    operator fun invoke( social_login_type: String,
                         social_id: String,
                         social_name: String,
                         social_phone: String,
                         social_photo: String,
                         age: String,
                         email: String,
                         gender: String) = kinimomRepository.signUp(SignUpRequest(social_login_type, social_id, social_name, social_phone, social_photo, age, email, gender))
}