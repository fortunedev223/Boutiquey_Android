package webkul.opencart.mobikul.gcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.text.Html
import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.HashSet

import webkul.opencart.mobikul.CategoryActivity
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.ViewProductSimple
import webkul.opencart.mobikul.analytics.MobikulApplication
import webkul.opencart.mobikul.otherActivty
import webkul.opencart.mobikul.utils.Validation

/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class MyFcmListenerService : FirebaseMessagingService() {
    private var title: String? = null
    internal var notificationId = 0
    private var configShared: SharedPreferences? = null
    private var message: String? = null
    private var drawerDataObject: JSONObject? = null
    private val drawerData: SharedPreferences? = null
    private var bannerImage: String? = "null"
    private lateinit var data: Map<String, String>

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        //        title = data.getString("title");
        //        notificationId = Integer.parseInt(data.getString("notification_id"));
        try {
            Log.d(TAG, "From: " + remoteMessage!!.from!!)
            Log.d(TAG, "Message: " + message)
            Log.d(TAG, "Data: " + remoteMessage.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (remoteMessage!!.from!!.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        //        ActivityCompat.invalidateOptionsMenu(Activity activity);

        onPostExecute(remoteMessage)
        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         * - Store message in local database.
         * - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        // [END_EXCLUDE]
    }

    protected fun onPostExecute(remoteMessage: RemoteMessage) {
        data = remoteMessage.data
        Log.d("notification------->", data.toString() + "")
        Log.d("notification----class", "notification----class MYFCMListenerService")
        message = Html.fromHtml(Html.fromHtml(data!!["content"]).toString()).toString()
        title = data!!["title"]
        notificationId = Integer.parseInt(data!!["notification_id"])
        if (data!!.containsKey("bannerImage"))
            bannerImage = data["bannerImage"]
        Log.d("bannerImage", bannerImage + "")
        var intent: Intent? = null
        configShared = getSharedPreferences("com.webkul.mobikul.notification", Context.MODE_MULTI_PROCESS)
        val editor = configShared!!.edit()
        val unreadNotifications = configShared!!.getStringSet("unreadNotifications", HashSet()) as HashSet<String>
        unreadNotifications.add(notificationId.toString() + "")
        val notificationType = data["type"]

        if (notificationType == "product") {
            intent = Intent(this, ViewProductSimple::class.java)
            intent.putExtra("idOfProduct", data!!["id"])
            intent.putExtra("nameOfProduct", title)
            if (Validation.isAppIsInBackground(this)) {
                println(" IN BACKGROUND -------------------- ")
                intent.putExtra("isNotification", "fromFCM")
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            sendNotification(intent)
        } else if (notificationType == "category") {
            intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("ID", data!!["id"])
            intent.putExtra("CATEGORY_NAME", title)
            intent.putExtra("type", "category")
            intent.putExtra("isNotification", title)
            //            drawerData = getSharedPreferences("drawerData", MODE_PRIVATE);
            //            String drawerString = drawerData.getString("drawerData", "");
            try {
                drawerDataObject = JSONObject("")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            intent.putExtra("isNotification", title)
            intent.putExtra("drawerData", drawerDataObject?.toString() + "")
            sendNotification(intent)
        } else if (notificationType.equals("Custom", ignoreCase = true)) {
            intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("type", "custom")
            intent.putExtra("id", data["id"])
            intent.putExtra("isNotification", title)
            sendNotification(intent)
        } else {
            intent = Intent(this, otherActivty::class.java)
            intent.putExtra("title", title)
            intent.putExtra("shortDiscription", message)
            intent.putExtra("type", "other")
            sendNotification(intent)
        }
        //        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        editor.putStringSet("unreadNotifications", unreadNotifications)
        editor.apply()
        Log.d("unreadNotifications", unreadNotifications.toString())
    }

    private fun sendNotification(intent: Intent) {
        val pendingIntent = PendingIntent.getActivity(this,
                notificationId /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var remote_picture: Bitmap? = null
        val notiStyle = NotificationCompat.BigPictureStyle()
        try {
            remote_picture = BitmapFactory.decodeStream(URL(bannerImage).content as InputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
                .setContentTitle(title)
                .setColor(ContextCompat.getColor(applicationContext, R.color.dark_primary_color))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)

        if (!bannerImage.equals("null", ignoreCase = true))
            notificationBuilder.setStyle(notiStyle)
        val notification = notificationBuilder.build()
        // cancel notification after click
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
        notification.defaults = notification.defaults or Notification.DEFAULT_VIBRATE
        notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
        // show scrolling text on status bar when notification arrives
        notification.tickerText = title
        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build())
    }

    fun createNotificationChannel() {
        val name = "Channel"
        val description = "Channel Description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel=NotificationChannel(CHANNEL_ID)
    }

    companion object {

        private val TAG = "MyFirebaseMsgService"
    }
}

