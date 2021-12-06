package webkul.opencart.mobikul.connection;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aman.gupta on 15/1/16.
 */


public class GetVolleyResponse {

    static int VOLLEY_ERROR = 1;
    RequestQueue queue;
    Context mCtx;

    public GetVolleyResponse(Context context) {
        mCtx = context;
//        new AppCredential(context);
    }

    public String getResponse(int method, final String url, final JSONObject jsonValue, final VolleyCallback callback) {
        queue = MySingleton.getInstance(mCtx).getRequestQueue();
        switch (method) {

            case Request.Method.GET:
            case Request.Method.DELETE: {
//                HttpsTrustManager.allowAllSSL();
                StringRequest strreq = new StringRequest(method, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        Log.d("url->", url + "");
                        callback.onSuccess(Response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        e.printStackTrace();
                        Log.d("url1=->", url + "");

//                        onVolleyError(e);
                    }
                }) {
                    @Override
                    public Map<String, String> getParams() {
                        Map<String, String> mParams = new HashMap<>();
                        try {
                            mParams.put("paramOne", jsonValue.getString("apiKey"));
                            mParams.put("paramTwo", jsonValue.getString("apiPassword"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return mParams;
                    }
                };
                strreq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                strreq.setShouldCache(false);
                MySingleton.getInstance(mCtx).addToRequestQueue(strreq);
            }
            break;
            case Request.Method.POST:
            case Request.Method.PUT:
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(method, url, jsonValue,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Success url->", url + "");
                                callback.onSuccess(response + "");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error url->", url + "");
                                error.printStackTrace();
                                JSONObject errorPost = new JSONObject();
                                try {
                                    errorPost.put("error", VOLLEY_ERROR);
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                                callback.onSuccess(errorPost.toString());
                            }
                        });
                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                jsObjRequest.setShouldCache(false);
                MySingleton.getInstance(mCtx).addToRequestQueue(jsObjRequest);
                break;
        }
        return "";
    }

    public interface VolleyCallback {
        void onSuccess(String result);
    }
}
