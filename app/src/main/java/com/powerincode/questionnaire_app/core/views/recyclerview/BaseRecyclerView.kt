package com.powerincode.questionnaire_app.core.views.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by powerman23rus on 26/02/2019.
 */
abstract class BaseRecyclerView<T, T2: BaseRecyclerView.OnItemClick<T>> : RecyclerView.Adapter<BaseRecyclerView<T, T2>.BaseViewHolder>() {
    private var listener : T2? = null
    private var data : List<T> = emptyList()
    private var selectedItem : T? = null

    override fun getItemCount() : Int = data.size
    override fun onBindViewHolder(p0 : BaseViewHolder, p1 : Int) {
        p0.bind(data[p1])
    }

    fun setRecycleListener(listener : T2) {
        this.listener = listener
    }

    protected fun onItemClick(position : Int, item : T?) {
        item ?: return

        if (selectedItem == null) {
            selectedItem = item
        } else if(selectedItem == item) {
            selectedItem = null
        } else {
            notifyItemChanged(data.indexOfFirst { it == selectedItem })
            selectedItem = item
        }

        notifyItemChanged(position)

        listener?.onClick(this, position, item)
    }

    fun swapData(data : List<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    abstract inner class BaseViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        protected var item : T? = null

        init {
            view.setOnClickListener {
                this@BaseRecyclerView.onItemClick(adapterPosition, item)
            }
        }

        open fun bind(item : T) {
            this.item = item

            onSelectionChange(item == selectedItem)
        }

        protected open fun onSelectionChange(isSelected : Boolean) {

        }

    }

    interface OnItemClick<T2> {
        fun onClick(sender : BaseRecyclerView<*, *>, position : Int, item : T2)
    }
}