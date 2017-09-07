package com.alex.ecg_project.ui.list_screen

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.alex.ecg_project.R
import com.alex.ecg_project.adapters.ListAdapter
import com.alex.ecg_project.models.Points
import com.alex.ecg_project.ui.BaseActivity
import com.alex.ecg_project.ui.data_screen.DataActivity
import kotlinx.android.synthetic.main.activity_list_points.*
import org.jetbrains.anko.startActivity

class ListActivity: BaseActivity(), ListScreenContract.View, ListAdapter.OnItemClickListener {

  override var layoutId = R.layout.activity_list_points

  private lateinit var adapter: ListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setSupportActionBar(list_toolbar)
    with(supportActionBar!!) {
      setHomeButtonEnabled(true)
      setDisplayHomeAsUpEnabled(true)
      title = "Points"
    }
    list_toolbar.setTitleTextColor(Color.WHITE)
    list_toolbar.setNavigationOnClickListener { finish() }

    adapter = ListAdapter(this, this)
    list.layoutManager = LinearLayoutManager(this)
    list.adapter = adapter
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.list_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.remove_action -> listPresenter.removeData()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onResume() {
    super.onResume()
    listPresenter.subscribe(this)
  }

  override fun showData(points: List<Points>) {
    adapter.addAll(points)
  }

  override fun showError(throwable: Throwable) {
  }

  override fun onItemClick(date: Long) = startActivity<DataActivity>(DataActivity.DATE_KEY to date)

  override fun onDestroy() {
    listPresenter.unsubscribe()
    super.onDestroy()
  }
}