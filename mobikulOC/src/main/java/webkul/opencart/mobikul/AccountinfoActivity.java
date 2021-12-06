package webkul.opencart.mobikul;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.model.BaseModel.BaseModel;
import webkul.opencart.mobikul.model.GDPRStatus.GdprModel;
import webkul.opencart.mobikul.model.MyAccountModel.MyAccount;
import webkul.opencart.mobikul.networkManager.RetrofitCallback;
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback;
import webkul.opencart.mobikul.utils.AppSharedPreference;
import webkul.opencart.mobikul.utils.MakeToast;
import webkul.opencart.mobikul.utils.SweetAlertBox;
import webkul.opencart.mobikul.databinding.ActivityAccountinfoBinding;

public class AccountinfoActivity extends BaseActivity {

    private static final String TAG = AccountinfoActivity.class.getSimpleName();
    private ProgressBar spinner;
    private ProgressDialog progress;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    SharedPreferences configShared;
    private static final int CAMERA_REQUEST = 1888;
    private boolean isInternetAvailable = false;
    Editor editor;
    private SharedPreferences shared;
    private int isPasswordLayout;
    private String emailAddr;
    private String firstName;
    private String lastName;
    private ActivityAccountinfoBinding accountinfoBinding;
    private TextView title;
    private Callback<MyAccount> accountCallback;
    private Callback<BaseModel> baseModelCallback;
    private Callback<BaseModel> editPasswordCallback;
    private Callback<GdprModel> gdprStatus;

