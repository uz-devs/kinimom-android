package org.codventure.kinimom.ui.main.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main_settings.*
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.settings.Prefs
import org.codventure.kinimom.ui.MainActivity

class SettingsFragment : Fragment(R.layout.fragment_main_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvEditUser.setOnClickListener {
            Prefs.clear(requireContext())
            (activity as MainActivity).openSocialLoginScreen()
        }
    }
}