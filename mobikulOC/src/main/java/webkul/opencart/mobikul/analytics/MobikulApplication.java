package webkul.opencart.mobikul.analytics;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import webkul.opencart.mobikul.CategoryActivity;
import webkul.opencart.mobikul.cartNotification.CartBroadCast;
import webkul.opencart.mobikul.cartNotification.CartService;
import webkul.opencart.mobikul.helper.Constant;
import webkul.opencart.mobikul.R;
import webkul.opencart.mobikul.ViewProductSimple;

public class MobikulApplication extends MultiDexApplication implements LifecycleObserver {


    private static MobikulApplication mInstance;
    public static Activity activity;

    private Tracker mTracker;

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        Log.d("OnLifecycleEvent", "===MobikulApplication====ON_STOP==>Application");
//        Intent intent = new Intent(getApplicationContext(), CartBroadCast.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                getApplicationContext(),
//                0,
//                intent,
//                PendingIntent.FLAG_ONE_SHOT);
//        AlarmManager mgr = (AlarmManager) getApplicationContext()
//                .getSystemService(Context.ALARM_SERVICE);
//        if (mgr != null) {
//            mgr.setRepeating(AlarmManager.RTC_WAKEUP,
//                    System.currentTimeMillis() + 60,
//                    System.currentTimeMillis() + 60,
//                    pendingIntent);
//        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        Log.d("OnLifecycleEvent", "===MobikulApplication====ON_START==>Application");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        mInstance = this;
//        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(
//                    getApplicationContext(),
//                    0,
//                    intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//            AlarmManager mgr = (AlarmManager) getApplicationContext()
//                    .getSystemService(Context.ALARM_SERVICE);
//            if (mgr != null) {
//                mgr.set(AlarmManager.RTC,
//                        System.currentTimeMillis() + 50,
//                        pendingIntent);
//            }
//            System.exit(2);
//        });
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d("ApplicationClass", "onTrimMemory: ");
        getSharedPreferences(Constant.INSTANCE.getSYNC_STATUS(), MODE_PRIVATE).edit().putBoolean(Constant.INSTANCE.getSYNC_STATUS(), true).apply();
    }

    public static synchronized MobikulApplication getInstance() {
        return mInstance;
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.app_tracker);
            mTracker.enableExceptionReporting(true);
        }
        return mTracker;
    }

    public Class viewCategoryList() {
        return CategoryActivity.class;
    }

    public Class viewCategoryGrid() {
        return CategoryActivity.class;
    }

    public Class viewProductSimple() {
        return ViewProductSimple.class;
    }

    public Class<?> viewMarketPlaceHome() {
        return null;
    }

    public Class<?> addProduct() {
        return null;
    }

    public Class<?> productList() {
        return null;
    }

    public Class<?> viewSellerDashBoard() {
        return null;
    }

    public Class<?> viewSellerOrderHistory() {
        return null;
    }

    public Class<?> viewSellerProfile() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}