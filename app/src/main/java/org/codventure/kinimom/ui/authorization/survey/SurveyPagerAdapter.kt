package org.codventure.kinimom.ui.authorization.survey

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.codventure.kinimom.ui.authorization.survey.pages.Page1Fragment
import org.codventure.kinimom.ui.authorization.survey.pages.Page2Fragment
import org.codventure.kinimom.ui.authorization.survey.pages.Page3Fragment
import org.codventure.kinimom.ui.authorization.survey.pages.Page4Fragment

/**
 * Created by abduaziz on 8/13/21 at 9:08 PM.
 */

class SurveyPagerAdapter(fragment: Fragment, val pages: List<Fragment>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }
}