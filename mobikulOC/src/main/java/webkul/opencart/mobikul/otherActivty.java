package webkul.opencart.mobikul;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import webkul.opencart.mobikul.helper.Constant;
import webkul.opencart.mobikul.databinding.ActivityOtherBinding;

public class otherActivty extends BaseActivity {
    String title;
    String shortDiscription;
    private Bundle extras;
    ActivityOtherBinding otherBinding;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (extras.containsKey("isNotification")) {
            Intent i = new Intent(otherActivty.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    @Override
    protected void onResume() {
        if (getItemCart() != null) {

            SharedPreferences customerDataShared = getSharedPreferences(Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), MODE_PRIVATE);
            LayerDrawable icon = (LayerDrawable) getItemCart().getIcon();
            // Update LayerDrawable's BadgeDrawable
            Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"));
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == android.R.id.home) {
            setConfigShared(getSharedPreferences("configureView", MODE_PRIVATE));
            if (!getConfigShared().getBoolean("isMainCreated", false)) {
                Intent i = new Intent(otherActivty.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else {
                onBackPressed();
            }
//				 Intent intent = NavUtils.getParentActivityIntent(this);
//				 Toast.makeText(getApplicationContext(), NavUtils.getParentActivityIntent(BaseActivity.this)+"", Toast.LENGTH_SHORT)
//						.show();
//				 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				 NavUtils.navigateUpTo(this, intent);
//				 NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        otherBinding = DataBindingUtil.setContentView(this, R.layout.activity_other);
        setToolbarLoginActivity((Toolbar) otherBinding.toolbar.findViewById(R.id.toolbar));
        setSupportActionBar(getToolbarLoginActivity());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        extras = getIntent().getExtras();
        title = extras.getString("title");
        shortDiscription = extras.getString("shortDiscription");
        TextView notificationTitle = (TextView) otherBinding.notificationOthersTitle;
        WebView myWebView = new WebView(this);
        TextView shortDescriptionNotify = (TextView) otherBinding.shortDescriptionNotifyOthers;
        shortDescriptionNotify.setVisibility(View.GONE);
        try {
            String mime = "text/html";
            String encoding = "utf-8";
            myWebView.setFocusable(true);
            myWebView.loadDataWithBaseURL(null, webkul.opencart.mobikul.helper.Utils.fromHtml(shortDiscription.toString()).toString(), mime, encoding, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("shortDiscription-->", webkul.opencart.mobikul.helper.Utils.fromHtml(shortDiscription.toString()).toString() + "");
        ((LinearLayout) otherBinding.layout).addView(myWebView);
        notificationTitle.setText(title);
//		shortDescriptionNotify.setText(Html.fromHtml(Html.fromHtml(shortDiscription.toString()).toString()));

		/* new changes i have done */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}


