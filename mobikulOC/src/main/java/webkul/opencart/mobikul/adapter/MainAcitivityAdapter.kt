package webkul.opencart.mobikul.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.util.ArrayList
import retrofit2.Call
import retrofit2.Response
import webkul.opencart.mobikul.*
import webkul.opencart.mobikul.adapterModel.HomePageAdapteModel
import webkul.opencart.mobikul.handlers.MainActivityHandler
import webkul.opencart.mobikul.helper.Type
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.MainProductSingleViewBinding


class MainAcitivityAdapter(private val context: Context, private val data: ArrayList<HomePageAdapteModel>, private var type: Int) :
        RecyclerView.Adapter<MainAcitivityAdapter.Myholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val inflater = LayoutInflater.from(context)
        val view = DataBindingUtil.inflate<MainProductSingleViewBinding>(inflater, R.layout.main_product_single_view, parent, false)
        return Myholder(view)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        if (position < data.size) {
            val mydata = data[position]
            if (Validation.checkSpecialPrice(mydata.specialPrice)) {
                holder.gridhomeBinding.productprice.textSize = 12f
                holder.gridhomeBinding.productprice.paintFlags = holder.gridhomeBinding.productprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            holder.gridhomeBinding.data = mydata
            holder.gridhomeBinding.productImage.setTag(R.id.productImage, position)
            setScaleAnimation(holder.gridhomeBinding.root)
            holder.gridhomeBinding.executePendingBindings()
            holder.gridhomeBinding.productLayout.visibility = View.VISIBLE
            holder.gridhomeBinding.llViewMore.visibility = View.GONE
            holder.gridhomeBinding.llMain.visibility = View.VISIBLE
        }
        holder.gridhomeBinding.handler = MainActivityHandler(context)
        holder.gridhomeBinding.handler?.setType(type)
        holder.gridhomeBinding.handler?.setDataBinding(holder.gridhomeBinding)
        if (Type.RECENT == type) {
            holder.gridhomeBinding.llViewMore.visibility = View.GONE
        }
        if (type == Type.RECENT) {
            holder.gridhomeBinding.wishlist.visibility = View.GONE
        } else {
            holder.gridhomeBinding.wishlist.visibility = View.VISIBLE
        }
    }

    private fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = 500
        view.startAnimation(anim)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class Myholder(val gridhomeBinding: MainProductSingleViewBinding) : RecyclerView.ViewHolder(gridhomeBinding.root) {
        private val productImage: ImageView
        private val wishlist: ImageView
        private val price: TextView
        private val specialPrice: TextView
        private val homeProductCardView: CardView
        private val llViewMore: LinearLayout
        private val llParent: LinearLayout
        private val llMain: LinearLayout

        init {
            homeProductCardView = gridhomeBinding.productCardv
            productImage = this.gridhomeBinding.productImage
            price = gridhomeBinding.productprice
            specialPrice = gridhomeBinding.specialProductPrice
            wishlist = gridhomeBinding.wishlist
            llViewMore = gridhomeBinding.llViewMore
            llParent = gridhomeBinding.llParent
            llMain = gridhomeBinding.llMain
            if (type == Type.RECENT) {
                val params1 = LinearLayout.LayoutParams(
                        (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2.2).toInt(),
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                homeProductCardView.layoutParams = params1
                params1.setMargins(1, 1, 1, 1)

                val params = FrameLayout.LayoutParams(
                        (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2.2).toInt(),
                        (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2.2).toInt())
                productImage.layoutParams = params
                productImage.setPadding(5, 5, 5, 5)
            } else {
                val params = FrameLayout.LayoutParams(
                        (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2).toInt(),
                        (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2).toInt())
                productImage.layoutParams = params
            }

        }
    }
}
