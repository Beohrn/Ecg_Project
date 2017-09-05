package com.alex.ecg_project.ui.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.alex.ecg_project.ERROR_VALUE
import com.alex.ecg_project.GENERATED_VALUE
import com.alex.ecg_project.R
import com.alex.ecg_project.RANDOM_ACTION
import com.alex.ecg_project.services.RandomService
import com.alex.ecg_project.ui.BaseActivity
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), Contract.MainActivityView {

  override var layoutId = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val randomService = Intent(applicationContext, RandomService::class.java)
    startService(randomService)

    start_button.onClick {
      val intentFilter = IntentFilter()
      intentFilter.addAction(RANDOM_ACTION)
      LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    stop_button.onClick { LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver) }

    with(line_chart) {
      description = Description().also { it.text = "" }
      setTouchEnabled(true)
      isDragEnabled = true
      setScaleEnabled(true)
      setPinchZoom(true)
      setBackgroundColor(Color.rgb(235, 242, 208))
//      setViewPortOffsets(60f, 0f, 60f, 50f)

      xAxis.setDrawGridLines(false)
      isDragEnabled = true
      setScaleEnabled(true)
      setPinchZoom(true)
      xAxis.isEnabled = false
      data = LineData()
      invalidate()
    }
  }

  fun addEntry(value: Int) {
    line_chart.data?.let {
      var dataSet = it.getDataSetByIndex(0)

      if (dataSet == null) {
        dataSet = createSet()
        it.addDataSet(dataSet)
      }

      it.addEntry(Entry(dataSet.entryCount.toFloat(), value.toFloat()), 0)
      it.notifyDataChanged()
      line_chart.isAutoScaleMinMaxEnabled = !line_chart.isAutoScaleMinMaxEnabled
      line_chart.notifyDataSetChanged()
      line_chart.setVisibleXRangeMaximum(500F)
      line_chart.moveViewToX(it.entryCount.toFloat())
    }
  }

  override fun onDestroy() {
//    presenter.unsubscribe()
    LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    super.onDestroy()
  }

  private fun createSet(): ILineDataSet? {
    val set = LineDataSet(null, "")
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

  override fun showData(value: Int) {
    addEntry(value)
    Log.d("MainActivity", "$value")
  }

  override fun showError(throwable: Throwable) {
    toast(throwable.message!!)
  }

  private val broadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      intent?.let {
        if (it.action == RANDOM_ACTION) {
          val number = it.getIntExtra(GENERATED_VALUE, -1)
          addEntry(number)
          Log.d("MainActivity", "$number")
        } else {
          val error = it.getStringExtra(ERROR_VALUE)
          Log.d("MainActivity", error)
        }
      }
    }
  }

}
