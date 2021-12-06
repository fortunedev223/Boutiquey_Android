package webkul.opencart.mobikul.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import webkul.opencart.mobikul.databinding.SearchProductBinding



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CustomAutoCompleteAdapter(context: Context, val layout: Int, var list: ArrayList<String>?) :
        ArrayAdapter<String>(context, layout, list) {

    private val listFilter = ListFilter()
    private var dataListAllItems: List<String>? = null

    override fun getCount(): Int {
        return list?.size!!
    }

    override fun getItem(position: Int): String {
        return list!![position]
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val tempView: SearchProductBinding? = if (view == null) {
            DataBindingUtil.inflate(
                    LayoutInflater.from(parent?.context), layout, parent, false
            )
        } else {
            DataBindingUtil.bind(view)
        }

        return tempView?.root!!
    }


    override fun getFilter(): Filter {
        return listFilter
    }


    inner class ListFilter : Filter() {
        private val lock = Any()

        override fun performFiltering(prefix: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()
            if (dataListAllItems == null) {
                synchronized(lock) {
                    dataListAllItems = ArrayList<String>(list)
                }
            }

            if (prefix == null || prefix.isEmpty()) {
                synchronized(lock) {
                    results.values = dataListAllItems
                    results.count = dataListAllItems?.size!!
                }
            } else {
                val searchStrLowerCase = prefix.toString().toLowerCase()

                val matchValues = ArrayList<String>()

                for (dataItem in dataListAllItems!!) {
                    if (dataItem.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem)
                    }
                }

                results.values = matchValues
                results.count = matchValues.size
            }

            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            list = if (results.values != null) {
                results.values as ArrayList<String>
            } else {
                null
            }
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

    }
}