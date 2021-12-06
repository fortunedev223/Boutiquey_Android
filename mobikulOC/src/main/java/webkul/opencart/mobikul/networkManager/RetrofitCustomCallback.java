package webkul.opencart.mobikul.networkManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.ApiLoginModel;
import webkul.opencart.mobikul.Cart;
import webkul.opencart.mobikul.helper.Constant;
import webkul.opencart.mobikul.helper.NetworkIssue;
import webkul.opencart.mobikul.MainActivity;
import webkul.opencart.mobikul.R;
import webkul.opencart.mobikul.utils.AppSharedPreference;
import webkul.opencart.mobikul.model.BaseModel.BaseModel;
import webkul.opencart.mobikul.utils.SweetAlertBox;


public class RetrofitCustomCallback<Z extends BaseModel> implements Callback<Z> {

    private Callback mcallback;
    private Context mContext;
    private static Call oldCall;

    public RetrofitCustomCallback(Callback callback, Context mcontext) {
        mcallback = callback;
        this.mContext = mcontext;
    }

    @Override
    public void onResponse(Call<Z> call, Response<Z> response) {
        oldCall = call;
        if (((Activity) mContext).isDestroyed()) {
            call.cancel();
            Log.d("Retrofit_Canceled", "Cancel called");
            return;
        }
        Log.d("HEADER", "onResponse: :--->" + response.headers());
        if (response.body() != null) {
            if (response.body().getFault() == 1) {
                AppSharedPreference.INSTANCE.editSharedPreference(mContext, Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(),
                        Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_KEY_WK_TOKEN(), "1");
                Log.d("RetroFit_CustomCallback", "onResponse:--------> " + response.body().getMessage());
                RetrofitCallback.INSTANCE.apiLoginCall(mContext, new Callback<ApiLoginModel>() {
                    @Override
                    public void onResponse(Call<ApiLoginModel> call, Response<ApiLoginModel> response) {
                        if (response.body().getFault() == 1) {
                            Log.d("RetroFit_CustomCall", "onResponse:-----------> " + "Fault");
                        } else {
                            AppSharedPreference.INSTANCE.editSharedPreference(mContext, Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(),
                                    Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_KEY_WK_TOKEN(), response.body().getWkToken().toString());
                            AppSharedPreference.INSTANCE.setStoreCode(mContext, response.body().getLanguage());
                            AppSharedPreference.INSTANCE.setCurrencyCode(mContext, response.body().getCurrency());
                            if (oldCall != null) {
                                Log.d("RetroFit_CustomCall", "onResponse:-----------> " + "CloneCall");
                                oldCall.clone().enqueue(new RetrofitCustomCallback(mcallback, mContext));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiLoginModel> call, Throwable t) {
                        Log.d("RetroFitCustorm", "onFail---------->" + t.toString());
                        mcallback.onFailure(call, t);
                    }
                });
            } else {
                if (response.body().getError() == 1 && response.body().getRedirect() != null && response.body().getRedirect().equalsIgnoreCase("cart")) {
                    ((Activity) mContext).finish();
                    mContext.startActivity(new Intent(mContext, Cart.class));
                } else {
                    mcallback.onResponse(call, response);
                }
            }
        } else {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText(mContext.getString(R.string.warning))
                    .setTitleText(mContext.getString(R.string.warning))
                    .setContentText(mContext.getResources().getString(R.string.something_went_wrong))
                    .setConfirmText(mContext.getResources().getString(R.string.dialog_ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);
                        }
                    })
                    .show();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100);
            params.setMargins(8, 8, 8, 8);
            Button confirmButton = sweetAlertDialog.findViewById(R.id.confirm_button);
            Button cancelButton = sweetAlertDialog.findViewById(R.id.cancel_button);
            confirmButton.setLayoutParams(params);
            cancelButton.setLayoutParams(params);
            sweetAlertDialog.setCancelable(false);
        }
    }

    @Override
    public void onFailure(Call<Z> call, Throwable t) {
        if (((Activity) mContext).isDestroyed()) {
            call.cancel();
            return;
        }
        Log.d("Exception", t.toString());
        mcallback.onFailure(call, t);
    }

}


