package org.codventure.kinimom.ui.authorization.survey.pages

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_survey_2.*
import org.codventure.kinimom.R
import org.codventure.kinimom.ui.authorization.survey.SurveyFragment
import java.util.*

/**
 * Created by abduaziz on 8/13/21 at 9:03 PM.
 */

class Page2Fragment(val surveyFragment: SurveyFragment) :
    Fragment(R.layout.fragment_login_survey_2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvExpectedDate.setOnClickListener {
            openDatePicker()
        }
    }

    private fun setSelectedDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val dateString = "$year-$monthOfYear-$dayOfMonth"
        surveyFragment.surveyResults.expected_date = dateString
        surveyFragment.updateNextButton()

        tvExpectedDate.text = "${year}년 ${monthOfYear}월 ${dayOfMonth}일"
        tvExpectedDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener() { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                setSelectedDate(year, monthOfYear, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }
}