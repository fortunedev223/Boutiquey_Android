package webkul.opencart.mobikul.connection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import webkul.opencart.mobikul.helper.Constant;
import webkul.opencart.mobikul.R;
import webkul.opencart.mobikul.SplashScreen;
import webkul.opencart.mobikul.credentials.AppCredentials;

public class MobikulMakeConnection extends AsyncTask<String, String, Object> {
    JSONObject mainObject;
    Context mContext;
    SharedPreferences configShared;
    public Object customerLoginResponse;
    public Editor editor;
    String returnFunctionName;

    public MobikulMakeConnection(Context context) {
        mContext = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        configShared = mContext.getSharedPreferences(Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), Context.MODE_PRIVATE);
    }

    @Override
    protected Object doInBackground(String... arguments) {
        final String apiFunctionName = arguments[0];
        String jsonString = arguments[1];
        returnFunctionName = apiFunctionName + "Response";
        Log.d("url", AppCredentials.INSTANCE.getURL() + "");
        try {
            HttpTransportSE ht = getHttpTransportSE();
            if (configShared.getString("SESSION_ID", "Session_Not_Loggin").equalsIgnoreCase("Session_Not_Loggin")) {
                editor = configShared.edit();
                String session_id = SplashScreen.Companion.getSessionObj().getSessionId(ht);
                editor.putString("SESSION_ID", session_id);
                editor.apply();
                Log.d("session", "login");
            }

            // request for customer login
            SoapObject customerLoginRequest = new SoapObject(AppCredentials.INSTANCE.getNAMESPACE(),apiFunctionName);
            SoapSerializationEnvelope requestEnvelop = getSoapSerializationEnvelope(customerLoginRequest);

            JSONObject jo = new JSONObject(jsonString);
            if (jo.has("page")) {
                if (!jo.getString("page").equalsIgnoreCase("1"))
                    returnFunctionName = returnFunctionName + "LazyLoad";
            }
            jo.put("session_id", configShared.getString("SESSION_ID", "Session_Not_Loggin"));
            jsonString = jo.toString();
            PropertyInfo stringArrayPropertyInfo = new PropertyInfo();
            stringArrayPropertyInfo.setName("attributes");
            stringArrayPropertyInfo.setValue(jsonString);
            stringArrayPropertyInfo.setType(jsonString.getClass());

            Log.d("Name", apiFunctionName + "");
            Log.d("JSONData---->", jsonString + "");
            customerLoginRequest.addProperty(stringArrayPropertyInfo);

            try {
                ht.call(AppCredentials.INSTANCE.getNAMESPACE(),requestEnvelop);
            } catch (IOException ex) {
                Log.d(mContext.getClass().getName(), "Io exception bufferedIOStream closed" + ex);
                this.cancel(true);
                errorRetryDialog(mContext, apiFunctionName, jsonString.toString());
                ex.printStackTrace();
                return "no";
            }
            customerLoginResponse = requestEnvelop.getResponse();
            String customerLoginResponseAsString = customerLoginResponse.toString();
            mainObject = new JSONObject(customerLoginResponseAsString);
            Log.d("Response in Connection", apiFunctionName + "-->" + mainObject.toString(4));

            if (mainObject.has("fault")) {
                Log.d("fault", "fault");
                editor = configShared.edit();
                editor.putString("SESSION_ID", "Session_Not_Loggin");
                editor.apply();
                this.cancel(true);
                new MobikulMakeConnection(mContext).execute(apiFunctionName, jsonString.toString());
                return "no";
            }
            return mainObject.toString();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
            e.printStackTrace();
            return "no";
        }
    }

    @Override
    protected void onPostExecute(Object backresult) {
        Method m;
        try {
            m = mContext.getClass().getDeclaredMethod(returnFunctionName, String.class);
            m.invoke(mContext, (String) backresult);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            try {
                m = mContext.getClass().getSuperclass().getDeclaredMethod(returnFunctionName, String.class);
                m.invoke(mContext, (String) backresult);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
    }

    private final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.xsd = SoapSerializationEnvelope.XSD;
        envelope.enc = SoapSerializationEnvelope.ENC;
        envelope.setOutputSoapObject(request);
        return envelope;
    }

    private final HttpTransportSE getHttpTransportSE() {
        HttpTransportSE ht = new HttpTransportSE(AppCredentials.INSTANCE.getURL(),60000);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }

    public void errorRetryDialog(final Context mContext, final String funName, final String params) {
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        new MobikulMakeConnection(mContext).execute(funName, params);
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


  /*  public void onVolleyError(final Context mContext, final String funName, final String params,IOException error) {
        String msg;
        Activity activityObj = (Activity) mContext;
        activityObj.setContentView(R.layout.connection_not_available);
//        activityObj.finish();
        if (error instanceof AuthFailureError || error instanceof ServerError || error instanceof ParseError) {
            ((ImageView) activityObj.findViewById(R.id.imageView)).setBackgroundResource(R.drawable.img_maintenance);
        } else if (error instanceof NoConnectionError || error instanceof NetworkError) {
            activityObj.setContentView(R.layout.connection_not_available);
        } else if (error instanceof TimeoutError) {
//            msg = getString(R.string.timeout);
        } else {
            ((ImageView) activityObj.findViewById(R.id.imageView)).setBackgroundResource(R.drawable.img_maintenance);
        }
    }*/
}