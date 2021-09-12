package org.codventure.kinimom.ui.authorization.survey.pages

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_survey_1.*
import org.codventure.kinimom.R
import org.codventure.kinimom.ui.authorization.survey.SurveyFragment


class Page1Fragment(val surveyFragment: SurveyFragment) : Fragment(R.layout.fragment_login_survey_1) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvHaveABaby.setOnClickListener {
            selectHaveABaby()
        }

        tvGettingReady.setOnClickListener {
            selectGettingReady()
        }
    }

    private fun selectHaveABaby(){
        surveyFragment.surveyResults.is_pregnant = "1"
        surveyFragment.updateNextButton()

        llHaveABaby.setBackgroundResource(R.drawable.login_button_background_enabled)
        tvHaveABaby.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        llGettingReady.setBackgroundResource(R.drawable.login_button_background)
        tvGettingReady.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
    }

    private fun selectGettingReady(){
        surveyFragment.surveyResults.is_pregnant = "2"
        surveyFragment.updateNextButton()

        llHaveABaby.setBackgroundResource(R.drawable.login_button_background)
        tvHaveABaby.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

        llGettingReady.setBackgroundResource(R.drawable.login_button_background_enabled)
        tvGettingReady.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
    }
}