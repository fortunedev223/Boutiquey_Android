package webkul.opencart.mobikul.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import webkul.opencart.mobikul.handlers.SubCategoryGridHandler
import webkul.opencart.mobikul.model.SubCategoryGrid.SubCategoryGridModel

import webkul.opencart.mobikul.databinding.SubCategoryGridBinding
import webkul.opencart.mobikul.model.SubcategoryModel.Category
import java.util.ArrayList

class SubCategoryGridAdapter(val categories: ArrayList<Category>, val activity: Activity) : RecyclerView.Adapter<SubCategoryGridAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val subCategoryGridBinding = SubCategoryGridBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(subCategoryGridBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.subCategoryGridBinding.data = categories.get(position)
        holder.rlParent.setTag(position)
        holder.subCategoryGridBinding.handler = SubCategoryGridHandler(activity)
        holder.subCategoryGridBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class MyViewHolder(internal var subCategoryGridBinding: SubCategoryGridBinding) : RecyclerView.ViewHolder(subCategoryGridBinding.root) {
        internal var subcategoryTxt: TextView
        internal var subCategoryImage: ImageView
        internal var subcategoryLayout: LinearLayout
        internal var rlParent: RelativeLayout

        init {
            subCategoryImage = subCategoryGridBinding.subCategoryImage
            subcategoryTxt = subCategoryGridBinding.subcategoryTxt
            subcategoryLayout = subCategoryGridBinding.subcategoryLayout
            rlParent = subCategoryGridBinding.rlParent
            val params = RelativeLayout.LayoutParams(
                    (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2),
                    (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 3))

            val paramsMain = FrameLayout.LayoutParams(
                    (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2),
                    (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 3))
            subCategoryImage.layoutParams = params
            rlParent.layoutParams = paramsMain
        }
    }
}
