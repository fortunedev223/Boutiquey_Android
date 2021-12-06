package webkul.opencart.mobikul.handlers

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.callback.CatergorySort
import webkul.opencart.mobikul.callback.ManuFactureInfor
import webkul.opencart.mobikul.model.ManufactureInfoModel.Manufacture
import webkul.opencart.mobikul.model.ProductCategory.FilterGroup
import webkul.opencart.mobikul.model.ProductCategory.ProductCategory
import webkul.opencart.mobikul.model.ProductSearch.ProductSearch
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.databinding.CategoryFilterBinding

import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.facebook.login.widget.ProfilePictureView.TAG
import webkul.opencart.mobikul.*
import webkul.opencart.mobikul.activity.ViewMoreActivity
import webkul.opencart.mobikul.adapter.SortItemAdapter
import webkul.opencart.mobikul.adapter.SortItemAdapterForManufacture
import webkul.opencart.mobikul.adapter.SortItemAdapterForSearch
import webkul.opencart.mobikul.model.ProductCategory.Sort
import webkul.opencart.mobikul.databinding.SorterBotttomSheetBinding

/**
Webkul Software. *
@Mobikul
@OpencartMobikul
@author Webkul
@copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
@license https://store.webkul.com/license.html
 */

class CategoryActivityHandler(private val mContext: Activity) : CatergorySort, ManuFactureInfor {
    private var productCategory: ProductCategory? = null
    private var productSearch: ProductSearch? = null
    private var manufacture: Manufacture? = null
    private var sortData: Array<String>? = null
    private var sortArrayList: ArrayList<Sort>? = null
    private var sheetDialog: BottomSheetDialog? = null
    private var sheetBinding: SorterBotttomSheetBinding? = null
    private var sortList: RecyclerView? = null
    private var sortByData: SortByData? = null

    fun onClickViewSwitcher(view: View) {
        if ((mContext as CategoryActivity).binding.myRecyclerView.adapter?.getItemViewType(0) == 0) {
            mContext.binding.myRecyclerView.layoutManager = LinearLayoutManager(mContext)
            mContext.myAdapter.setType(MyAdapter.LAYOUT_ITEM_LIST)
            val list = AppCompatResources.getDrawable(mContext, R.drawable.ic_block_view)
            mContext.viewSwticherImageView.setImageDrawable(list)
            mContext.getSharedPreferences("categoryView", MODE_PRIVATE).edit().putBoolean("isGridView", false).apply()
        } else {
            mContext.binding.myRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
            val block = AppCompatResources.getDrawable(mContext, R.drawable.ic_list_view)
            mContext.myAdapter.setType(MyAdapter.LAYOUT_ITEM_GRID)
            mContext.viewSwticherImageView.setImageDrawable(block)
            mContext.getSharedPreferences("categoryView", MODE_PRIVATE).edit().putBoolean("isGridView", true).apply()
        }
    }

    fun onClickShopBy(view: View) {
        if (productCategory != null) {
            if (productCategory!!.moduleData!!.filterGroups != null) {
                if (productCategory!!.moduleData!!.filterGroups!!.size != 0) {
                    openFilterDialog(productCategory!!.moduleData!!.filterGroups)
                } else {
                    MakeToast().shortToast(mContext, mContext.resources.getString(R.string.filter_options_are_not_available))
                }
            } else {
                MakeToast().shortToast(mContext, mContext.resources.getString(R.string.filter_options_are_not_available))
            }
        }
    }

