package org.codventure.kinimom.ui.authorization.survey.pages

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_survey_3.*
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.di.ApplicationComponent
import org.codventure.kinimom.framework.extension.toast
import org.codventure.kinimom.ui.MainActivity
import org.codventure.kinimom.ui.authorization.survey.SurveyFragment
import java.util.*

/**
 * Created by abduaziz on 8/13/21 at 9:04 PM.
 */

class Page3Fragment(val surveyFragment: SurveyFragment, val isPreparing: Boolean = false) :
    Fragment(R.layout.fragment_login_survey_3), Page3View {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }
    private lateinit var presenter: Page3Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = Page3Presenter(this)
        appComponent.inject(presenter)

        if (isPreparing){
            tvSurveyPage3Title.text = getString(R.string.you_are_preparing_for_pregnancy)
            tvSurveyPage3TitleHint.visibility = View.VISIBLE
        }else{
            tvSurveyPage3Title.text = getString(R.string.give_a_birth_to_a_healthy_baby)
            tvSurveyPage3TitleHint.visibility = View.GONE
        }

        etNickname.addTextChangedListener { s ->
            presenter.validateNickname(s.toString())
        }

        tvCheckNickname.setOnClickListener {
            presenter.checkUserNickname(etNickname.text.toString())
        }

        tvDateOfBirth.setOnClickListener {
            openDatePicker()
        }

        tvMan.setOnClickListener {
            selectGenderMan()
        }

        tvWoman.setOnClickListener {
            selectGenderWoman()
        }
    }

    override fun showInternetConnectionError() {
        toast(getString(R.string.error_internet_connection))
    }

    override fun disableCheckNicknameButton() {
        llCheckNickname.setBackgroundResource(R.drawable.login_button_background)
        tvCheckNickname.text = getString(R.string.double_check)
        tvCheckNickname.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
    }

    override fun enableCheckNickameButton() {
        llCheckNickname.setBackgroundResource(R.drawable.login_button_background_enabled)
        tvCheckNickname.text = getString(R.string.double_check)
        tvCheckNickname.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
    }

    override fun showValidNickname() {
        surveyFragment.surveyResults.nickname = ""
        surveyFragment.updateNextButton()

        tvCheckNicknameStatus.text = ""
        tvCheckNicknameStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
    }

    override fun showInvalidNicknameError() {
        surveyFragment.surveyResults.nickname = ""
        surveyFragment.updateNextButton()

        tvCheckNicknameStatus.text = getString(R.string.error_user_nickname_contraints)
        tvCheckNicknameStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
    }

    override fun showNicknameUnavailable(invalidNickname: String) {
        etNickname.setText(invalidNickname)
        (activity as MainActivity).hideSoftInput()

        surveyFragment.surveyResults.nickname = ""
        surveyFragment.updateNextButton()

        tvCheckNicknameStatus.text = getString(R.string.error_unavailable_nickname)
        tvCheckNicknameStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        disableCheckNicknameButton()
    }

    override fun showNicknameAvailable(validNickname: String) {
        etNickname.setText(validNickname)
        (activity as MainActivity).hideSoftInput()

        surveyFragment.surveyResults.nickname = validNickname
        surveyFragment.updateNextButton()

        tvCheckNicknameStatus.text = getString(R.string.available_nickname)
        tvCheckNicknameStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

        disableCheckNicknameButton()
        tvCheckNickname.text = getString(R.string.available)
    }

    private fun setSelectedDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val dateString = "$year-$monthOfYear-$dayOfMonth"
        surveyFragment.surveyResults.date_of_birth = dateString
        surveyFragment.updateNextButton()

        tvDateOfBirth.text = "${year}년 ${monthOfYear}월 ${dayOfMonth}일"
        tvDateOfBirth.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
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

    private fun selectGenderMan() {
        surveyFragment.surveyResults.gender = "m"
        surveyFragment.updateNextButton()

        llMan.setBackgroundResource(R.drawable.login_button_background_enabled)
        tvMan.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        llWoman.setBackgroundResource(R.drawable.login_button_background)
        tvWoman.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
    }

    private fun selectGenderWoman() {
        surveyFragment.surveyResults.gender = "f"
        surveyFragment.updateNextButton()

        llMan.setBackgroundResource(R.drawable.login_button_background)
        tvMan.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

        llWoman.setBackgroundResource(R.drawable.login_button_background_enabled)
        tvWoman.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
    }
}