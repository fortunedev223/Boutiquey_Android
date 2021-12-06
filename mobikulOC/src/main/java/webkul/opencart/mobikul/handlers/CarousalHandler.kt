package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent

import webkul.opencart.mobikul.adapterModel.CarousalAdapterModel
import webkul.opencart.mobikul.CategoryActivity

/**
 * Created by manish.choudhary on 14/8/17.
 */

class CarousalHandler(private val mcontext: Context) {

    fun onClickCarousal(carousalAdapterModel: CarousalAdapterModel) {
        val intent = Intent(mcontext, CategoryActivity::class.java)
        intent.putExtra("manufacturer_id", carousalAdapterModel.link)
        intent.putExtra("imageTitle", carousalAdapterModel.title)
        intent.putExtra("drawerData", "")
        mcontext.startActivity(intent)
    }
}