    private fun openFilterDialog(filterGroups: List<FilterGroup>?) {
        mContext as CategoryActivity
        val mDialog = Dialog(mContext)
        val filterBinding = CategoryFilterBinding.inflate(LayoutInflater.from(mContext))
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(filterBinding.root)
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(mContext, R.color.white_transparent)))
        setFilterData(filterBinding, filterGroups)
        mDialog.window!!.setLayout(webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                webkul.opencart.mobikul.helper.Utils.getDeviceScrenHeight()
                        - mContext.binding?.toolbar!!.height / 2)
        filterBinding.cancel.setOnClickListener {
            mDialog.dismiss()
        }

        filterBinding.close.setOnClickListener {
            if (CategoryActivity.filterName!!.size != 0) {
                CategoryActivity.filterName!!.clear()
                setFilterData(filterBinding, filterGroups)
            }
        }

        filterBinding.apply.setOnClickListener {
            mContext.applyFilter()
            mDialog.dismiss()
        }
        mDialog.show()
    }

    private fun setFilterData(filterBinding: CategoryFilterBinding, filterGroups: List<FilterGroup>?) {
        filterBinding.filterContainer.removeAllViews()
        for (i in filterGroups!!.indices) {
            val linearLayout = LinearLayout(mContext)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.background = ContextCompat.getDrawable(mContext, R.drawable.border)
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(10, 10, 10, 10)
            linearLayout.layoutParams = params
            val title = TextView(mContext)
            title.textSize = 16f
            title.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            title.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_border_color))
            title.setPadding(10, 10, 10, 10)
            title.textAlignment = View.TEXT_ALIGNMENT_CENTER
            title.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            linearLayout.addView(title)
            val view = View(mContext)
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_gray_color1))
            view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
            linearLayout.addView(view)

            for (j in 0 until filterGroups[i].filter!!.size) {
                val filterName = CheckBox(mContext)
                filterName.textSize = 14f
                filterName.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                if (CategoryActivity.filterName.contains(filterGroups[i].filter!![j].filterId)) {
                    filterName.isChecked = true
                }
                filterName.tag = filterGroups[i].filter!![j].filterId
                filterName.setPadding(10, 10, 10, 10)
                println(" Filter Name === " + filterGroups[i].filter!![j].name)
                filterName.text = filterGroups[i].filter!![j].name
                linearLayout.addView(filterName)
                filterName.setOnCheckedChangeListener { compoundButton, b ->
                    if (filterName.isChecked) {
                        CategoryActivity.filterName.add(filterName.tag.toString())
                    } else if (!filterName.isChecked) {
                        CategoryActivity.filterName.remove(filterName.tag.toString())
                    }
                }
            }
            title.text = filterGroups[i].name
            filterBinding.filterContainer.addView(linearLayout)
        }


    }

    fun onClickSortBy(view: View) {
        sheetBinding = SorterBotttomSheetBinding.inflate(LayoutInflater.from(mContext))
        sheetDialog = BottomSheetDialog(mContext!!, R.style.BottomSheetDialogTheme)
        sheetDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        sheetDialog?.setContentView(sheetBinding!!.root)
        sortList = sheetBinding!!.sortList
        sortList?.layoutManager = LinearLayoutManager(mContext)
        if (productCategory != null) {
            mContext as CategoryActivity
            if (!mContext.searchQuery.equals("", ignoreCase = true)) {
            } else {
                val category = java.util.ArrayList<Sort>()
                for (i in 0..productCategory!!.categoryData.sorts.size - 1) {
                    category.add(productCategory!!.categoryData.sorts.get(i))
                }
                val sortItemAdapter: SortItemAdapter = SortItemAdapter(mContext, category, sortByData, sheetDialog, sortData)
                sortList!!.adapter = sortItemAdapter
            }
        } else if (productSearch != null) {
            val category = java.util.ArrayList<webkul.opencart.mobikul.model.ProductSearch.Sort>()
            for (i in 0..productSearch!!.sorts.size - 1) {
                category.add(productSearch!!.sorts.get(i))
            }
            sortList!!.adapter = SortItemAdapterForSearch(mContext, category, sortByData, sheetDialog, sortData)
        } else if (manufacture != null) {
            val category = java.util.ArrayList<webkul.opencart.mobikul.model.ManufactureInfoModel.Sort>()
            manufacture!!.manufacturers.sorts.size
            for (i in 0..manufacture!!.manufacturers.sorts.size - 1) {
                category.add(manufacture!!.manufacturers.sorts.get(i))
            }
            sortList?.adapter = SortItemAdapterForManufacture(mContext, category, sortByData, sheetDialog, sortData)
        }
        sheetDialog?.show()
    }

    fun onClickSortByViewMore(view: View) {
        sheetBinding = SorterBotttomSheetBinding.inflate(LayoutInflater.from(mContext))
        sheetDialog = BottomSheetDialog(mContext, R.style.BottomSheetDialogTheme)
        sheetDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        sheetDialog?.setContentView(sheetBinding!!.root)
        sortList = sheetBinding?.sortList
        sortList?.layoutManager = LinearLayoutManager(mContext)
        if (sortArrayList != null && sortArrayList!!.size > 0) {
            mContext as ViewMoreActivity
            val category = java.util.ArrayList<Sort>()
            category.addAll(sortArrayList!!)
            sortList?.adapter = SortItemAdapter(mContext, category, sortByData, sheetDialog, sortData)
        }
        sheetDialog?.show()
    }

    fun onClickAddToCart(view: View, product: Product) {
        val cartModelCallback = object : Callback<AddToCartModel> {
            override fun onResponse(call: Call<AddToCartModel>, response: Response<AddToCartModel>) {
                if (response.body()?.error != 1) {
                    val total = response.body()!!.total!!
                    MakeToast().shortToast(mContext, response.body()!!.message)
                    AppSharedPreference.editSharedPreference(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, total)
                    if (mContext is CategoryActivity) {
                        if (mContext.itemCart != null) {
                            val icon = mContext.itemCart!!.icon as LayerDrawable
                            Log.d(TAG, "CartCount-----> " + AppSharedPreference.getCartItems(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                            Utils.setBadgeCount(mContext, icon, AppSharedPreference.getCartItems(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                        }
                    }
                } else {
                    MakeToast.instance.shortToast(mContext, response?.body()?.message)
                }

                SweetAlertBox.dissmissSweetAlertBox()
            }

            override fun onFailure(call: Call<AddToCartModel>, t: Throwable) {

            }
        }
        if (!product.isHasOptions) {
            SweetAlertBox().showProgressDialog(mContext)
            RetrofitCallback.addToCartWithoutOptionCall(mContext, product.productId.toString(), "1",
                    RetrofitCustomCallback(cartModelCallback, mContext))
        } else {
            val intent = Intent(mContext, ViewProductSimple::class.java)
            intent.putExtra("idOfProduct", product.getProductId())
            intent.putExtra("nameOfProduct", product.getProductName())
            mContext.startActivity(intent)
        }
    }

    fun onClickAddToWishlist(view: View, product: Product) {
        val shared = mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        view as ImageView
        if (isLoggedIn) {
            val wishlistCallback = object : Callback<AddtoWishlist> {
                override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    MakeToast().shortToast(mContext, response.body()!!.message)
                    if (response.body()?.status != null && response.body()?.status == true) {
                        view.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.wishlist_selected))
                        product.isWishlist_status = true
                    } else {
                        view.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.wishlishv3_product_page))
                        product.isWishlist_status = false
                    }
                }

                override fun onFailure(call: Call<AddtoWishlist>, t: Throwable) {
                    t.printStackTrace()
                }
            }
            SweetAlertBox().showProgressDialog(mContext)
            RetrofitCallback.addToWishlistCall(mContext, product.getProductId(), RetrofitCustomCallback(wishlistCallback, mContext))
        } else {
            SweetAlertBox().showWarningPopUp(mContext, "", mContext.resources.getString(R.string.wishlist_msg), product.getProductId())
        }
    }

    fun onClickProduct(view: View, product: Product) {
        val productName = product.productName
        val productId = product.productId
        val intent = Intent(mContext, ViewProductSimple::class.java)
        try {
            intent.putExtra("idOfProduct", productId)
            intent.putExtra("nameOfProduct", productName)
        } catch (e: NumberFormatException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        mContext.startActivity(intent)
        if (mContext is CategoryActivity) {
            mContext.overridePendingTransition(R.anim.reverse_fadein, R.anim.nothing)
        }
    }

    fun setSortData(sortData: Array<String>) {
        this.sortData = sortData
    }

    override fun getproductCategorySortBy(productCategory: ProductCategory) {
        this.productCategory = productCategory
    }

    override fun getproductSearchSortBy(productSearch: ProductSearch) {
        this.productSearch = productSearch
    }

    override fun getManufactureInfo(manufacture: Manufacture) {
        this.manufacture = manufacture

    }

    fun setSortByData(sortByData: SortByData) {
        this.sortByData = sortByData
    }

    fun setSortByDataViewMore(sortByData: SortByData) {
        this.sortByData = sortByData
    }

    fun setSortList(sortArrayList: ArrayList<Sort>) {
        this.sortArrayList = sortArrayList
    }
}
