package org.codventure.kinimom.ui.main.tabs.home

import org.codventure.kinimom.core.domain.Community
import java.util.ArrayList

interface HomeView {
    fun setHusbandWifeScores(husbandScore: Int, wifeScore: Int)
    fun setHusbandWifeResults(husbandResult: String, wifeResult: String)

    fun setMonth(year: Int, month: Int)
    fun setWeekdays(weekdays: ArrayList<Pair<Int, Int>>)

    fun setCommunities(communities: ArrayList<Community>)
    fun setMenstruation(menstruation: String)
}