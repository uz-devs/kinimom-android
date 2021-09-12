package org.codventure.kinimom.ui

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.R
import org.codventure.kinimom.core.domain.Community
import org.codventure.kinimom.core.interactors.GetCommunities
import org.codventure.kinimom.core.interactors.SignUp
import org.codventure.kinimom.framework.di.ApplicationComponent
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import org.codventure.kinimom.framework.settings.Settings
import org.codventure.kinimom.ui.authorization.agreement.AgreementFragment
import org.codventure.kinimom.ui.authorization.login.LoginFragment
import org.codventure.kinimom.ui.authorization.survey.SurveyFragment
import org.codventure.kinimom.ui.main.MainFragment
import org.codventure.kinimom.ui.main.tabs.community.detail.CommunityDetailFragment
import org.codventure.kinimom.ui.main.tabs.settings.PreferencesFragment
import org.codventure.kinimom.ui.main.tabs.settings.SettingsFragment
import org.codventure.kinimom.ui.splash.SplashFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as AndroidApplication).appComponent
    }

    // region inject
    @Inject
    lateinit var settings: Settings

    @Inject
    lateinit var signUp: SignUp

    @Inject
    lateinit var getCommunities: GetCommunities
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.inject(this)

        if (savedInstanceState == null) {
            if (settings.isUserLoggedIn()) {
                openSplashScreen()
                refreshTokenAndOpenMain()
            } else {
                openSocialLoginScreen()
            }
        }
    }

    private fun refreshTokenAndOpenMain() {
        val socialUser = settings.getSocialLoginCredentials()
        doAsync {
            val user = signUp(
                social_login_type = socialUser.social_login_type,
                social_id = socialUser.social_id,
                social_name = socialUser.social_name,
                social_phone = socialUser.social_phone,
                social_photo = socialUser.social_photo,
                age = "",
                email = "",
                gender = ""
            )
            uiThread {
                if (user?.shouldFillUpSurvey() == false) {
                    openMainScreen()
                } else {
                    openSocialLoginScreen()
                }
            }
        }
    }

    fun openSplashScreen() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, SplashFragment())
        }
    }

    fun openSocialLoginScreen() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, LoginFragment())
        }
    }

    fun openAgreementsScreen() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, AgreementFragment())
        }
    }

    fun openSurveyScreen() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, SurveyFragment())
        }
    }

    fun openMainScreen() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, MainFragment())
        }
    }

    fun openCommunityDetail(community: Community, wannaComment: Boolean) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack("CommunityDetailFragment")
            add(R.id.fragment_container_view, CommunityDetailFragment(community, wannaComment))
        }
    }

    fun openPreferences() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack("SettingsFragment")
            add(R.id.fragment_container_view, PreferencesFragment())
        }
    }

    // region Bloody keyboard utils
    // hide keyboard
    fun hideSoftInput() {
        if (windowToken != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        } else {
            Log.d("Keyboard", "Keyboard close error")
        }
    }

    // show keyboard
    private var windowToken: IBinder? = null

    fun showSoftKeyboard(et: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        et.requestFocus()
        et.isCursorVisible = true
        this.windowToken = et.windowToken
    }
    // endregion
}
