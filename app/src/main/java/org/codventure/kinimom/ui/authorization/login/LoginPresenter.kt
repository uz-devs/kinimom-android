package org.codventure.kinimom.ui.authorization.login

import com.facebook.Profile
import org.codventure.kinimom.core.interactors.GetCommunities
import org.codventure.kinimom.core.interactors.SignUp
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import org.codventure.kinimom.framework.settings.Settings
import org.json.JSONObject
import javax.inject.Inject

class LoginPresenter(var view: LoginView) {
    // region inject
    @Inject
    lateinit var signUp: SignUp

    @Inject
    lateinit var getCommunities: GetCommunities

    @Inject
    lateinit var settings: Settings
    // endregion

    fun signUpWithFacebook(profile: Profile) {
        view.disableLoginButtons()
        doAsync {
            val user = signUp(
                social_login_type = "facebook",
                social_id = profile.id ?: "",
                social_name = profile.name ?: "",
                social_phone = "",
                social_photo = profile.getProfilePictureUri(56, 56).toString(),
                age = "",
                email = "",
                gender = ""
            )
            uiThread {
                view.enableLoginButtons()
                if (user == null) {
                    view.showSocialLoginFailed()
                    return@uiThread
                }

                // check for survey results
                if (user.shouldFillUpSurvey()) {
                    view.openSurvey()
                } else {
                    view.openMain()
                }
            }
        }
    }

    fun signUpWithNaver(profile: Map<String, String>) {
        view.disableLoginButtons()
        doAsync {
            val user = signUp(
                social_login_type = "naver",
                social_id = profile["id"] ?: "",
                social_name = profile["name"] ?: "",
                social_phone = profile["mobile_e164"] ?: "",
                social_photo = profile["profile_image"] ?: "",
                age = profile["age"] ?: "",
                email = profile["email"] ?: "",
                gender = profile["gender"] ?: ""
            )
            uiThread {
                view.enableLoginButtons()
                if (user == null) {
                    view.showSocialLoginFailed()
                    return@uiThread
                }

                // check for survey results
                if (user.shouldFillUpSurvey()) {
                    view.openSurvey()
                } else {
                    view.openMain()
                }
            }
        }
    }

    fun signUpWithKakao(profile: Map<String, String?>) {
        view.disableLoginButtons()
        doAsync {
            val user = signUp(
                social_login_type = "kakaotalk",
                social_id = profile["id"] ?: "",
                social_name = profile["nickname"] ?: "",
                social_phone = profile["phone"] ?: "",
                social_photo = profile["profilePicUrl"] ?: "",
                age = "",
                email = profile["email"] ?: "",
                gender = profile["gender"] ?: ""
            )
            uiThread {
                view.enableLoginButtons()
                if (user == null) {
                    view.showSocialLoginFailed()
                    return@uiThread
                }

                // check for survey results
                if (user.shouldFillUpSurvey()) {
                    view.openSurvey()
                } else {
                    view.openMain()
                }
            }
        }
    }
}
