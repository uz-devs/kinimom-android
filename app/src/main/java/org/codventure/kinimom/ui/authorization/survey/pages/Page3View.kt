package org.codventure.kinimom.ui.authorization.survey.pages


interface Page3View {
    fun showInternetConnectionError()

    fun enableCheckNickameButton()
    fun disableCheckNicknameButton()

    fun showValidNickname()
    fun showInvalidNicknameError()

    fun showNicknameUnavailable(invalidNickname: String)
    fun showNicknameAvailable(validNickname: String)
}