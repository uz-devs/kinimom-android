package org.codventure.kinimom.ui.authorization.login

import com.facebook.Profile
import org.codventure.kinimom.core.interactors.SignUp
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import javax.inject.Inject

/**
 * Created by abduaziz on 7/18/21 at 5:03 PM.
 */

class LoginPresenter(var view: LoginView?) {

    @Inject
    lateinit var signUp: SignUp

    fun signUpWithFacebook(profile: Profile) {
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
                if (user == null){
                    view?.showSocialLoginFailed()
                    return@uiThread
                }

                // check for survey results
                if (user.shouldFillUpSurvey()){
                    view?.openSurvey()
                }else{
                    view?.openMain()
                }
            }
        }
    }

}