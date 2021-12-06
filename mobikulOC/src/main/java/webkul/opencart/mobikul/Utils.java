package webkul.opencart.mobikul;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import webkul.opencart.mobikul.helper.Constant;
import webkul.opencart.mobikul.credentials.AppCredentials;

import static webkul.opencart.mobikul.FileAdapter.TAG;


public class Utils {

    Context context;

    Utils(Context c) {
        this.context = c;
    }

    private Object response;

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {
        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }
        Log.d(TAG, "setBadgeCount:-----> "+ count);
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
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

    public static String initCapitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public static int getRandomDarkColor() {
        return Color.argb(255, (int) (Math.floor(Math.random() * 128) + 50), (int) (Math.floor(Math.random() * 128) + 50), (int) (Math.floor(Math
                .random() * 128) + 50));
    }

    public String getSessionId(HttpTransportSE ht) {
        try {
            SoapObject soapLoginRequest = new SoapObject(AppCredentials.INSTANCE.getNAMESPACE(), "apiLoginCall");
            SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(soapLoginRequest);
            //		soapLoginRequest.addProperty("username", soapUserName);
            JSONObject jo = new JSONObject();
            jo.put("apiKey", AppCredentials.INSTANCE.getSOAP_USER_NAME());
            jo.put("apiPassword", md5(AppCredentials.INSTANCE.getSOAP_PASSWORD()));
            SharedPreferences shared = context.getSharedPreferences(Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), Context.MODE_PRIVATE);
            SharedPreferences configShared = context.getSharedPreferences("configureView", Context.MODE_PRIVATE);
            Boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
            if (isLoggedIn) {
                jo.put("customer_id", shared.getString("customerId", ""));
            }
            jo.put("language", configShared.getString("storeCode", ""));
            jo.put("currency", configShared.getString("currencyCode", ""));
            Log.d("Jo", jo + "");
            soapLoginRequest.addProperty("attributes", jo + "");
            try {
                ht.call(AppCredentials.INSTANCE.getNAMESPACE(), envelope);
            } catch (IOException ex) {
                Log.d("create account ",
                        "Io exception bufferedIOStream closed" + ex);
            }

            response = envelope.getResponse();
            Log.d(getClass().getName() + "debug", response + "");
            jo = new JSONObject((String) response);

            return jo.getString("session_id");
        } catch (Exception ex) {
            Log.d("Utils Exception", "In generating Session Id" + ex);
            return "no";
        }
    }

    private final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.xsd = SoapSerializationEnvelope.XSD;
        envelope.enc = SoapSerializationEnvelope.ENC;
        envelope.setOutputSoapObject(request);
        return envelope;
    }

    public static boolean isPackageExisted(String targetPackage, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}