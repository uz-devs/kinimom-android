package org.codventure.kinimom.ui.authorization.survey.pages

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_survey_preparing_4.*
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.extension.removeChars
import org.codventure.kinimom.ui.authorization.survey.SurveyFragment
import java.util.*

/**
 * Created by abduaziz on 9/2/21 at 3:35 PM.
 */

class PreparingPage4Fragment(val surveyFragment: SurveyFragment) :
    Fragment(R.layout.fragment_login_survey_preparing_4) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set date to today
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        tvLastMenstrualPeriod.hint = "예시 : ${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH)}월 ${calendar.get(Calendar.DAY_OF_MONTH)}일"

        tvLastMenstrualPeriod.setOnClickListener {
            openDatePicker()
        }

        etMenstrualCycle.mask = "999일"
        etMenstrualCycle.inputType = InputType.TYPE_CLASS_NUMBER
        etMenstrualCycle.addTextChangedListener {s ->
            surveyFragment.surveyResults.period_cycle = s.toString().removeChars()
            surveyFragment.updateNextButton()
        }

        etMenstrualPeriod.mask = "999일"
        etMenstrualPeriod.inputType = InputType.TYPE_CLASS_NUMBER
        etMenstrualPeriod.addTextChangedListener {s ->
            surveyFragment.surveyResults.period_term = s.toString().removeChars()
            surveyFragment.updateNextButton()
        }
    }

    private fun setSelectedDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val dateString = "$year-$monthOfYear-$dayOfMonth"
        surveyFragment.surveyResults.period_end_date = dateString
        surveyFragment.updateNextButton()

        tvLastMenstrualPeriod.text = "${year}년 ${monthOfYear}월 ${dayOfMonth}일"
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener() { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                setSelectedDate(year, monthOfYear+1, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }
}