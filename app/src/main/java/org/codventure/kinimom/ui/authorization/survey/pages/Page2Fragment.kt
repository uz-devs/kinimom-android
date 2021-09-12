package org.codventure.kinimom.ui.authorization.survey.pages

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_survey_2.*
import org.codventure.kinimom.R
import org.codventure.kinimom.ui.authorization.survey.SurveyFragment
import java.util.*


class Page2Fragment(val surveyFragment: SurveyFragment) :
    Fragment(R.layout.fragment_login_survey_2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set date to today
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        tvExpectedDate.hint = "예시 : ${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH)}월 ${calendar.get(Calendar.DAY_OF_MONTH)}일"

        tvExpectedDate.setOnClickListener {
            openDatePicker()
        }
    }

    private fun setSelectedDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val dateString = "$year-$monthOfYear-$dayOfMonth"
        surveyFragment.surveyResults.expected_date = dateString
        surveyFragment.updateNextButton()

        tvExpectedDate.text = "${year}년 ${monthOfYear}월 ${dayOfMonth}일"
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireContext(),
            android.app.AlertDialog.THEME_HOLO_LIGHT,
            DatePickerDialog.OnDateSetListener() { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                setSelectedDate(year, monthOfYear+1, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.setOnShowListener {
            dialog.getButton(Dialog.BUTTON_NEGATIVE).apply {
                (layoutParams as LinearLayout.LayoutParams).weight = 0f
                visibility = View.GONE
            }
            dialog.getButton(Dialog.BUTTON_POSITIVE).apply {
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                text = getString(R.string.confirm)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
        dialog.show()
    }
}