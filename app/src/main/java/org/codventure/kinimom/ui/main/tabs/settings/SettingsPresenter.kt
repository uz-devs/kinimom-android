package org.codventure.kinimom.ui.main.tabs.settings

import org.codventure.kinimom.framework.settings.Settings
import javax.inject.Inject

class SettingsPresenter(val view: SettingsView) {
    // region injections
    @Inject
    lateinit var settings: Settings
    // endregion

    fun initProfile() {
        view.setName(settings.getUsername())
        view.setProfileImage(settings.getSocialLoginCredentials().social_photo)
    }
}