    public void isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        isInternetAvailable = !(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable());
    }

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
        if (!isInternetAvailable) {
            showDialog(this);
        } else {
            accountinfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_accountinfo);
            setToolbarLoginActivity((Toolbar) accountinfoBinding.toolbar.findViewById(R.id.toolbar));
            Drawable profileBackground = AppCompatResources.getDrawable(this, R.drawable.profile_background);
            accountinfoBinding.profileImage.setBackground(profileBackground);
            setSupportActionBar(getToolbarLoginActivity());
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            title = accountinfoBinding.toolbar.findViewById(R.id.title);
            title.setText(getString(R.string.accountinfo_action_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            LinearLayout dashboardContainer = (LinearLayout) accountinfoBinding.accinfoContainer;
            shared = getSharedPreferences("customerData", MODE_PRIVATE);
            Boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
            if (!isLoggedIn) {
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
            }
            accountCallback = new Callback<MyAccount>() {
                @Override
                public void onResponse(Call<MyAccount> call, Response<MyAccount> response) {
                    if (SweetAlertBox.Companion.getSweetAlertDialog() != null) {
                        SweetAlertBox.Companion.getSweetAlertDialog().dismissWithAnimation();
                    }
                    TextView accinfofirstnameValuetext = accountinfoBinding.accinfofirstnameValue;
                    accinfofirstnameValuetext.setText(response.body().getFirstname());
                    TextView accinfolastnameValuetext = accountinfoBinding.accinfolastnameValue;
                    accinfolastnameValuetext.setText(response.body().getLastname());
                    TextView accinfoemailValuetext = accountinfoBinding.accinfoemailValue;
                    accinfoemailValuetext.setText(response.body().getEmail());
                    TextView accinfoPhoneValue = accountinfoBinding.accinfoPhoneValue;
                    accinfoPhoneValue.setText(response.body().getTelephone());
                    TextView accinfoFaxValue = accountinfoBinding.accinfoFaxValue;
                    accinfoFaxValue.setText(response.body().getFax());
                    spinner = accountinfoBinding.accountinfoprogress;
                    spinner.setVisibility(View.GONE);
                    if (AppSharedPreference.INSTANCE.getGdprStatus(AccountinfoActivity.this).equals("1") && response.body().getGdprStatus() == 1) {
                        accountinfoBinding.checkboxGdpr.setVisibility(View.VISIBLE);
                        setSpannable(response.body().getGdprContent());
                        accountinfoBinding.changePasswordInfo.setVisibility(View.GONE);
                    } else {
                        accountinfoBinding.changePasswordInfo.setVisibility(View.GONE);
                        accountinfoBinding.checkboxGdpr.setVisibility(View.GONE);
                    }
                    LinearLayout accinfoContainer = accountinfoBinding.accinfoContainer;
                    accinfoContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<MyAccount> call, Throwable t) {
                }
            };
            gdprStatus = new Callback<GdprModel>() {
                @Override
                public void onResponse(Call<GdprModel> call, Response<GdprModel> response) {
                    if (SweetAlertBox.Companion.getSweetAlertDialog() != null) {
                        SweetAlertBox.Companion.getSweetAlertDialog().dismissWithAnimation();
                    }
                    if (AppSharedPreference.INSTANCE.getGdprStatus(AccountinfoActivity.this).equals("1") && response.body().getMobikulGdprStatus() == 1) {
                        accountinfoBinding.checkboxGdpr.setVisibility(View.VISIBLE);
                        setSpannable(response.body().getMobikulGdprPasswordDescription());
                    } else {
                        accountinfoBinding.checkboxGdpr.setVisibility(View.GONE);
                    }
                    LinearLayout accinfoContainer = accountinfoBinding.accinfoContainer;
                    accinfoContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<GdprModel> call, Throwable t) {

                }
            };
            baseModelCallback = new Callback<BaseModel>() {
                @Override
                public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                    if (SweetAlertBox.Companion.getSweetAlertDialog() != null) {
                        SweetAlertBox.Companion.getSweetAlertDialog().dismissWithAnimation();
                    }
                    try {
                        if (response.body().getError() == 0) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("customerData", MODE_PRIVATE);
                            Editor editor = pref.edit();
                            editor.putString("customerEmail", emailAddr);
                            editor.putString("customerName", firstName + " " + lastName);
                            editor.apply();
                            Intent intent_name = new Intent(AccountinfoActivity.this, MainActivity.class);
                            intent_name.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent_name.putExtra("pos", 3);
                            finish();
                            startActivity(intent_name);
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(AccountinfoActivity.this, R.style.AlertDialogTheme)
                                    .setTitle(getResources().getString(R.string.Error)).setPositiveButton(getResources().getString(R.string.dialog_ok),
                                            new OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                            alert.setMessage(response.body().getMessage()).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BaseModel> call, Throwable t) {

                }
            };

            if (getIntent().getExtras().containsKey("changeAccountInfo")) {
                accountinfoBinding.passwrodLayout.setVisibility(View.GONE);
                accountinfoBinding.accinfoContainer1.setVisibility(View.VISIBLE);
                isPasswordLayout = 0;
                new SweetAlertBox().showProgressDialog(AccountinfoActivity.this);
                RetrofitCallback.INSTANCE.myAccountCall(AccountinfoActivity.this,
                        new RetrofitCustomCallback(accountCallback, AccountinfoActivity.this));
            } else {
                title.setText(getString(R.string.change_password));
                accountinfoBinding.email.setVisibility(View.GONE);
                accountinfoBinding.passwrodLayout.setVisibility(View.VISIBLE);
                accountinfoBinding.accinfoContainer1.setVisibility(View.GONE);
                accountinfoBinding.accountinfoprogress.setVisibility(View.GONE);
                isPasswordLayout = 1;
                new SweetAlertBox().showProgressDialog(AccountinfoActivity.this);
                RetrofitCallback.INSTANCE.getGdprStatusCalling(AccountinfoActivity.this,
                        new RetrofitCustomCallback(gdprStatus, AccountinfoActivity.this));
            }
        }
        editPasswordCallback = new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (SweetAlertBox.Companion.getSweetAlertDialog() != null) {
                    SweetAlertBox.Companion.getSweetAlertDialog().dismissWithAnimation();
                }
                try {
                    if (response.body().getError() == 0) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                        Intent intent_name = new Intent();
                        intent_name.setClass(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(intent_name);
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(AccountinfoActivity.this, R.style.AlertDialogTheme).setTitle(getResources().getString(R.string.Error))
                                .setPositiveButton(getResources().getString(R.string.dialog_ok),
                                        new OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                        alert.setMessage(response.body().getMessage()).show();
//                        if (getResponseObject().has("message"))
//                            alert.setMessage(getResponseObject().getString("message")).show();
//                        else
//                            alert.setMessage(getResponseObject().getString("warning")).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Log.d(TAG, "onResponse: " + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {

            }
        };
    }


    private void setSpannable(String msg) {
        Log.d(TAG, "setSpannable: message " + msg);
        String gdprValue = getResources().getString(R.string.gdpr_check_value);
        Spannable ss = new SpannableString(gdprValue);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Dialog mDialog = new Dialog(AccountinfoActivity.this);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.setContentView(R.layout.terms_and_conditions_text);
                WebView webView = mDialog.findViewById(R.id.webView);
                webView.getSettings().setDisplayZoomControls(true);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mDialog.findViewById(R.id.container).getLayoutParams();
                layoutParams = new RelativeLayout.LayoutParams(webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                        webkul.opencart.mobikul.helper.Utils.getDeviceScrenHeight());
                mDialog.findViewById(R.id.container).setLayoutParams(layoutParams);
                mDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                try {
                    webView.loadData(msg, "text/html; charset=UTF-8", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDialog.show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, 32, gdprValue.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        accountinfoBinding.checkboxGdpr.setText(ss);
        accountinfoBinding.checkboxGdpr.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void finish(View v) {
        finish();
    }

    public void clickCamera(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

    public void takePhoto(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void validate(View v) {
        //getting field values
        EditText firstNameField = accountinfoBinding.accinfofirstnameValue;
        firstName = firstNameField.getText().toString().trim();
        EditText lastNameField = accountinfoBinding.accinfolastnameValue;
        lastName = lastNameField.getText().toString().trim();
        EditText emailAddrField = accountinfoBinding.accinfoemailValue;
        emailAddr = emailAddrField.getText().toString().trim();
        EditText accinfoPhone = accountinfoBinding.accinfoPhoneValue;
        String accinfoPhoneValue = accinfoPhone.getText().toString().trim();
        EditText accinfoFax = accountinfoBinding.accinfoFaxValue;
        String accinfoFaxValue = accinfoFax.getText().toString().trim();
        String telephone = accountinfoBinding.accinfoPhoneValue.getText().toString();
        EditText newPasswordField = accountinfoBinding.accinfonewpasswordValue;
        EditText confirmationField = accountinfoBinding.accinfonewconfirmpasswordValue;
        String newPassword = null, confirmation = null;
        Boolean isFormValidated = true, confirmationPasswordFilled = false, passwordFilled = false;
        if (isPasswordLayout == 0) {
            if (firstName.matches("")) {
                accountinfoBinding.firstname.setError(accountinfoBinding.firstname.getHint() + " " + getResources().getString(R.string.is_require_text));
                isFormValidated = false;
            } else
                accountinfoBinding.firstname.setError(null);
            if (lastName.matches("")) {
                accountinfoBinding.lastname.setError(accountinfoBinding.lastname.getHint() + " " + getResources().getString(R.string.is_require_text));
                isFormValidated = false;
            } else
                accountinfoBinding.lastname.setError(null);
            if (emailAddr.matches("")) {
                accountinfoBinding.email.setError(accountinfoBinding.email.getHint() + " " + getResources().getString(R.string.is_require_text));
                isFormValidated = false;
            } else if (!EMAIL_PATTERN.matcher(emailAddr).matches()) {
                accountinfoBinding.email.setError(getResources().getString(R.string.enter_valid_email));
                isFormValidated = false;
            } else
                accountinfoBinding.email.setError(null);
            if (telephone.matches("")) {
                accountinfoBinding.phone.setError(getString(R.string.telephone_is_required));
                accountinfoBinding.phone.requestFocus();
                isFormValidated = false;
            }
            if (accountinfoBinding.checkboxGdpr.getVisibility() == View.VISIBLE && !accountinfoBinding.checkboxGdpr.isChecked()) {
                MakeToast.Companion.getInstance().longToast(this, "please select GDPR options.");
                new SweetAlertBox().showErrorPopUp(this, "Error", "please select GDPR options.");
                isFormValidated = false;
            }
        } else {
            newPassword = newPasswordField.getText().toString().trim();
            confirmation = confirmationField.getText().toString().trim();

            if (newPassword.matches("")) {
                accountinfoBinding.password.setError(getResources().getString(R.string.new_password) + " " +
                        getResources().getString(R.string.is_require_text));
                isFormValidated = false;
            } else if (newPassword.length() < 4) {
                accountinfoBinding.password.setError(getResources().getString(R.string.new_password_length_check));
                isFormValidated = false;
            } else {
                passwordFilled = true;
                accountinfoBinding.password.setError(null);
            }

            if (confirmation.matches("")) {
                accountinfoBinding.confirmpassword.setError(getResources().getString(R.string.confirm_password) + " " + getResources().getString(R.string.is_require_text));
                isFormValidated = false;
            } else {
                confirmationPasswordFilled = true;
                accountinfoBinding.confirmpassword.setError(null);
            }
            if (passwordFilled && confirmationPasswordFilled) {
                if (!newPassword.equals(confirmation)) {
                    accountinfoBinding.confirmpassword.setError(getResources().getString(R.string.password_shud_match));
                    isFormValidated = false;
                } else
                    accountinfoBinding.confirmpassword.setError(null);
            }

            if (accountinfoBinding.checkboxGdpr.getVisibility() == View.VISIBLE && !accountinfoBinding.checkboxGdpr.isChecked()) {
                MakeToast.Companion.getInstance().longToast(this, "please select GDPR options.");
                new SweetAlertBox().showErrorPopUp(this, "Error", "please select GDPR options.");
                isFormValidated = false;
            }

            Log.d(TAG, "validate: GDPR Status " + accountinfoBinding.checkboxGdpr.isChecked());
//		}
        }
        if (isFormValidated) {
            try {
                new SweetAlertBox().showProgressDialog(AccountinfoActivity.this);
                if (isPasswordLayout == 0) {
                    JSONObject jo = new JSONObject();
                    jo.put("firstname", firstName);
                    jo.put("lastname", lastName);
                    jo.put("email", emailAddr);
                    jo.put("telephone", accinfoPhoneValue);
                    jo.put("fax", accinfoFaxValue);
                    RetrofitCallback.INSTANCE.accountInfo(AccountinfoActivity.this, firstName, lastName, emailAddr, accinfoPhoneValue, accinfoFaxValue, new RetrofitCustomCallback(baseModelCallback, AccountinfoActivity.this));
                } else {
                    JSONObject jo = new JSONObject();
                    Log.d(TAG, "validate: " + newPassword);
                    jo.put("password", newPassword);
                    RetrofitCallback.INSTANCE.editPassword(AccountinfoActivity.this, newPassword, new RetrofitCustomCallback(editPasswordCallback, AccountinfoActivity.this));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            accountinfoBinding.profileImage.setImageBitmap(photo);
        }
    }

    public void editCustomerResponse(String backresult) {
        try {
            Log.d(TAG, "editCustomerResponse: " + backresult);
            setResponseObject(new JSONObject(backresult));
            progress.dismiss();
            if (getResponseObject().getString("error").equalsIgnoreCase("0")) {
                Toast.makeText(getApplicationContext(), getResponseObject().getString("message"),
                        Toast.LENGTH_LONG).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("customerData", MODE_PRIVATE);
                Editor editor = pref.edit();
                editor.putString("customerEmail", emailAddr);
                editor.putString("customerName", firstName + " " + lastName);
                editor.apply();
                Intent intent_name = new Intent(AccountinfoActivity.this, MainActivity.class);
                intent_name.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent_name.putExtra("pos", 3);
                finish();
                startActivity(intent_name);
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(AccountinfoActivity.this, R.style.AlertDialogTheme).setTitle(getResources().getString(R.string.Error))
                        .setPositiveButton(getResources().getString(R.string.dialog_ok),
                                new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                alert.setMessage(getResponseObject().getJSONObject("message").getString("warning")).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editPasswordResponse(String backresult) {
        try {
            setResponseObject(new JSONObject(backresult));
            progress.dismiss();
            if (getResponseObject().getString("error").equalsIgnoreCase("0")) {
                Toast.makeText(getApplicationContext(), getResponseObject().getString("message"),
                        Toast.LENGTH_LONG).show();
                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent_name);
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(AccountinfoActivity.this, R.style.AlertDialogTheme).setTitle(getResources().getString(R.string.Error))
                        .setPositiveButton(getResources().getString(R.string.dialog_ok),
                                new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                if (getResponseObject().has("message"))
                    alert.setMessage(getResponseObject().getString("message")).show();
                else
                    alert.setMessage(getResponseObject().getString("warning")).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
