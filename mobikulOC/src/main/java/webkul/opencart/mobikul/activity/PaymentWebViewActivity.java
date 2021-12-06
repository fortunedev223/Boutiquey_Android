package webkul.opencart.mobikul.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.BaseActivity;
import webkul.opencart.mobikul.OrderPlaceActivity;
import webkul.opencart.mobikul.R;
import webkul.opencart.mobikul.model.ConfirmOrderModel.ConfirmOrder;
import webkul.opencart.mobikul.networkManager.RetrofitCallback;
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback;
import webkul.opencart.mobikul.utils.SweetAlertBox;


public class PaymentWebViewActivity extends BaseActivity {

    private static final String TAG = PaymentWebViewActivity.class.getSimpleName();
    // to avoid WindowLeaked by ProgressDialog
    boolean isFirstCall = true;
    private Intent intent;
    private WebView webView;
    Toolbar toolbar;
    TextView title;
    private ProgressDialog progress;
    private ProgressBar mProgressbar;
    private Callback<ConfirmOrder> confirmOrderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_web_view);
        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.make_payment));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.webView);
        intent = getIntent();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        webView.setWebViewClient(new MyBrowser());
        progress = ProgressDialog.show(PaymentWebViewActivity.this, null, getResources().getString(R.string.loading), true);
        String postData = "";
//        try {
//            postData = "wk_token=" + URLEncoder.encode(AppSharedPreference.INSTANCE.getWkToken(PaymentWebViewActivity.this, Constants.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME()),"UTF-8")+"&xyz="+
//                    URLEncoder.encode(getIntent().getStringExtra("type"),"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Log.d(TAG, "onCreate: append url----- "+postData);
//        webView.postUrl(removeAMP(intent.getStringExtra("long_url")), postData.getBytes());
        webView.loadUrl(removeAMP(intent.getStringExtra("long_url")));
        Log.d(TAG, "onCreate: fail_url -- " + getIntent().getStringExtra("fail_url"));
        Log.d(TAG, "onCreate: success_url -- " + getIntent().getStringExtra("success_url"));
        confirmOrderCallback = new Callback<ConfirmOrder>() {
            public void onResponse(Call<ConfirmOrder> call, Response<ConfirmOrder> response) {
                SweetAlertBox.Companion.dissmissSweetAlertBox();
                if (response.body().getError() == 1) {
                    Toast.makeText(PaymentWebViewActivity.this, getString(R.string.order_cancel), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Intent i = new Intent(PaymentWebViewActivity.this, OrderPlaceActivity.class);
                    i.putExtra("heading", response.body().getSuccess().getHeadingTitle());
                    i.putExtra("message", response.body().getSuccess().getTextMessage());
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ConfirmOrder> call, Throwable t) {
                SweetAlertBox.Companion.dissmissSweetAlertBox();
                Log.d(TAG, "onFailure: " + t.toString());
            }
        };
    }

    private String removeAMP(String url) {
        if (url.contains("amp;")) {
            return url.replace("amp;", "");
        } else {
            return url;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SweetAlertBox.Companion.dissmissSweetAlertBox();
        progress.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        progress.dismiss();
    }

    private class MyBrowser extends WebViewClient {
        private String str;

        //        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            Log.d("URL", "shouldOverrideUrlLoading:--------> "+request.toString());
//            if (request.toString().contains("route=mobikul/cpay/success")||request.toString().contains("route=mobikul/cpay/fail")) {
//                Intent intent = new Intent();
//                intent.putExtra("redirect_url", request.toString());
//                PaymentWebViewActivity.this.setResult(111, intent);
//                PaymentWebViewActivity.this.finish();
//            }else{
//                str = request.toString();
//                view.loadUrl(request.toString());
//                return true;
//            }
//            return true;
//        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap facIcon) {
            Log.d(PaymentWebViewActivity.class.getSimpleName(), "onPageStarted: ------------>" + url);
//            if (getIntent().hasExtra("success_url") && getIntent().getStringExtra("success_url").equals(url)) {
//                webView.stopLoading();
//                new SweetAlertBox().showProgressDialog(PaymentWebViewActivity.this);
//                RetrofitCallback.INSTANCE.confirmOrderCall(PaymentWebViewActivity.this, "1", new RetrofitCustomCallback(confirmOrderCallback, PaymentWebViewActivity.this));
//            } else if (getIntent().hasExtra("fail_url") && getIntent().getStringExtra("fail_url").equals(url)) {
//                webView.stopLoading();
//                new SweetAlertBox().showProgressDialog(PaymentWebViewActivity.this);
//                RetrofitCallback.INSTANCE.confirmOrderCall(PaymentWebViewActivity.this, "0", new RetrofitCustomCallback(confirmOrderCallback, PaymentWebViewActivity.this));
//            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (progress != null)
                progress.dismiss();
            isFirstCall = false;
            Log.d(TAG, "onPageFinished: " + url);
            if (getIntent().hasExtra("success_url") && getIntent().getStringExtra("success_url").equals(url)) {
                new SweetAlertBox().showProgressDialog(PaymentWebViewActivity.this);
                RetrofitCallback.INSTANCE.confirmOrderCall(PaymentWebViewActivity.this, "1", new RetrofitCustomCallback(confirmOrderCallback, PaymentWebViewActivity.this));
            } else if (getIntent().hasExtra("fail_url") && getIntent().getStringExtra("fail_url").equals(url)) {
                new SweetAlertBox().showProgressDialog(PaymentWebViewActivity.this);
                RetrofitCallback.INSTANCE.confirmOrderCall(PaymentWebViewActivity.this, "0", new RetrofitCustomCallback(confirmOrderCallback, PaymentWebViewActivity.this));
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        SweetAlertBox.Companion.dissmissSweetAlertBox();
    }


    @Override
    public boolean onCreateOptionsMenu(@NotNull Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_cart).setVisible(false);
        menu.findItem(R.id.action_bell).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme);
        alert.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        alert.setNegativeButton(getResources().getString(android.R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alert.setMessage(getString(R.string.not_approve)).show();
    }

}
