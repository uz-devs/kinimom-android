package org.codventure.kinimom.ui.main.tabs

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import kotlinx.android.synthetic.main.fragment_main_daily.*
import org.codventure.kinimom.R

class DailyFragment : Fragment(R.layout.fragment_main_daily) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radarChart.description.setEnabled(false)
        radarChart.webLineWidth = 1f
        radarChart.webColor = Color.LTGRAY
        radarChart.webLineWidthInner = 1f
        radarChart.webColorInner = Color.LTGRAY
        radarChart.webAlpha = 100

        setData()

        radarChart.animateXY(1400, 1400, Easing.EaseInOutQuad)

        val xAxis: XAxis = radarChart.getXAxis()
        xAxis.textSize = 9f
        xAxis.axisMinimum = 1F
        xAxis.axisMaximum = 5F
        xAxis.valueFormatter = IndexAxisValueFormatter(listOf("1", "2", "3", "4", "5"))
        xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.black)

        val yAxis: YAxis = radarChart.yAxis
        yAxis.setLabelCount(5, false)
        yAxis.textSize = 9f
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 5f
        yAxis.setDrawLabels(false)

        val l: Legend = radarChart.legend
        l.isEnabled = false
    }

    private fun setData() {
        val entries1 = arrayListOf(
            RadarEntry(2F),
            RadarEntry(3F),
            RadarEntry(1F),
            RadarEntry(4F),
            RadarEntry(5F)
        )

        val set1 = RadarDataSet(entries1, "Label smth")
        set1.color = ContextCompat.getColor(requireContext(), R.color.red)
        set1.fillColor = ContextCompat.getColor(requireContext(), R.color.red)
        set1.setDrawFilled(true)
        set1.fillAlpha = 180
        set1.lineWidth = 2f
        set1.setDrawValues(true)

        val sets: ArrayList<IRadarDataSet> = ArrayList()
        sets.add(set1)

        val data = RadarData(sets)
        data.setValueTextSize(8f)
        data.setDrawValues(true)
        data.setValueTextColor(Color.CYAN)

        radarChart.data = data
        radarChart.invalidate()
    }
}