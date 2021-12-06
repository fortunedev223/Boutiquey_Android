package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import webkul.opencart.mobikul.adapterModel.RightNavAdapterModel
import webkul.opencart.mobikul.CategoryActivity
import webkul.opencart.mobikul.Subcategory
import webkul.opencart.mobikul.databinding.LeftNavLayoutBinding
import webkul.opencart.mobikul.model.SubcategoryModel.SubCategoryModel
import webkul.opencart.mobikul.networkManager.RetrofitCallback

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class LeftNavHandlers {
    private var mcontext: Context? = null
    private var leftNavLayoutBinding: LeftNavLayoutBinding? = null

    constructor(mcontext: Context) {
        this.mcontext = mcontext
    }

    constructor(mcontext: Context, leftNavLayoutBinding: LeftNavLayoutBinding) {
        this.mcontext = mcontext
        this.leftNavLayoutBinding = leftNavLayoutBinding
    }

    fun onClickCategory(view: View, dataholder: RightNavAdapterModel) {
        if (dataholder.childStatus!!) {
            val intent = Intent(mcontext, Subcategory::class.java)
            intent.putExtra("id", dataholder.path)
            intent.putExtra("CATEGORY_NAME",dataholder.categoryName)
            intent.putExtra("image",dataholder.imageUrl)
            intent.putExtra("dominant",dataholder.dominantColor)
            mcontext?.startActivity(intent)

        } else {
            val intent = Intent(mcontext, CategoryActivity::class.java)
            intent.putExtra("ID", dataholder.path)
            intent.putExtra("CATEGORY_NAME", dataholder.categoryName)
            mcontext?.startActivity(intent)
        }
    }
}
