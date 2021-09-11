package org.codventure.kinimom.ui.main.tabs.home

import org.codventure.kinimom.core.interactors.GetBestCommunities
import org.codventure.kinimom.core.interactors.GetMenstruation
import org.codventure.kinimom.core.interactors.GetTestLastOne
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import org.codventure.kinimom.framework.settings.Settings
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class HomePresenter(val view: HomeView) {
    // region injections
    @Inject
    lateinit var getTestLastOne: GetTestLastOne

    @Inject
    lateinit var getBestCommunities: GetBestCommunities

    @Inject
    lateinit var getMenstruation: GetMenstruation

    @Inject
    lateinit var settings: Settings
    // endregion

    fun initDates() {
        val cal = Calendar.getInstance()
        view.setMonth(year = cal.get(Calendar.YEAR), month = cal.get(Calendar.MONTH))

        cal.add(Calendar.DAY_OF_MONTH, -3)
        val weekdays = arrayListOf<Pair<Int, Int>>()
        for (i in 0 until 7) {
            weekdays.add(Pair(cal.get(Calendar.DAY_OF_WEEK) - 1, cal.get(Calendar.DAY_OF_MONTH)))
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
        view.setWeekdays(weekdays)
    }

    fun fetchLastScores() {
        doAsync {
            val res = getTestLastOne(settings.getUserId())
            uiThread {
                if (res?.wife_test_last_one?.isNotEmpty() == true && res.husband_test_last_one.isNotEmpty())
                    view.setHusbandWifeScores(
                        husbandScore = parseInt(res.husband_test_last_one[0].score!!),
                        wifeScore = parseInt(res.wife_test_last_one[0].love!!)
                    )
            }
        }
    }

    fun fetchBestCommunities() {
        doAsync {
            val res = getBestCommunities(settings.getUserId())
            uiThread { res?.community_list?.let { if (it.isNotEmpty()) view.setCommunities(communities = it) } }
        }
    }

    fun fetchMenstruation() {
        doAsync {
            val cal = Calendar.getInstance()
            val today = cal.time.clone() as Date
            cal.add(Calendar.YEAR, 1)
            var closest: Pair<Date, Date>? = null

            val date = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(today)
            val menstruation = getMenstruation(settings.getUserId(), date)

            if (menstruation?.current_month_menstruation?.isNotEmpty() == true) {
                for (x in menstruation.current_month_menstruation)
                    if (x.menstruation_start != null && x.menstruation_end != null) {
                        val start = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(x.menstruation_start)
                        val end = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(x.menstruation_end)
                        if (start != null && end != null)
                            if (closest == null)
                                closest = Pair<Date, Date>(start, end)
                            else if (start.after(today) && start.before(closest.first))
                                closest = Pair<Date, Date>(start, end)
                    }
            } else if (menstruation?.next_month_menstruation?.isNotEmpty() == true)
                for (x in menstruation.next_month_menstruation)
                    if (x.menstruation_start != null && x.menstruation_end != null) {
                        val start = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(x.menstruation_start)
                        val end = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(x.menstruation_end)
                        if (start != null && end != null && start.after(today))
                            if (closest == null)
                                closest = Pair<Date, Date>(start, end)
                            else if (start.before(closest.first))
                                closest = Pair<Date, Date>(start, end)
                    }

            if (closest != null) {
                val startStr = SimpleDateFormat("yy.MM.dd", Locale.getDefault()).format(closest.first)
                val endStr = SimpleDateFormat("yy.MM.dd", Locale.getDefault()).format(closest.second)
                uiThread { view.setMenstruation("가임가 $startStr~$endStr") }
            }
        }
    }
}
