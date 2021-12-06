package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent

import webkul.opencart.mobikul.adapterModel.HomePageBannerAdapterModel
import webkul.opencart.mobikul.CategoryActivity
import webkul.opencart.mobikul.ViewProductSimple


class HomePagePagerHandler(private val mcontext: Context) {

    fun onClickImage(dataHolder: HomePageBannerAdapterModel) {
        if (dataHolder.type == "product") {
            val intent = Intent(mcontext, ViewProductSimple::class.java)
            intent.putExtra("idOfProduct", dataHolder.link)
            intent.putExtra("nameOfProduct", dataHolder.title)
            mcontext.startActivity(intent)

        } else if (dataHolder.type == "category") {
            val intent = Intent(mcontext, CategoryActivity::class.java)
            intent.putExtra("ID", dataHolder.link)
            intent.putExtra("CATEGORY_NAME", dataHolder.title)
            mcontext.startActivity(intent)
        }
    }
}
