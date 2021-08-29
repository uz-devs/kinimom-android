package org.codventure.kinimom.ui.main.tabs.home

import java.util.ArrayList

interface HomeView {
    fun setHusbandWifeScores(husbandScore: Int, wifeScore: Int)
    fun setHusbandWifeResults(husbandResult: String, wifeResult: String)

    fun setMonth(year: Int, month: Int)
    fun setWeekdays(weekdays: ArrayList<Pair<Int, Int>>)
}