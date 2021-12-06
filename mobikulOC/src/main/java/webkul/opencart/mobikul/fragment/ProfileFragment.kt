package webkul.opencart.mobikul.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.content.res.AppCompatResources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import webkul.opencart.mobikul.BuildConfig

import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var profileBinding: FragmentProfileBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        setAppCompatDrawable()
        if (isAdded) {
            if (AppSharedPreference.isLogin(activity!!, "")) {
                profileBinding?.handlers = (activity as MainActivity).mMainActivityBinding?.handlers
                profileBinding?.data = (activity as MainActivity).mMainActivityBinding?.data
            } else {
                profileBinding?.handlers = (activity as MainActivity).mMainActivityBinding?.handlers
                profileBinding?.data = (activity as MainActivity).mMainActivityBinding?.data
            }
        }
        if (!BuildConfig.isMobikul) {
            profileBinding?.sellerPageLine?.visibility = View.VISIBLE
            profileBinding?.sellerPage?.visibility = View.VISIBLE
        }
        return profileBinding?.root
    }

    private fun setAppCompatDrawable() {
        profileBinding?.profileImage?.background = AppCompatResources.getDrawable(activity!!, R.drawable.profile_background)
        profileBinding?.orderReturn?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.order_return), null, null, null)
        profileBinding?.wishlistDashboard?.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(activity!!, R.drawable.wishlist_dashboard), null, null)
        profileBinding?.addressbookDashboard?.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(activity!!, R.drawable.addressbook_dashboard), null, null)
        profileBinding?.orderDashboard?.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(activity!!, R.drawable.order_dashboard), null, null)
        profileBinding?.notification?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.ic_notifications_black_24dp), null, null, null)
        profileBinding?.dashboard?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.ic_acc_info_account), null, null, null)
        profileBinding?.editAccout?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.edit_dashboard), null, null, null)
        profileBinding?.changePassword?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.ic_password), null, null, null)
        profileBinding?.downloadProduct?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.ic_download_account), null, null, null)
        profileBinding?.rewardPoints?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.ic_reward_point), null, null, null)
        profileBinding?.transaction?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.ic_transaction), null, null, null)
        profileBinding?.logout?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.logout), null, null, null)
        profileBinding?.sellerProfile?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.seller_profile), null, null, null)
        profileBinding?.sellerDashboard?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.seller_dashboard), null, null, null)
        profileBinding?.sellerOrder?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.seller_order), null, null, null)
        profileBinding?.facebookBtn?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!,
                R.drawable.facebook), null, null, null)
        profileBinding?.googleBtn?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!,
                R.drawable.googleplus), null, null, null)
        profileBinding?.addProduct?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.add_product),
                null, null, null)
        profileBinding?.productList?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(activity!!, R.drawable.product_list),
                null, null, null)
    }
}
