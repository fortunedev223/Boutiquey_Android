package webkul.opencart.mobikul;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import webkul.opencart.mobikul.utils.SweetAlertBox;

@TargetApi(Build.VERSION_CODES.M)
class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    CancellationSignal cancellationSignal;
    private Context appContext;
    private Callback mCallback;

    public FingerprintHandler(Context context, Callback callback) {
        appContext = context;
        mCallback = callback;
    }

    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        Toast.makeText(appContext,
                "Authentication error\n" + errString,
                Toast.LENGTH_LONG).show();
        SweetAlertBox.Companion.dissmissSweetAlertBox();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(appContext,
                "Authentication help\n" + helpString,
                Toast.LENGTH_LONG).show();
        SweetAlertBox.Companion.dissmissSweetAlertBox();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(appContext,
                "Authentication failed.",
                Toast.LENGTH_LONG).show();
        SweetAlertBox.Companion.dissmissSweetAlertBox();
        mCallback.onError();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        SharedPreferences appShared = appContext.getSharedPreferences("configureView", Context.MODE_PRIVATE);
        if (!appShared.getBoolean("FingetprintEnabled", false))
            appShared.edit().putBoolean("FingetprintEnabled", true).apply();
        mCallback.onAuthenticated();
    }


    interface Callback {

        void onAuthenticated();

        void onError();
    }
}