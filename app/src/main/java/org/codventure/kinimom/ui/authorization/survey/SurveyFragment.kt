package org.codventure.kinimom.ui.authorization.survey

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_login_survey.*
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.di.ApplicationComponent
import org.codventure.kinimom.framework.extension.toast
import org.codventure.kinimom.ui.MainActivity
import org.codventure.kinimom.ui.authorization.survey.pages.Page1Fragment
import org.codventure.kinimom.ui.authorization.survey.pages.Page2Fragment
import org.codventure.kinimom.ui.authorization.survey.pages.Page3Fragment
import org.codventure.kinimom.ui.authorization.survey.pages.Page4Fragment

/**
 * Created by abduaziz on 8/12/21 at 3:02 PM.
 */

class SurveyFragment : Fragment(R.layout.fragment_login_survey), SurveyView {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    lateinit var presenter: SurveyPresenter

    val surveyResults = SurveyResults()

    private val page1 = Page1Fragment(this)
    private val page2 = Page2Fragment(this)
    private val page3 = Page3Fragment(this)
    private val page4 = Page4Fragment(this)
    private val pages = listOf(page1, page2, page3, page4)
    private lateinit var indicators: Array<ImageView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SurveyPresenter(this)
        appComponent.inject(presenter)

        indicators = arrayOf(indicator1, indicator2, indicator3, indicator4)

        val adapter = SurveyPagerAdapter(this, pages)

        viewPager.isUserInputEnabled = false // pages can't be scrolled by user
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators()
                updateNextButton()
            }
        })

        tvNext.setOnClickListener {
            onNextClicked()
        }

        ivBack.setOnClickListener {
            onPrevClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        viewPager.currentItem = 0
    }

    private fun updateIndicators() {
        indicators.forEach {
            it.setImageResource(R.drawable.login_btn_topslide_off)
        }
        indicators[viewPager.currentItem].setImageResource(R.drawable.login_btn_topslide_on)
    }

    override fun updateNextButton() {
        if (surveyResults.isPageCompleted(viewPager.currentItem)) {
            tvNext.isEnabled = true
            tvNext.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        } else {
            tvNext.isEnabled = false
            tvNext.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
    }

    private fun onNextClicked() {
        if (viewPager.currentItem + 1 < viewPager.adapter!!.itemCount) {
            viewPager.currentItem = viewPager.currentItem + 1
        } else {
            presenter.saveUserInfo(surveyResults)
        }
    }

    private fun onPrevClicked() {
        if (viewPager.currentItem - 1 >= 0) {
            viewPager.currentItem = viewPager.currentItem - 1
        } else {
            (activity as MainActivity).openSocialLoginScreen()
        }
    }

    override fun showUserUpdateError() {
        toast(getString(R.string.error_internet_connection))
    }

    override fun openMain() {
        (activity as MainActivity).openMainScreen()
    }
}