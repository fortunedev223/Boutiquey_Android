package webkul.opencart.mobikul.helper;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashSet;
import java.util.Set;

import webkul.opencart.mobikul.CategoryActivity;
import webkul.opencart.mobikul.databinding.NoInternetBinding;
import webkul.opencart.mobikul.utils.AppSharedPreference;


public class Utils {


    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static int getDeviceScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getDeviceScrenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static void showConnectionDialog(Context context) {
        Dialog dialog = new Dialog(context);
        NoInternetBinding internetBinding = NoInternetBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(internetBinding.getRoot());
        dialog.getWindow().setLayout(webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
        internetBinding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public static boolean isOnline(Activity activity) {
        ConnectivityManager conMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
//        isInternetAvailable = !(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable());

        return (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) ? false : true;


    }

    public static void setRecentSearchData(String search, CategoryActivity categoryActivity) {
        if (AppSharedPreference.INSTANCE.getRecentSearchData(categoryActivity) != null) {
            Set<String> recentSearchSet = AppSharedPreference.INSTANCE.getRecentSearchData(categoryActivity);
            recentSearchSet.add(search);
            AppSharedPreference.INSTANCE.putRecentSearchData(categoryActivity, recentSearchSet);
        } else {
            LinkedHashSet<String> recentSearchSet = new LinkedHashSet<>();
            recentSearchSet.add(search);
            AppSharedPreference.INSTANCE.putRecentSearchData(categoryActivity, recentSearchSet);
        }
    }

    public static String getScreenWidth() {
        return String.valueOf(getDeviceScreenWidth());
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void objectAnimator(View view) {
        ObjectAnimator.ofFloat(view, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1400)
                .start();
    }
}
