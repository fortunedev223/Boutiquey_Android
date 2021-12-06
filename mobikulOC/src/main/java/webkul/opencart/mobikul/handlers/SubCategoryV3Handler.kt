package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.view.View

import webkul.opencart.mobikul.adapterModel.SubCategoryV3ThemeAdapterModel
import webkul.opencart.mobikul.CategoryActivity
import webkul.opencart.mobikul.Subcategory


class SubCategoryV3Handler(private val mContext: Context) {

    fun onClickCategory(view: View, model: SubCategoryV3ThemeAdapterModel) {
        if (model.childStatus!!) {
            val intent = Intent(mContext, Subcategory::class.java)
            intent.putExtra("id", model.path)
            intent.putExtra("CATEGORY_NAME", model.categoryName)
            intent.putExtra("image",model.imageUrl)
            intent.putExtra("dominant",model.dominantColor)
            mContext.startActivity(intent)
        } else {
            val intent = Intent(mContext, CategoryActivity::class.java)
            intent.putExtra("ID", model.path)
            intent.putExtra("CATEGORY_NAME", model.categoryName)
            mContext.startActivity(intent)
        }
    }
}
