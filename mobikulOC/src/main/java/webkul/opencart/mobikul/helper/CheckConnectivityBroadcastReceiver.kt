package webkul.opencart.mobikul.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import webkul.opencart.mobikul.analytics.MobikulApplication

class CheckConnectivityBroadcastReceiver(private val connectivityReceiverListener: ConnectivityReceiverListener) : BroadcastReceiver() {

    companion object {

        fun isConnected() : Boolean {
            val connectivityManager = MobikulApplication.getInstance().applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetworkInfo

            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        connectivityReceiverListener.onNetworkConnectionChanged(isConnected)
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}