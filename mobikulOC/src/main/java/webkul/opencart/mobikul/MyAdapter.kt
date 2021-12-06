package webkul.opencart.mobikul

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Paint
import android.graphics.Point
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.facebook.shimmer.ShimmerFrameLayout

import webkul.opencart.mobikul.handlers.CategoryActivityHandler
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.analytics.MobikulApplication
import webkul.opencart.mobikul.databinding.ItemProductGridViewBinding
import webkul.opencart.mobikul.databinding.ItemProductListViewBinding

class MyAdapter(val mContext: Activity,
                val mDataset: MutableList<Product>,
                val mMobikulApplication: MobikulApplication) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    /**
     * @return get layout type of item
     */
    var viewType: Int = 0

    private val screenWidth: Int
        get() {
            if (!(mContext as Activity).isFinishing) {
                val display = mContext.windowManager.defaultDisplay
                val size = Point()
                display.getSize(size)
                return size.x
            }
            return Utils.getDeviceScreenWidth()
        }

    init {
        val categoryViewShared = mContext.getSharedPreferences("categoryView", Context.MODE_PRIVATE)
        if (categoryViewShared.getBoolean("isGridView", false)) {
            viewType = LAYOUT_ITEM_GRID
        } else {
            viewType = LAYOUT_ITEM_LIST
        }
    }

    fun setType(viewType: Int) {
        this.viewType = viewType
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        var gridViewBinding: ItemProductGridViewBinding? = null
        var listViewBinding: ItemProductListViewBinding? = null
        if (viewType == LAYOUT_ITEM_GRID) {
            gridViewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_product_grid_view, parent, false)
        }
        if (viewType == LAYOUT_ITEM_LIST) {
            listViewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_product_list_view, parent, false)
        }
        return if (gridViewBinding != null) {
            ViewHolder(gridViewBinding)
        } else {
            ViewHolder(listViewBinding!!)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val perProduct = mDataset[position]
        if (holder.gridViewBinding != null) {
            holder.gridViewBinding?.data = perProduct
            holder.gridViewBinding?.handler = CategoryActivityHandler(mContext)
            if (Validation.checkSpecialPrice(perProduct.isFormatedSpecialPrice)) {
                holder.gridViewBinding?.newPrice?.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                holder.gridViewBinding?.newPrice?.setPadding(5, 5, 5, 5)
                holder.gridViewBinding?.newPrice?.textSize = 12f
                holder.gridViewBinding?.newPrice?.paintFlags = holder.newPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.gridViewBinding?.executePendingBindings()
            } else {
                holder.gridViewBinding?.newPrice?.visibility = View.VISIBLE
                holder.gridViewBinding?.newPrice?.textSize = 14f
                holder.gridViewBinding?.newPrice?.setTextColor(ContextCompat.getColor(mContext, R.color.v3_price_color))
                holder.gridViewBinding?.newPrice?.paintFlags = 1
                holder.gridViewBinding?.executePendingBindings()
            }

        } else {
            holder.listViewBinding.data = perProduct
            holder.listViewBinding.handler = CategoryActivityHandler(mContext)
            if (Validation.checkSpecialPrice(perProduct.isFormatedSpecialPrice)) {
                holder.listViewBinding.newPrice.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                holder.listViewBinding.newPrice.setPadding(5, 5, 5, 5)
                holder.listViewBinding.newPrice.textSize = 12f
                holder.listViewBinding.newPrice.paintFlags = holder.newPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.listViewBinding.executePendingBindings()
            } else {
                holder.listViewBinding.newPrice.visibility = View.VISIBLE
                holder.listViewBinding.newPrice.textSize = 14f
                holder.listViewBinding.newPrice.setTextColor(ContextCompat.getColor(mContext, R.color.v3_price_color))
                holder.listViewBinding.newPrice.paintFlags = 1
                holder.listViewBinding.executePendingBindings()
            }

        }
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    fun addAll(items: List<Product>) {
        mDataset.addAll(items)
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        // each data item is just a string in this case

        internal lateinit var listViewBinding: ItemProductListViewBinding
        internal var gridViewBinding: ItemProductGridViewBinding? = null
        var productName: TextView
        var productImage: ImageView
        var productImageLayout: RelativeLayout
        var newPrice: TextView
        var shortDescription: TextView
        var price: TextView
        var ratingBar: RatingBar
        var sellerStringTV: TextView
        var outOfStock: TextView
        var addToCart: View
        var addToWishlist: ImageView
        var shimmerFrameLayout: ShimmerFrameLayout
        var sale: TextView
        var model: TextView

        constructor(gridViewBinding: ItemProductGridViewBinding) : super(gridViewBinding.root) {
            this.gridViewBinding = gridViewBinding
            productName = gridViewBinding.productName
            if (!BuildConfig.MOBIKUL_APP) {
                productName.minLines = 3
                productName.maxLines = 3
            }
            shortDescription = gridViewBinding.shortDescription
            productImage = gridViewBinding.productImage
            productImageLayout = gridViewBinding.productImageLayout
            val productImageLayoutparams = productImageLayout.layoutParams as RelativeLayout.LayoutParams
            productImageLayoutparams.width = (Utils.getDeviceScreenWidth() / 2).toInt()
            productImageLayoutparams.height = (Utils.getDeviceScreenWidth() / 2).toInt()
            productImageLayout.layoutParams = productImageLayoutparams
            productImage.layoutParams = FrameLayout.LayoutParams(
                    (Utils.getDeviceScreenWidth() / 2).toInt(),
                    (Utils.getDeviceScreenWidth() / 2).toInt())
            newPrice = gridViewBinding.newPrice
            price = gridViewBinding.price
            price.visibility = View.GONE
            ratingBar = gridViewBinding.ratingBar
            sellerStringTV = gridViewBinding.sellerStringTV
            addToCart = gridViewBinding.addToCart
            shimmerFrameLayout = gridViewBinding.shimmerViewContainer
            addToWishlist = gridViewBinding.wishlist
            outOfStock = gridViewBinding.outOfStock
            sale = gridViewBinding.sale
            model = gridViewBinding.model
        }

        constructor(listViewBinding: ItemProductListViewBinding) : super(listViewBinding.root) {
            this.listViewBinding = listViewBinding
            productName = listViewBinding.productName
            if (!BuildConfig.MOBIKUL_APP) {
                productName.minLines = 3
                productName.maxLines = 3
            }
            shortDescription = listViewBinding.shortDescription
            productImage = listViewBinding.productImage
            productImageLayout = listViewBinding.productImageLayout
            val productImageLayoutparams = productImageLayout.layoutParams as RelativeLayout.LayoutParams
            productImageLayoutparams.width = (Utils.getDeviceScreenWidth() / 2).toInt()
            productImageLayoutparams.height = (Utils.getDeviceScreenWidth() / 2).toInt()
            productImageLayout.layoutParams = productImageLayoutparams
            productImage.layoutParams = FrameLayout.LayoutParams(
                    (Utils.getDeviceScreenWidth() / 2).toInt(),
                    (Utils.getDeviceScreenWidth() / 2).toInt())
            newPrice = listViewBinding.newPrice
            price = listViewBinding.price
            price.visibility = View.GONE
            ratingBar = listViewBinding.ratingBar
            shimmerFrameLayout = listViewBinding.shimmerViewContainer
            sellerStringTV = listViewBinding.sellerStringTV
            addToCart = listViewBinding.addToCart
            addToWishlist = listViewBinding.wishlist
            outOfStock = listViewBinding.outOfStock
            sale = listViewBinding.sale
            model = listViewBinding.model
        }
    }

    companion object {
        var LAYOUT_ITEM_GRID = 0
        var LAYOUT_ITEM_LIST = 1
    }

}

