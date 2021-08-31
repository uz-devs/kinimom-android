package org.codventure.kinimom.ui.authorization.login

/**
 * Created by abduaziz on 7/18/21 at 5:03 PM.
 */

interface LoginView {
    fun disableLoginButtons()
    fun enableLoginButtons()
    fun openSurvey()
    fun openMain()
    fun showSocialLoginFailed()
}