package webkul.opencart.mobikul.helper

import android.annotation.TargetApi
import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.widget.Toast

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkSchedulerService : JobService(), CheckConnectivityBroadcastReceiver.ConnectivityReceiverListener {

    private val TAG = NetworkSchedulerService::class.java.simpleName
    private lateinit var checkConnectivityBroadcastReceiver: CheckConnectivityBroadcastReceiver

    override fun onCreate() {
        super.onCreate()
        checkConnectivityBroadcastReceiver = CheckConnectivityBroadcastReceiver(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_NOT_STICKY
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        unregisterReceiver(checkConnectivityBroadcastReceiver)
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        registerReceiver(checkConnectivityBroadcastReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        return true
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        val message : String = if(isConnected) "Connected" else "Not Connected"
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    public interface NetworkSchedulerCallback {
        fun onNetworkChange()
    }
}