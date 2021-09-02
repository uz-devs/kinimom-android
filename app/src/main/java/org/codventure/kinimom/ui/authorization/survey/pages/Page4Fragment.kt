package org.codventure.kinimom.ui.authorization.survey.pages

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_survey_4.*
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.extension.removeChars
import org.codventure.kinimom.ui.authorization.survey.SurveyFragment

/**
 * Created by abduaziz on 8/13/21 at 9:04 PM.
 */

class Page4Fragment(val surveyFragment: SurveyFragment, var isPreparing: Boolean = false) :
    Fragment(R.layout.fragment_login_survey_4) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etHeight.mask = "999cm"
        etHeight.addTextChangedListener { s ->
            surveyFragment.surveyResults.height = s.toString().removeChars()
            surveyFragment.updateNextButton()
        }

        etWeight.mask = "999kg"
        etWeight.addTextChangedListener { s ->
            surveyFragment.surveyResults.weight = s.toString().removeChars()
            surveyFragment.updateNextButton()
        }

        etWeightBefore.mask = "999kg"
        etWeightBefore.addTextChangedListener { s ->
            surveyFragment.surveyResults.weight_before = s.toString().removeChars()
            surveyFragment.updateNextButton()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isPreparing){
            tvWeightBeforePregnancy.visibility = View.GONE
            flWeightBeforePregnancy.visibility = View.GONE
            etWeightBefore.visibility = View.GONE
        } else{
            tvWeightBeforePregnancy.visibility = View.VISIBLE
            flWeightBeforePregnancy.visibility = View.VISIBLE
            etWeightBefore.visibility = View.VISIBLE
        }
    }
}