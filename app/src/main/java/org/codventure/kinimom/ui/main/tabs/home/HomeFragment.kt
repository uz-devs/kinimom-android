package org.codventure.kinimom.ui.main.tabs.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_survey_3.*
import kotlinx.android.synthetic.main.fragment_main_home.*
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.di.ApplicationComponent
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * Created by abduaziz on 8/28/21 at 4:27 PM.
 */

class HomeFragment : Fragment(R.layout.fragment_main_home), HomeView {
    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }
    private lateinit var presenter: HomePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HomePresenter(this)
        appComponent.inject(presenter)

        presenter.initDates()
        presenter.fetchLastScores()
    }

    override fun setHusbandWifeScores(husbandScore: Int, wifeScore: Int) {
        // total score (middle tv)
        val scoreDrawableIds = arrayOf(
            R.drawable.home_ready_img_main_graph_01,
            R.drawable.home_ready_img_main_graph_02,
            R.drawable.home_ready_img_main_graph_03,
            R.drawable.home_ready_img_main_graph_04,
            R.drawable.home_ready_img_main_graph_05,
            R.drawable.home_ready_img_main_graph_06,
            R.drawable.home_ready_img_main_graph_07,
            R.drawable.home_ready_img_main_graph_08,
            R.drawable.home_ready_img_main_graph_09,
            R.drawable.home_ready_img_main_graph_10
        )
        val totalScore = (husbandScore + wifeScore).toFloat() / 2
        if (totalScore <= 5)
            tvScore.setTextColor(Color.parseColor("#9b82c1"))
        tvScore.text = "${min(90, max(10, totalScore.toInt() * 10))}%"
        ivScore.setImageResource(scoreDrawableIds[min(9, totalScore.toInt())])
        tvScoreAbove9.visibility = if (totalScore > 9) View.VISIBLE else View.INVISIBLE

        // wife score (left)
        val wifeScoreDrawableIds = arrayOf(
            R.drawable.wife_progress_0,
            R.drawable.wife_progress_1,
            R.drawable.wife_progress_2,
            R.drawable.wife_progress_3,
            R.drawable.wife_progress_4,
            R.drawable.wife_progress_5,
            R.drawable.wife_progress_6,
            R.drawable.wife_progress_7,
            R.drawable.wife_progress_8,
            R.drawable.wife_progress_9
        )
        ivWifeScore.setImageResource(wifeScoreDrawableIds[min(9, max(0, wifeScore))])

        // husband score (right)
        val husbandScoreDrawableIds = arrayOf(
            R.drawable.husband_progress_0,
            R.drawable.husband_progress_1,
            R.drawable.husband_progress_2,
            R.drawable.husband_progress_3,
            R.drawable.husband_progress_4,
            R.drawable.husband_progress_5,
            R.drawable.husband_progress_6,
            R.drawable.husband_progress_7,
            R.drawable.husband_progress_8,
            R.drawable.husband_progress_9
        )
        ivHusbandScore.setImageResource(husbandScoreDrawableIds[min(9, max(0, husbandScore))])
    }

    override fun setHusbandWifeResults(husbandResult: String, wifeResult: String) {
        tvHusbandResults.text = husbandResult
        tvWifeResults.text = wifeResult
    }

    override fun setMonth(year: Int, month: Int) {
        tvMonth.text = getString(R.string.month_format, year, month)
    }

    override fun setWeekdays(weekdays: ArrayList<Pair<Int, Int>>) {
        val weekdaysStr = resources.getStringArray(R.array.weekdays)
        llWeekView.children.forEachIndexed { i, v ->
            val p = v as ViewGroup
            val t1 = p.getChildAt(0) as TextView
            val t2 = p.getChildAt(1)

            t1.text = weekdaysStr[weekdays[i].first]
            if (t2 is TextView)
                t2.text = getString(R.string.date_format, weekdays[i].second)
        }
    }
}