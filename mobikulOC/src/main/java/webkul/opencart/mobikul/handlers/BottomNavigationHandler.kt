package webkul.opencart.mobikul.handlers

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import webkul.opencart.mobikul.activity.SubCategoryV3Theme
import webkul.opencart.mobikul.fragment.ProfileFragment
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.NotificationActivity
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.ActivityMainBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class BottomNavigationHandler(val mContext: Context, val bottomHome: ImageView, val bottomCategory: ImageView,
                              val bottomNotification: ImageView, val bottomAccount: ImageView) {

    private val bottomHomeDrawable: Drawable?
    private val bottomCategoryDrawable: Drawable?
    private val bottomNotificationDrawable: Drawable?
    private val bottomAccountDrawable: Drawable?
    private val bottomHomeDrawableUnselect: Drawable?
    private val bottomCategoryDrawableUnselect: Drawable?
    private val bottomNotificationDrawableUnselect: Drawable?
    private val bottomAccountDrawableUnselect: Drawable?
    private val mainBinding: ActivityMainBinding

    init {
        bottomHomeDrawable = AppCompatResources.getDrawable(mContext, R.drawable.bottom_home_select)
        bottomCategoryDrawable = AppCompatResources.getDrawable(mContext, R.drawable.bottom_category_select)
        bottomAccountDrawable = AppCompatResources.getDrawable(mContext, R.drawable.bottom_user_select)
        bottomNotificationDrawable = AppCompatResources.getDrawable(mContext, R.drawable.bottom_notification_select)
        bottomHomeDrawableUnselect = AppCompatResources.getDrawable(mContext, R.drawable.bottom_home)
        bottomCategoryDrawableUnselect = AppCompatResources.getDrawable(mContext, R.drawable.bottom_category)
        bottomAccountDrawableUnselect = AppCompatResources.getDrawable(mContext, R.drawable.bottom_user)
        bottomNotificationDrawableUnselect = AppCompatResources.getDrawable(mContext, R.drawable.bottom_notification)
        mainBinding = (mContext as MainActivity).mMainActivityBinding!!
    }

    fun onClickHome(view: View) {
        onClickHomeTab()
    }

    fun onClickHomeTab(){
        if (!mainBinding.refresh.isRefreshing) {
            if (check == 1) {
                check = 0
                mainBinding.navigation!!.bottomHomeTv.setTextColor(ContextCompat.getColor(mContext, R.color.accent_color))
                mainBinding.navigation.bottomCategoryTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomNotificationTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomProfileTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                bottomCategory.setImageDrawable(bottomCategoryDrawableUnselect)
                bottomHome.setImageDrawable(bottomHomeDrawable)
                bottomNotification.setImageDrawable(bottomNotificationDrawableUnselect)
                bottomAccount.setImageDrawable(bottomAccountDrawableUnselect)
                addHomeFragment()
            } else {
                check = 1
                mainBinding.navigation!!.bottomHomeTv.setTextColor(ContextCompat.getColor(mContext, R.color.accent_color))
                mainBinding.navigation.bottomCategoryTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomNotificationTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomProfileTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                bottomCategory.setImageDrawable(bottomCategoryDrawableUnselect)
                bottomHome.setImageDrawable(bottomHomeDrawable)
                bottomNotification.setImageDrawable(bottomNotificationDrawableUnselect)
                bottomAccount.setImageDrawable(bottomAccountDrawableUnselect)
                addHomeFragment()
            }
        }
    }

    private fun addHomeFragment() {
        mainBinding.refresh.visibility = View.VISIBLE
        mainBinding.container.visibility = View.GONE
        val count = (mContext as MainActivity).supportFragmentManager.backStackEntryCount
        for (i in 0 until count) {
            mContext.supportFragmentManager.popBackStack()
        }
    }

    fun onClickCategory(view: View) {
        if (!mainBinding.refresh.isRefreshing) {
            if (check == 1) {
                check = 0
                mainBinding.navigation!!.bottomHomeTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomCategoryTv.setTextColor(ContextCompat.getColor(mContext, R.color.accent_color))
                mainBinding.navigation.bottomNotificationTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomProfileTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                bottomCategory.setImageDrawable(bottomCategoryDrawable)
                bottomHome.setImageDrawable(bottomHomeDrawableUnselect)
                bottomNotification.setImageDrawable(bottomNotificationDrawableUnselect)
                bottomAccount.setImageDrawable(bottomAccountDrawableUnselect)
                addCategoryFragment()
            } else {
                check = 1
                mainBinding.navigation!!.bottomHomeTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomCategoryTv.setTextColor(ContextCompat.getColor(mContext, R.color.accent_color))
                mainBinding.navigation.bottomNotificationTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomProfileTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                bottomCategory.setImageDrawable(bottomCategoryDrawable)
                bottomHome.setImageDrawable(bottomHomeDrawableUnselect)
                bottomNotification.setImageDrawable(bottomNotificationDrawableUnselect)
                bottomAccount.setImageDrawable(bottomAccountDrawableUnselect)
                addCategoryFragment()
            }
        }
    }

    private fun addCategoryFragment() {
        mainBinding.refresh.visibility = View.GONE
        mainBinding.container.visibility = View.VISIBLE
        val count = (mContext as MainActivity).supportFragmentManager.backStackEntryCount
        for (i in 0 until count) {
            mContext.supportFragmentManager.popBackStack()
        }
        mContext.supportFragmentManager
                .beginTransaction()
                .replace(mainBinding.container.id, SubCategoryV3Theme(), SubCategoryV3Theme::class.java.simpleName)
                .addToBackStack(SubCategoryV3Theme::class.java.simpleName)
                .commit()
    }

    fun onClickNotification(view: View) {
        if (!mainBinding.refresh.isRefreshing) {
            if (check == 1) {
                check = 0
                mainBinding.navigation!!.bottomHomeTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomCategoryTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomNotificationTv.setTextColor(ContextCompat.getColor(mContext, R.color.accent_color))
                mainBinding.navigation.bottomProfileTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                bottomCategory.setImageDrawable(bottomCategoryDrawableUnselect)
                bottomHome.setImageDrawable(bottomHomeDrawableUnselect)
                bottomNotification.setImageDrawable(bottomNotificationDrawable)
                bottomAccount.setImageDrawable(bottomAccountDrawableUnselect)
                if ((mContext as MainActivity).supportFragmentManager.findFragmentByTag(NotificationActivity::class.java.simpleName) == null) {
                    addNotificationFragment()
                }
            } else {
                check = 1
                mainBinding.navigation!!.bottomHomeTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomCategoryTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomNotificationTv.setTextColor(ContextCompat.getColor(mContext, R.color.accent_color))
                mainBinding.navigation.bottomProfileTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                bottomCategory.setImageDrawable(bottomCategoryDrawableUnselect)
                bottomHome.setImageDrawable(bottomHomeDrawableUnselect)
                bottomNotification.setImageDrawable(bottomNotificationDrawable)
                bottomAccount.setImageDrawable(bottomAccountDrawableUnselect)
                if ((mContext as MainActivity).supportFragmentManager.findFragmentByTag(NotificationActivity::class.java.simpleName) == null) {
                    addNotificationFragment()
                }
            }
        }

    }

    private fun addNotificationFragment() {
        mainBinding.refresh.visibility = View.GONE
        mainBinding.container.visibility = View.VISIBLE
        val count = (mContext as MainActivity).supportFragmentManager.backStackEntryCount
        for (i in 0 until count) {
            mContext.supportFragmentManager.popBackStack()
        }
        mContext.supportFragmentManager
                .beginTransaction()
                .replace(mainBinding.container.id, NotificationActivity(), NotificationActivity::class.java.simpleName)
                .addToBackStack(NotificationActivity::class.java.simpleName)
                .commit()
    }

    fun onClickAccount(view: View) {
        if (!mainBinding.refresh.isRefreshing) {
            if (check == 1) {
                check = 0
                mainBinding.navigation?.bottomHomeTv?.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation?.bottomCategoryTv?.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation?.bottomNotificationTv?.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation?.bottomProfileTv?.setTextColor(ContextCompat.getColor(mContext, R.color.accent_color))
                bottomCategory.setImageDrawable(bottomCategoryDrawableUnselect)
                bottomHome.setImageDrawable(bottomHomeDrawableUnselect)
                bottomNotification.setImageDrawable(bottomNotificationDrawableUnselect)
                bottomAccount.setImageDrawable(bottomAccountDrawable)
                addAccountFragment()
            } else {
                check = 1
                mainBinding.navigation!!.bottomHomeTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomCategoryTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomNotificationTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
                mainBinding.navigation.bottomProfileTv.setTextColor(ContextCompat.getColor(mContext, R.color.accent_color))
                bottomCategory.setImageDrawable(bottomCategoryDrawableUnselect)
                bottomHome.setImageDrawable(bottomHomeDrawableUnselect)
                bottomNotification.setImageDrawable(bottomNotificationDrawableUnselect)
                bottomAccount.setImageDrawable(bottomAccountDrawable)
                addAccountFragment()
            }
        }
    }

    private fun addAccountFragment() {
        mainBinding.refresh.visibility = View.GONE
        mainBinding.container.visibility = View.VISIBLE
        val count = (mContext as MainActivity).supportFragmentManager.backStackEntryCount
        for (i in 0 until count) {
            mContext.supportFragmentManager.popBackStack()
        }
        mContext.supportFragmentManager
                .beginTransaction()
                .replace(mainBinding.container.id, ProfileFragment(), ProfileFragment::class.java.simpleName)
                .addToBackStack(ProfileFragment::class.java.simpleName)
                .commit()
    }

    companion object {
        private var check = 0
    }
}
