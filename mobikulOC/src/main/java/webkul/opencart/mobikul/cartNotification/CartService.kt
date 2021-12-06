package webkul.opencart.mobikul.cartNotification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.ApiLoginModel
import webkul.opencart.mobikul.Cart
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.model.VIewCartModel.ViewCart
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.utils.AppSharedPreference

/**
 * Webkul Software. *
 *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CartService : IntentService("ChatService") {

    var apiLoginCallback: Callback<ApiLoginModel>? = null
    var callback: Callback<ViewCart>? = null

    override fun onHandleIntent(p0: Intent?) {
        callback = object : Callback<ViewCart> {
            override fun onFailure(call: Call<ViewCart>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ViewCart>?, response: Response<ViewCart>?) {
                if (response?.body()?.fault == 1) {
                    RetrofitCallback.apiLoginCall(this@CartService, apiLoginCallback!!)
                } else if (response?.body()?.error != 1 &&
                        response?.body()?.cart != null &&
                        response.body()?.cart?.products?.size != 0) {
                    sendNotification(p0!!)
                }
            }
        }

        apiLoginCallback = object : Callback<ApiLoginModel> {
            override fun onFailure(call: Call<ApiLoginModel>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ApiLoginModel>?, response: Response<ApiLoginModel>?) {
                if (response?.body()!!.fault == 0 && response.body()?.error != 1) {
                    AppSharedPreference.editSharedPreference(this@CartService,
                            Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_WK_TOKEN, response.body()!!.wkToken.toString())
                    AppSharedPreference.setStoreCode(this@CartService, response.body()?.language!!)
                    AppSharedPreference.setCurrencyCode(this@CartService, response.body()?.currency!!)
                    RetrofitCallback.viewCartCall(this@CartService, Utils.getScreenWidth(), callback as Callback<ViewCart>)
                }
            }
        }
        RetrofitCallback.viewCartCall(this, Utils.getScreenWidth(), callback as Callback<ViewCart>)
    }

    private fun sendNotification(intent: Intent) {
        val cartIntent = Intent(this, Cart::class.java)
        cartIntent.putExtra("notification", "1")
        val pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, cartIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var remote_picture: Bitmap? = null
        val notiStyle = NotificationCompat.BigPictureStyle()

        notiStyle.bigPicture(remote_picture)
        val idChannel = "OpenCart-Mobikul"
        val notificationBuilder = NotificationCompat.Builder(this, idChannel)
                .setSmallIcon(R.drawable.status)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_HIGH
        } else {
            NotificationManager.IMPORTANCE_MAX
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel: NotificationChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(idChannel, this.getString(R.string.app_name), importance)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            mChannel.description = this.getString(R.string.app_name)
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mChannel.enableLights(true)
            mChannel.setLightColor(Color.RED)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager.createNotificationChannel(mChannel)
            notificationBuilder.setChannelId(idChannel)
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX)
        }
        notificationBuilder
                .setLargeIcon(icon)
                .setContentTitle("Cart Item")
                .setColor(ContextCompat.getColor(applicationContext, R.color.dark_primary_color))
                .setContentText("You have Items in your Cart")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)

        val notification = notificationBuilder.build()
        // cancel notification after click
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
        notification.defaults = notification.defaults or Notification.DEFAULT_VIBRATE
        notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
        // show scrolling text on status bar when notification arrives
        notification.tickerText = "Cart Item"
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {
        val TAG = CartService::class.java.canonicalName
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("onBind", "========>" + TAG)
        return null
    }

    override fun onCreate() {
        Log.d("onCreate", "========>" + TAG)
        super.onCreate()
    }

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Log.d("onStartCommand", "========>" + TAG)
//        return super.onStartCommand(intent, flags, startId)
//    }

    override fun onDestroy() {
        Log.d("onDestroy", "========>" + TAG)
        super.onDestroy()
    }

}
