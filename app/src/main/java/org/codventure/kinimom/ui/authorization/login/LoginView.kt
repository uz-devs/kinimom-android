package org.codventure.kinimom.ui.authorization.login

interface LoginView {
    fun disableLoginButtons()
    fun enableLoginButtons()
    fun openSurvey()
    fun openMain()
    fun showSocialLoginFailed()
}