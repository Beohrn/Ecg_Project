package com.alex.ecg_project.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.alex.ecg_project.R
import com.alex.ecg_project.getDate
import com.alex.ecg_project.models.Points
import kotlinx.android.synthetic.main.list_item.view.*

class ListItemView : FrameLayout {

  constructor(context: Context?) : super(context) {
    init(context)
  }

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    init(context)
  }

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init(context)
  }

  private fun init(context: Context?) = View.inflate(context, R.layout.list_item, this)

  fun setData(points: Points?) {
    points?.let {
      date_text.text = getDate(it.date)
      location.text = it.address
    }
  }
}