package org.codventure.kinimom.ui.main.tabs.settings

import org.codventure.kinimom.framework.di.ApplicationComponent
import kotlinx.android.synthetic.main.fragment_main_settings.*
import org.codventure.kinimom.framework.extension.userAvatar
import org.codventure.kinimom.AndroidApplication
import androidx.fragment.app.Fragment
import org.codventure.kinimom.R
import com.bumptech.glide.Glide
import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.codventure.kinimom.framework.settings.Prefs
import org.codventure.kinimom.ui.MainActivity

class SettingsFragment : Fragment(R.layout.fragment_main_settings), SettingsView {
    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }
    private lateinit var presenter: SettingsPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SettingsPresenter(this)
        appComponent.inject(presenter)

        presenter.initProfile()

        tvEditUser.setOnClickListener {
            Prefs.clear(requireContext())
            (activity as MainActivity).openSocialLoginScreen()
        }

        ivPreferences.setOnClickListener {
            (activity as MainActivity).openPreferences()
        }
    }

    override fun setName(name: String) {
        tvSocialName.text = name
    }

    override fun setProfileImage(imageUrl: String) {
        Glide.with(requireContext()).load(imageUrl).error(imageUrl.userAvatar()).into(ivProfileImage)
    }
}
