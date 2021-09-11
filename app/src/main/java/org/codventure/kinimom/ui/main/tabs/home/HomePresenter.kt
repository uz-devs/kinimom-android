package org.codventure.kinimom.ui.main.tabs.home

import org.codventure.kinimom.core.interactors.GetBestCommunities
import org.codventure.kinimom.core.interactors.GetTestLastOne
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.uiThread
import org.codventure.kinimom.framework.settings.Settings
import java.lang.Integer.parseInt
import java.util.Calendar
import javax.inject.Inject

class HomePresenter(val view: HomeView) {
    // region injections
    @Inject
    lateinit var getTestLastOne: GetTestLastOne

    @Inject
    lateinit var getBestCommunities: GetBestCommunities

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
}
