package com.alex.ecg_project.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.alex.ecg_project.adapters.ListAdapter.ItemViewHolder
import com.alex.ecg_project.models.Points
import com.alex.ecg_project.views.ListItemView
import org.jetbrains.anko.onClick

class ListAdapter(val context: Context, val listener: OnItemClickListener): RecyclerView.Adapter<ItemViewHolder>() {

  private val list = arrayListOf<Points>()

  fun addAll(items: List<Points>) {
    list.clear()
    list.addAll(items)
    notifyDataSetChanged()
  }

  init { setHasStableIds(true) }

  override fun getItemCount() = list.size

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ItemViewHolder(ListItemView(context))

  override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) =
      (holder?.itemView as ListItemView).setData(list[position])

  override fun getItemId(position: Int) = list[position].date

  inner class ItemViewHolder(itemView: ListItemView): RecyclerView.ViewHolder(itemView) {
    init { itemView.onClick { listener.onItemClick(list[adapterPosition].date) } }
  }

  interface OnItemClickListener {
    fun onItemClick(date: Long)
  }
}