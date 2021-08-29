package org.codventure.kinimom.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*
import org.codventure.kinimom.R
import org.codventure.kinimom.ui.main.tabs.CommunityFragment
import org.codventure.kinimom.ui.main.tabs.DailyFragment
import org.codventure.kinimom.ui.main.tabs.home.HomeFragment
import org.codventure.kinimom.ui.main.tabs.SettingsFragment

/**
 * Created by abduaziz on 8/13/21 at 11:37 AM.
 */

class MainFragment : Fragment(R.layout.fragment_main), BottomNavigationView.OnNavigationItemSelectedListener{
    private val homeFragment = HomeFragment()
    private val dailyFragment = DailyFragment()
    private val communityFragment = CommunityFragment()
    private val settingsFragment = SettingsFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavBar.setOnNavigationItemSelectedListener(this)
        selectTab(homeFragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home -> {
                selectTab(homeFragment)
            }
            R.id.menu_daily -> {
                selectTab(dailyFragment)
            }
            R.id.menu_community -> {
                selectTab(communityFragment)
            }
            R.id.menu_settings -> {
                selectTab(settingsFragment)
            }
        }
        return true
    }

    private fun selectTab(fragment: Fragment){
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_main_tab_container, fragment)
        }
    }
}