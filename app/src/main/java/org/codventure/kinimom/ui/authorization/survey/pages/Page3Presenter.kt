package org.codventure.kinimom.ui.authorization.survey.pages

import org.codventure.kinimom.core.interactors.CheckNickname
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import javax.inject.Inject


class Page3Presenter(var view: Page3View) {

    @Inject
    lateinit var checkNickname: CheckNickname

    fun checkUserNickname(nickname: String){
        if (!validateNickname(nickname)) {
            view.showInvalidNicknameError()
            return
        }
        doAsync {
            val isValidNickname = checkNickname(nickname)
            uiThread {
                if (isValidNickname == null){
                    view.showInternetConnectionError()
                    return@uiThread
                }

                if (isValidNickname){
                    view.showNicknameAvailable(nickname)
                }else{
                    view.showNicknameUnavailable(nickname)
                }
            }
        }
    }

    private val disallowedChars = "[ !\"#$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~]+"
    fun validateNickname(nickname: String): Boolean {
        if (nickname.length <= 2 || nickname.length > 10){
            view.showInvalidNicknameError()
            view.disableCheckNicknameButton()
            return false
        }

        for (c in nickname){
            if (disallowedChars.contains(c)){
                view.showInvalidNicknameError()
                view.disableCheckNicknameButton()
                return false
            }
        }

        view.showValidNickname()
        view.enableCheckNickameButton()
        return true
    }

}