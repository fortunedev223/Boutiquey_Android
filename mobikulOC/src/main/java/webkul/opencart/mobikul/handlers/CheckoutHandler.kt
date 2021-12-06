package webkul.opencart.mobikul.handlers

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View

import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.R



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CheckoutHandler(private val mcontext: Context) {

    fun onClickedContinue(view: View) {

    }

    fun onClickPaymentAddress(view: View) {
        var count = (mcontext as CheckoutActivity).supportFragmentManager.backStackEntryCount
        Log.d("CheckoutHandler", "onClickPaymentAddress: ---------->$count")
        while (count != 1) {
            mcontext.supportFragmentManager.popBackStack()
            count--
        }
        if (count != 0) {
            mcontext.binding!!.billingPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            mcontext.binding!!.paymentPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            mcontext.binding!!.confirmPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            mcontext.binding!!.billingPipeView1.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.billingAddressImage1.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
            } else {
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.billingAddressImage1.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
            }

        }
    }

    fun onClickPaymentAddress1(view: View) {
        var count = (mcontext as CheckoutActivity).supportFragmentManager.backStackEntryCount
        Log.d("CheckoutHandler", "onClickPaymentAddress1: ---------->$count")
        if (count == 2) {
            while (count != 1) {
                mcontext.supportFragmentManager.popBackStack()
                count--
            }
            if (count != 0) {
                mcontext.binding!!.billingPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
                mcontext.binding!!.paymentPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
                mcontext.binding!!.confirmPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
                mcontext.binding!!.billingPipeView1.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                    mcontext.binding!!.billingAddressImage1.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                    mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                    mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                    mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                } else {
                    mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                    mcontext.binding!!.billingAddressImage1.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                    mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                    mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                    mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                }
            }
        }
        if (count > 1) {
            while (count != 2) {
                mcontext.supportFragmentManager.popBackStack()
                count--
            }
            mcontext.binding!!.billingPipeView1.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            mcontext.binding!!.paymentPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            mcontext.binding!!.confirmPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            mcontext.binding!!.billingPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.billingAddressImage1.background = mcontext.getResources().getDrawable(R.drawable.ic_checkout_step_befor_5, mcontext.getTheme())
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
            } else {
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.billingAddressImage1.background = mcontext.getResources().getDrawable(R.drawable.ic_checkout_step_befor_5)
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
            }


        }

    }


    fun onClickShippingMethod(view: View) {
        var count = (mcontext as CheckoutActivity).supportFragmentManager.backStackEntryCount
        if (count > 2) {
            while (count != 3) {
                mcontext.supportFragmentManager.popBackStack()
                count--
            }
            mcontext.binding!!.billingPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
            mcontext.binding!!.paymentPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            mcontext.binding!!.confirmPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
            } else {
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
            }


        }

    }

    fun onClickPaymentMethod(view: View) {
        var count = (mcontext as CheckoutActivity).supportFragmentManager.backStackEntryCount
        Log.d("CheckoutHandler", "onClickPaymentMethod: ---------->$count")
        if (count > 3) {
            while (count != 4) {
                mcontext.supportFragmentManager.popBackStack()
                count--
            }
            mcontext.binding!!.billingPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
            mcontext.binding!!.paymentPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
            mcontext.binding!!.confirmPipeView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.light_gray_color1))
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected, mcontext.getTheme())
            } else {
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_unselected)
            }

        }

    }

    fun onClickConfirmOrderMethod(view: View) {
        var count = (mcontext as CheckoutActivity).supportFragmentManager.backStackEntryCount
        Log.d("CheckoutHandler", "onClickConfirmOrderMethod: ---------->$count")
        if (count > 4) {
            while (count != 5) {
                mcontext.supportFragmentManager.popBackStack()
                count--
            }

            mcontext.binding!!.billingPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
            mcontext.binding!!.paymentPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
            mcontext.binding!!.confirmPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected, mcontext.getTheme())
            } else {
                mcontext.binding!!.billingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.shippingAddressImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.paymentMethodImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
                mcontext.binding!!.confirmOrderImage.background = mcontext.getResources().getDrawable(R.drawable.checkout_selected)
            }

        }
    }
}
