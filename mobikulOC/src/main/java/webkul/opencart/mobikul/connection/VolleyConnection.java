package webkul.opencart.mobikul.connection;

/**
 * Created by aman.gupta on 29/12/16.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import webkul.opencart.mobikul.R;
import webkul.opencart.mobikul.credentials.AppCredentials;

public class VolleyConnection {
    public Object customerLoginResponse;
    public SharedPreferences.Editor editor;
    Context mContext;
    SharedPreferences configShared;
    String returnFunctionName;
    private String url;
    String TAG = VolleyConnection.class.getSimpleName();

    public VolleyConnection(Context context) {
        mContext = context;
        configShared = mContext.getSharedPreferences("customerData", Context.MODE_PRIVATE);
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
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

    public void getResponse(final int method, final String apiName, final String data) {
        returnFunctionName = apiName + "Response";
        url = getURL(apiName);
        try {
            Log.d(TAG, " getResponse: Request ==== " + data);
            final JSONObject requestData = new JSONObject(data);
            if (requestData.has("page")) {
                if (!requestData.getString("page").equalsIgnoreCase("1"))
                    returnFunctionName = returnFunctionName + "LazyLoad";
            }
            editor = configShared.edit();
            if (configShared.getString("wk_token", "Session_Not_Loggin").equalsIgnoreCase("Session_Not_Loggin")) {
                JSONObject jo = new JSONObject();
                try {
                    jo.put("apiKey", AppCredentials.INSTANCE.getSOAP_USER_NAME());
                    jo.put("apiPassword", md5(AppCredentials.INSTANCE.getSOAP_PASSWORD()));
                    SharedPreferences configShared = mContext.getSharedPreferences("configureView", Context.MODE_PRIVATE);
                    Boolean isLoggedIn = (configShared.getBoolean("isLoggedIn", false));
                    if (isLoggedIn) {
                        jo.put("customer_id", configShared.getString("customerId", ""));
                    }
                    jo.put("language", configShared.getString("storeCode", ""));
                    jo.put("currency", configShared.getString("currencyCode", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("JO", " Request ==== " + jo + "");
                new GetVolleyResponse(mContext).getResponse(Request.Method.POST, AppCredentials.INSTANCE.getURL() + "common/" + "apiLogin", jo, new GetVolleyResponse.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        JSONObject res = null;
                        try {
                            res = new JSONObject(result);
                            configShared.edit().clear().apply();
                            Log.d("Response", res + "");
                            if (res.has("wk_token")) {
                                editor.putString("wk_token", res.getString("wk_token"));
                            } else
                                editor.putString("wk_token", "Session_Not_Loggin");
                            editor.apply();
                            requestData.put("wk_token", configShared.getString("wk_token", "Session_Not_Loggin"));
                            Log.d("data", requestData + "");
                            new GetVolleyResponse(mContext).getResponse(method, url, requestData, new GetVolleyResponse.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        Log.d("Response", result + "");
                                        JSONObject res = new JSONObject(result);
                                        if (res.has("fault") && res.getInt("fault") == 1) {
                                            editor.putString("wk_token", "Session_Not_Loggin");
                                            editor.apply();
                                            getResponse(method, apiName, data);
                                        } else
                                            responseReturn(result);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                requestData.put("wk_token", configShared.getString("wk_token", "Session_Not_Loggin"));
                Log.d("data", requestData + "");
                new GetVolleyResponse(mContext).getResponse(method, url, requestData, new GetVolleyResponse.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            Log.d("Response", result + "");
                            JSONObject res = new JSONObject(result);
                            if ((res.has("fault") && res.getInt("fault") == 1)) {
                                editor.putString("wk_token", "Session_Not_Loggin");
                                editor.apply();
                                getResponse(method, apiName, data);
                            } else
                                responseReturn(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getURL(String apiName) {
        if (apiName.equalsIgnoreCase("getProductCall") || apiName.equalsIgnoreCase("productCategory")
                || apiName.equalsIgnoreCase("writeProductReview") || apiName.equalsIgnoreCase("addToWishlist")
                || apiName.equalsIgnoreCase("productSearch") || apiName.equalsIgnoreCase("getNotifications")
                || apiName.equalsIgnoreCase("viewNotifications") || apiName.equalsIgnoreCase("productManufacturer")
                || apiName.equalsIgnoreCase("manufacturerInfo") || apiName.equalsIgnoreCase("searchSuggest")
                || apiName.equalsIgnoreCase("customCollection"))
            url = AppCredentials.INSTANCE.getURL() + "catalog/" + apiName;
        else if (apiName.equalsIgnoreCase("addToCart") || apiName.equalsIgnoreCase("viewCart") ||
                apiName.equalsIgnoreCase("removeFromCart") || apiName.equalsIgnoreCase("updateCart") ||
                apiName.equalsIgnoreCase("applyCoupon") || apiName.equalsIgnoreCase("applyVoucher") ||
                apiName.equalsIgnoreCase("applyPoints"))
            url = AppCredentials.INSTANCE.getURL() + "cart/" + apiName;
        else if (apiName.equalsIgnoreCase("customerLogin") || apiName.equalsIgnoreCase("customerLogout")
                || apiName.equalsIgnoreCase("myAccount") || apiName.equalsIgnoreCase("editCustomer")
                || apiName.equalsIgnoreCase("editPassword") || apiName.equalsIgnoreCase("getAddresses")
                || apiName.equalsIgnoreCase("getAddress") || apiName.equalsIgnoreCase("addAddress")
                || apiName.equalsIgnoreCase("deleteAddress") || apiName.equalsIgnoreCase("registerAccount")
                || apiName.equalsIgnoreCase("addCustomer") || apiName.equalsIgnoreCase("getOrders")
                || apiName.equalsIgnoreCase("getOrderInfo") || apiName.equalsIgnoreCase("getRewardInfo")
                || apiName.equalsIgnoreCase("getTransactionInfo") || apiName.equalsIgnoreCase("getReturns")
                || apiName.equalsIgnoreCase("getReturnInfo") || apiName.equalsIgnoreCase("getDownloadInfo")
                || apiName.equalsIgnoreCase("getRecurrings") || apiName.equalsIgnoreCase("getRecurringInfo")
                || apiName.equalsIgnoreCase("getNewsletter") || apiName.equalsIgnoreCase("setNewsletter")
                || apiName.equalsIgnoreCase("forgotPassword") || apiName.equalsIgnoreCase("downloadProduct")
                || apiName.equalsIgnoreCase("setNewsletter") || apiName.equalsIgnoreCase("setNewsletter")
                || apiName.equalsIgnoreCase("setNewsletter") || apiName.equalsIgnoreCase("setNewsletter")
                || apiName.equalsIgnoreCase("getWishlist")
                || apiName.equalsIgnoreCase("setNewsletter") || apiName.equalsIgnoreCase("removeFromWishlist"))
            url = AppCredentials.INSTANCE.getURL() + "customer/" + apiName;
        else if (apiName.equalsIgnoreCase("checkout") || apiName.equalsIgnoreCase("confirmOrder"))
            url = AppCredentials.INSTANCE.getURL() + "checkout/" + apiName;
        else if (apiName.equals("getWalletHistory") || apiName.equals("addMoney") || apiName.equals("getWalletBalance") || apiName.equals("customerImage") || apiName.equals("addSocialCustomer")) {
            url = AppCredentials.INSTANCE.getURL() + "customer/" + apiName;
        } else if (apiName.equalsIgnoreCase("getSellerProfile") || apiName.equalsIgnoreCase("getSellerOrders")
                || apiName.equalsIgnoreCase("getDashbordData") || apiName.equalsIgnoreCase("addHistory")
                || apiName.equalsIgnoreCase("writeReview") || apiName.equalsIgnoreCase("contactSeller")
                || apiName.equalsIgnoreCase("getSellData") || apiName.equalsIgnoreCase("getSellPage"))
            url = AppCredentials.INSTANCE.getURL() + "marketplace/" + apiName;
        else
            url = AppCredentials.INSTANCE.getURL() + "common/" + apiName;
        return url;
    }

    private void responseReturn(String result) {
        Method m;
        try {
            m = mContext.getClass().getDeclaredMethod(returnFunctionName, String.class);
            m.invoke(mContext, (String) result);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            try {
                m = mContext.getClass().getSuperclass().getDeclaredMethod(returnFunctionName, String.class);
                m.invoke(mContext, (String) result);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
    }

    public void errorRetryDialog(final Context mContext, final String funName, final String params) {
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };
        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(mContext.getResources().getString(R.string.intenet_unavailable))
                        .setPositiveButton(mContext.getResources().getString(R.string.retry), dialogClickListener)
                        .show();
            }
        });
    }

}
