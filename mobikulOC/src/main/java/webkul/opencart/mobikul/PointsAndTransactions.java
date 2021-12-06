package webkul.opencart.mobikul;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.model.RewardDataModels.RewardData;
import webkul.opencart.mobikul.model.TransactionInfo.TransactionInfoDataModel;
import webkul.opencart.mobikul.networkManager.RetrofitCallback;
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback;
import webkul.opencart.mobikul.utils.SweetAlertBox;
import webkul.opencart.mobikul.databinding.PointAndTransactionDetailsBinding;
import webkul.opencart.mobikul.databinding.TableRowLayoutBinding;

public class PointsAndTransactions extends BaseActivity {

    private static final String TAG = PointsAndTransactions.class.getSimpleName();
    public Editor editor;
    private LinearLayout ptContainer;
    Object response;
    ProgressBar spinner;
    private ActionBar actionBar;
    PointAndTransactionDetailsBinding detailsBinding;
    private TextView title;

    @Override
    protected void onResume() {
        if (getItemCart() != null) {

            SharedPreferences customerDataShared = getSharedPreferences("customerData", MODE_PRIVATE);
            LayerDrawable icon = (LayerDrawable) getItemCart().getIcon();
            // Update LayerDrawable's BadgeDrawable
            Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"));
        }
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnline();
        if (!isInternetAvailable()) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            showDialog(this);
        } else {
            detailsBinding = DataBindingUtil.setContentView(this, R.layout.point_and_transaction_details);
            setToolbarLoginActivity((Toolbar) detailsBinding.toolbar.findViewById(R.id.toolbar));
            setSupportActionBar(getToolbarLoginActivity());
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            ptContainer = detailsBinding.ptContainer;
            ptContainer.setVisibility(View.GONE);
            setShared(getSharedPreferences("customerData", MODE_PRIVATE));
            JSONObject jo = new JSONObject();
            title = (TextView) detailsBinding.toolbar.findViewById(R.id.title);
            try {
                jo.put("page", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (getIntent().getExtras().containsKey("Points")) {
                detailsBinding.heading.setText(R.string.your_reward_points);
                title.setText(R.string.your_reward_points);
//                new VolleyConnection(this).getResponse(Request.Method.POST, "getRewardInfo", jo.toString());

                Callback<RewardData> rewardDataModelCallback = new Callback<RewardData>() {
                    @Override
                    public void onResponse(Call<RewardData> call, Response<RewardData> response) {
                        Log.d(TAG, "onResponse: " + response.body().rewardsTotal);
                        SweetAlertBox.Companion.dissmissSweetAlertBox();
                        getRewardInfoResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<RewardData> call, Throwable t) {

                    }
                };
                new SweetAlertBox().showProgressDialog(this);
                RetrofitCallback.INSTANCE.getRewardPoint(this, "1", new RetrofitCustomCallback<>(rewardDataModelCallback, this));
                detailsBinding.TableHeading3.setText(R.string.points);
            } else {
                detailsBinding.heading.setText(R.string.your_transactions);
                title.setText(R.string.your_transactions);

                Callback<TransactionInfoDataModel> transactionInfoDataModelCallback = new Callback<TransactionInfoDataModel>() {
                    @Override
                    public void onResponse(Call<TransactionInfoDataModel> call, Response<TransactionInfoDataModel> response) {
                        Log.d(TAG, "onResponse: " + response.body().getTransactionsTotal());
                        SweetAlertBox.Companion.dissmissSweetAlertBox();
                        getTransactionInfoResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<TransactionInfoDataModel> call, Throwable t) {

                    }
                };
//                new SweetAlertBox().showProgressDialog(this,"loading","");
                RetrofitCallback.INSTANCE.getTransactionInfo(this, "1", new RetrofitCustomCallback<>(transactionInfoDataModelCallback, this));
//                new VolleyConnection(this).getResponse(Request.Method.POST, "getTransactionInfo", jo.toString());
                detailsBinding.TableHeading3.setText(R.string.amount_usd_);
            }
        }
    }

    public void getRewardInfoResponse(RewardData backresult) {
        try {
            Log.d(TAG, "getRewardInfoResponse: " + backresult);
//            setResponseObject(new JSONObject(backresult));
            if (backresult.getError() == 0) {
                Log.d("rewardText", backresult.rewardText + "");
                String str = webkul.opencart.mobikul.helper.Utils.fromHtml(backresult.rewardText).toString();
                str = str.substring(0, str.lastIndexOf(":"));
                detailsBinding.subHeading.setText(Html.fromHtml(str + ": " + backresult.totalPoints));
                for (int i = 0; i < backresult.rewardData.size(); i++) {
                    TableRowLayoutBinding child = TableRowLayoutBinding.inflate(getLayoutInflater());
                    detailsBinding.tableLayout.addView(child.getRoot());
                    TextView tableData1 = child.tableData1;
                    tableData1.setText(backresult.rewardData.get(i).date_added);
                    tableData1.setPadding(5, 5, 5, 5);
                    TextView tableData2 = child.tableData2;
                    tableData2.setText(backresult.rewardData.get(i).description);
                    tableData2.setPadding(5, 5, 5, 5);
                    TextView tableData3 = child.tableData3;
                    tableData3.setText(backresult.rewardData.get(i).points);
                    tableData3.setPadding(5, 5, 5, 5);
                }
            } else {
                detailsBinding.errorTv.setText(backresult.getMessage());
                detailsBinding.errorTv.setVisibility(View.VISIBLE);
                detailsBinding.dataContainer.setVisibility(View.GONE);
            }
            spinner = detailsBinding.progress;
            spinner.setVisibility(View.GONE);
            ptContainer.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            spinner = detailsBinding.progress;
            spinner.setVisibility(View.GONE);
            ptContainer.setVisibility(View.VISIBLE);
            Log.d("Exception", e.toString());
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_bell).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    public void getTransactionInfoResponse(TransactionInfoDataModel backresult) {
        try {
            if (backresult.getError() == 0) {
                String str = Html.fromHtml(backresult.getTransactionText()).toString();
                str = str.substring(0, str.lastIndexOf(":"));
                detailsBinding.subHeading.setText(str + " " + backresult.getTotalBalance());
                for (int i = 0; i < backresult.getTransactionData().size(); i++) {
                    TableRowLayoutBinding child = TableRowLayoutBinding.inflate(getLayoutInflater());
                    detailsBinding.tableLayout.addView(child.getRoot());
                    TextView tableData1 = child.tableData1;
                    tableData1.setText(backresult.getTransactionData().get(i).getDate_added());
                    tableData1.setPadding(5, 5, 5, 5);
                    TextView tableData2 = child.tableData2;
                    tableData2.setText(backresult.getTransactionData().get(i).getDescription());
                    tableData2.setPadding(5, 5, 5, 5);
                    TextView tableData3 = child.tableData3;
                    tableData3.setText(backresult.getTransactionData().get(i).getAmount());
                    tableData3.setPadding(5, 5, 5, 5);
                }
            } else {
                detailsBinding.errorTv.setText(backresult.getMessage());
                detailsBinding.errorTv.setVisibility(View.VISIBLE);
                detailsBinding.dataContainer.setVisibility(View.GONE);
            }
            spinner = detailsBinding.progress;
            spinner.setVisibility(View.GONE);
            ptContainer.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.d("Exception", e.toString());
            spinner = detailsBinding.progress;
            spinner.setVisibility(View.GONE);
            ptContainer.setVisibility(View.VISIBLE);
        }
    }
}
