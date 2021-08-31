package org.codventure.kinimom.ui.authorization.survey.pages

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
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

class Page3Fragment(val surveyFragment: SurveyFragment) :
    Fragment(R.layout.fragment_login_survey_3), Page3View {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }
    private lateinit var presenter: Page3Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = Page3Presenter(this)
        appComponent.inject(presenter)

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
        tvCheckNicknameStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
    }

    override fun showNicknameUnavailable(invalidNickname: String) {
        etNickname.setText(invalidNickname)
        (activity as MainActivity).hideSoftInput()

        surveyFragment.surveyResults.nickname = ""
        surveyFragment.updateNextButton()

        tvCheckNicknameStatus.text = getString(R.string.error_user_nickname_contraints)
        tvCheckNicknameStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
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
            DatePickerDialog.OnDateSetListener() { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                setSelectedDate(year, monthOfYear, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
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