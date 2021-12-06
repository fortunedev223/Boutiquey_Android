package webkul.opencart.mobikul;


import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import webkul.opencart.mobikul.databinding.ActivityOrderPlacedBinding;

public class OrderPlaceActivity extends BaseActivity {
    private String incrementId, canReorder, heading, msg;
    private ActionBar actionBar;
    ActivityOrderPlacedBinding placedBinding;
    private TextView title;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        placedBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_placed);
        actionBar = getSupportActionBar();
        setToolbarLoginActivity(placedBinding.toolbar.findViewById(R.id.toolbar));
        setSupportActionBar(getToolbarLoginActivity());
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        heading = getIntent().getExtras().getString("heading");
        msg = getIntent().getExtras().getString("message");
        placedBinding.heading.setText(heading);
        placedBinding.msg.setText(Html.fromHtml(msg));
        title = (TextView) placedBinding.toolbar.findViewById(R.id.title);
        title.setText(getString(R.string.order_placed));
        SharedPreferences.Editor editor = getSharedPreferences("customerData", MODE_PRIVATE).edit();
        editor.putString("cartItems", "0");
        editor.apply();
        Button b = placedBinding.continue1;
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewOrder = new Intent(OrderPlaceActivity.this, MainActivity.class);
//                viewOrder.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                viewOrder.putExtra("updateHome", true);
                startActivity(viewOrder);
                OrderPlaceActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_cart).setVisible(false);
        menu.findItem(R.id.action_bell).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);
        return true;
    }


}
