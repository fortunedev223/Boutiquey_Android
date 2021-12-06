package webkul.opencart.mobikul

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import webkul.opencart.mobikul.adapter.SubCategoryGridAdapter
import webkul.opencart.mobikul.databinding.SubcategoryFragmentListBinding
import webkul.opencart.mobikul.helper.setGridLayoutManager
import java.util.ArrayList
import webkul.opencart.mobikul.model.SubcategoryModel.Category


class SubcateryFragment : Fragment() {
    private var mContext: Subcategory? = null
    lateinit var binding: SubcategoryFragmentListBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SubcategoryFragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = binding.recyclerview
        if (isAdded) {
            recyclerView.setGridLayoutManager(activity!!, 2)
        }
        mContext = activity as Subcategory?
        if (isAdded) {
            recyclerView.adapter = SubCategoryGridAdapter(categories!!, activity!!)
        }
        recyclerView.isSelected = true
    }


    companion object {
        var categories: ArrayList<Category>? = null
        fun newInstance(categories: ArrayList<Category>): SubcateryFragment {
            this.categories = categories
            return SubcateryFragment()
        }

    }

}
