package org.codventure.kinimom.ui.main.tabs.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main_home.*
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.di.ApplicationComponent
import java.util.*

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
        val husbandAngle = (husbandScore.toFloat() / 10) * 175
        val wifeAngle = (wifeScore.toFloat() / 10) * 175

        pbWife.progress = wifeAngle.toInt()
        pbWife.rotation = 90f
        pbHusband.progress = husbandAngle.toInt()
        pbHusband.rotation = 90f - husbandAngle
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