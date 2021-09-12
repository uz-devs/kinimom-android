package org.codventure.kinimom.ui.authorization.survey.pages

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_survey_4.*
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.extension.removeChars
import org.codventure.kinimom.ui.authorization.survey.SurveyFragment


class Page4Fragment(val surveyFragment: SurveyFragment, var isPreparing: Boolean = false) :
    Fragment(R.layout.fragment_login_survey_4) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isPreparing){
            tvWeightBeforePregnancy.visibility = View.GONE
            flWeightBeforePregnancy.visibility = View.GONE
            etWeightBefore.visibility = View.GONE
        } else{
            tvWeightBeforePregnancy.visibility = View.VISIBLE
            flWeightBeforePregnancy.visibility = View.VISIBLE
            etWeightBefore.visibility = View.VISIBLE
        }

        etHeight.mask = "999cm"
        etHeight.inputType = InputType.TYPE_CLASS_NUMBER
        etHeight.addTextChangedListener { s ->
            surveyFragment.surveyResults.height = s.toString().removeChars()
            surveyFragment.updateNextButton()
        }

        etWeight.mask = "999kg"
        etWeight.inputType = InputType.TYPE_CLASS_NUMBER
        etWeight.addTextChangedListener { s ->
            surveyFragment.surveyResults.weight = s.toString().removeChars()
            surveyFragment.updateNextButton()
        }

        etWeightBefore.mask = "999kg"
        etWeightBefore.inputType = InputType.TYPE_CLASS_NUMBER
        etWeightBefore.addTextChangedListener { s ->
            surveyFragment.surveyResults.weight_before = s.toString().removeChars()
            surveyFragment.updateNextButton()
        }
    }
}