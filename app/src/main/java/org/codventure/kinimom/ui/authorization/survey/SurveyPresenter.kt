package org.codventure.kinimom.ui.authorization.survey

import org.codventure.kinimom.core.interactors.UserInfoSave
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import org.codventure.kinimom.framework.settings.Settings
import javax.inject.Inject


class SurveyPresenter(val view: SurveyView) {

    @Inject
    lateinit var userInfoSave: UserInfoSave

    @Inject
    lateinit var settings: Settings

    fun saveUserInfo(surveyResults: SurveyResults){
        doAsync {
            val response = userInfoSave(settings.getUserId(), surveyResults)
            uiThread {
                if (response == true){
                    view.openMain()
                }else{
                    view.showUserUpdateError()
                }
            }
        }
    }

}