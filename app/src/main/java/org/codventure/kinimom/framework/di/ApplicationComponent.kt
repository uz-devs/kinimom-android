package org.codventure.kinimom.framework.di

import org.codventure.kinimom.ui.main.tabs.community.detail.CommunityDetailPresenter
import org.codventure.kinimom.ui.authorization.survey.pages.Page3Presenter
import org.codventure.kinimom.ui.main.tabs.community.CommunityPresenter
import org.codventure.kinimom.ui.authorization.survey.SurveyPresenter
import org.codventure.kinimom.ui.authorization.login.LoginPresenter
import org.codventure.kinimom.ui.main.tabs.home.HomePresenter
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.ui.MainActivity
import javax.inject.Singleton
import dagger.Component
import org.codventure.kinimom.ui.main.tabs.settings.SettingsPresenter

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(activity: MainActivity)

    fun inject(presenter: LoginPresenter)

    fun inject(presenter: Page3Presenter)
    fun inject(presenter: SurveyPresenter)

    fun inject(presenter: CommunityPresenter)
    fun inject(presenter: HomePresenter)
    fun inject(presenter: CommunityDetailPresenter)

    fun inject(presenter: SettingsPresenter)
}
