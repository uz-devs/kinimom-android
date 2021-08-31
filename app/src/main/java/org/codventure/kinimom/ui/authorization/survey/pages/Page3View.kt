package org.codventure.kinimom.ui.authorization.survey.pages

/**
 * Created by abduaziz on 8/16/21 at 4:09 PM.
 */

interface Page3View {
    fun showInternetConnectionError()

    fun enableCheckNickameButton()
    fun disableCheckNicknameButton()

    fun showValidNickname()
    fun showInvalidNicknameError()

    fun showNicknameUnavailable(invalidNickname: String)
    fun showNicknameAvailable(validNickname: String)
}