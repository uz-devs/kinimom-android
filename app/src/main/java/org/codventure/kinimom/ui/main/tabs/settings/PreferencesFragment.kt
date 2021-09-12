package org.codventure.kinimom.ui.main.tabs.settings

import kotlinx.android.synthetic.main.fragment_login_survey.*
import androidx.fragment.app.Fragment
import org.codventure.kinimom.R
import android.os.Bundle
import android.view.View

class PreferencesFragment : Fragment(R.layout.fragment_main_preferences) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
