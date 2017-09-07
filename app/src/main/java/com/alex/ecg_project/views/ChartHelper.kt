package com.alex.ecg_project.views

import android.graphics.Color
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class ChartHelper(val lineChart: LineChart) {

  fun init() {
    with(lineChart) {
      description = Description().also { it.text = "" }
      setTouchEnabled(true)
      isDragEnabled = true
      setScaleEnabled(true)
      setPinchZoom(false)
      setBackgroundColor(Color.rgb(235, 242, 208))

      // yAxis
      axisRight.isEnabled = false
      axisLeft.textSize = 12f

      xAxis.isEnabled = true
      xAxis.setDrawGridLines(false)

      data = LineData()
      invalidate()
    }
  }

  fun addEntry(value: Int) {
    lineChart.data?.let { data ->

      var dataSet = data.getDataSetByIndex(0)

      if (dataSet == null) {
        dataSet = createSet()
        data.addDataSet(dataSet)
      }

      data.addEntry(Entry(dataSet.entryCount.toFloat(), value.toFloat()), 0)
      data.notifyDataChanged()

      lineChart.isAutoScaleMinMaxEnabled = !lineChart.isAutoScaleMinMaxEnabled
      lineChart.notifyDataSetChanged()
      lineChart.setVisibleXRangeMaximum(1000F)

      lineChart.moveViewToX(data.entryCount.toFloat() - 100)
    }
  }

  private fun createSet(yVals: List<Entry>? = null): ILineDataSet {
    val set = LineDataSet(yVals, "")
    set.axisDependency = YAxis.AxisDependency.LEFT
    set.color = ColorTemplate.getHoloBlue()
    set.setCircleColor(Color.WHITE)
    set.lineWidth = 1f
    set.circleRadius = 2f
    set.fillAlpha = 65
    set.fillColor = ColorTemplate.getHoloBlue()
    set.highLightColor = Color.rgb(244, 117, 117)
    set.valueTextColor = Color.WHITE
    set.valueTextSize = 9f
    set.setDrawValues(false)
    return set
  }
}