package org.codventure.kinimom.framework.di

import dagger.Component
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.ui.MainActivity
import org.codventure.kinimom.ui.authorization.login.LoginPresenter
import org.codventure.kinimom.ui.authorization.survey.SurveyPresenter
import org.codventure.kinimom.ui.authorization.survey.pages.Page3Presenter
import org.codventure.kinimom.ui.main.tabs.community.CommunityPresenter
import javax.inject.Singleton

/**
 * Created by abduaziz on 7/17/21 at 10:42 PM.
 */

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: AndroidApplication)
    fun inject(activity: MainActivity)

    fun inject(presenter: LoginPresenter)

    fun inject(presenter: Page3Presenter)
    fun inject(presenter: SurveyPresenter)

    fun inject(presenter: CommunityPresenter)
}