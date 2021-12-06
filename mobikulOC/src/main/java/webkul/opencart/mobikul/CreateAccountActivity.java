package webkul.opencart.mobikul;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.content.res.AppCompatResources;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.activity.DashBoard;
import webkul.opencart.mobikul.handlers.CreateAccountHandler;
import webkul.opencart.mobikul.model.RegisterModel.RagisterData;
import webkul.opencart.mobikul.networkManager.RetrofitCallback;
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback;
import webkul.opencart.mobikul.twoWayBindingModel.RegisterAccountModel;
import webkul.opencart.mobikul.databinding.ActivityCreateAccountBinding;


public class CreateAccountActivity extends BaseActivity {

    private static final String TAG = CreateAccountActivity.class.getSimpleName();
    Object response = null, customerCreateAccountResponse = null;
    private ProgressBar spinner;
    Spinner stateDropdown;
    private String country_id, state_id;
    JSONObject mainObject;
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private boolean isInternetAvailable;
    public Editor editor;
    private int radioId;
    protected int groupId;
    private int countryPosition = 0;
    private int statePosition = 0;
    private ActionBar actionBar;
    private ActivityCreateAccountBinding createAccountBinding;
    private TextView title;
    private CreateAccountHandler accountHandler;
    private RegisterAccountModel accountModel;
    private Callback<RagisterData> ragisterDataCallback;
    public static Boolean REDIRECT = false;
    private boolean becomeSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnline();
        if (!isInternetAvailable) {
            showDialog(this);
        } else {
            createAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);
            setToolbarLoginActivity(createAccountBinding.toolbar.findViewById(R.id.toolbar));
            if (getIntent().hasExtra("redirect")) {
                REDIRECT = true;
            }
            if (!BuildConfig.isMobikul) {
                becomeSeller = true;
                createAccountBinding.becomeSeller.setVisibility(View.VISIBLE);
            }
            accountHandler = new CreateAccountHandler(CreateAccountActivity.this);
            accountModel = new RegisterAccountModel(CreateAccountActivity.this, createAccountBinding);
            setSupportActionBar(getToolbarLoginActivity());
            actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
            createAccountBinding.mainContainer.setVisibility(View.GONE);
            spinner = createAccountBinding.progress;
            stateDropdown = createAccountBinding.statesSpinner;
            title = createAccountBinding.toolbar.findViewById(R.id.title);
            title.setText(getString(R.string.register));
            createAccountBinding.setData(accountModel);
            createAccountBinding.setHandler(accountHandler);
            accountHandler.getBinding(createAccountBinding);
            Drawable profileBackground = AppCompatResources.getDrawable(CreateAccountActivity.this, R.drawable.profile_background);
            createAccountBinding.profileImage.setBackground(profileBackground);
            RadioGroup subscribeGroup = createAccountBinding.isSubscribed;
            subscribeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radioId = checkedId;
                    accountHandler.getRadioId(radioId);
                }
            });
            RadioGroup groupIdRadio = createAccountBinding.groupId;
            groupIdRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    groupId = checkedId;
                    accountHandler.getGroupId(groupId);
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getToolbarLoginActivity().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            SharedPreferences shared = getSharedPreferences("customerData", MODE_PRIVATE);
            Boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
            if (isLoggedIn) {
                Intent intent = new Intent(this, DashBoard.class);
                this.startActivity(intent);
            }
            ragisterDataCallback = new Callback<RagisterData>() {
                @Override
                public void onResponse(Call<RagisterData> call, Response<RagisterData> response) {
                    registerAccountResponse(response.body());
                }

                @Override
                public void onFailure(Call<RagisterData> call, Throwable t) {

                }
            };
            RetrofitCallback.INSTANCE.registerCalling(this, new RetrofitCustomCallback<>(ragisterDataCallback, this));
        }
    }

    private String countryId;

    public void isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        isInternetAvailable = !(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable());
    }

    public ActivityCreateAccountBinding getCreateAccountBinding() {
        return createAccountBinding;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void registerAccountResponse(RagisterData backresult) {
        try {
            if (backresult.getBecomeSeller() && becomeSeller) {
                getCreateAccountBinding().becomeSeller.setText(getString(R.string.become_a_seller));
                getCreateAccountBinding().becomeSeller.setVisibility(View.VISIBLE);
            }
            getCreateAccountBinding().becomeSeller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (getCreateAccountBinding().becomeSeller.isChecked()) {
                        getCreateAccountBinding().storeName.setVisibility(View.VISIBLE);
                    } else {
                        getCreateAccountBinding().storeName.setVisibility(View.GONE);
                    }
                }
            });
            if (backresult.getStoreCountryId() != null) {
                countryId = backresult.getStoreCountryId();
            }
            String privatePolicy = getResources().getString(R.string.i_have_read_and_agree_to_the_privacy_policy_);
            SpannableString ss = new SpannableString(privatePolicy);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    final Dialog mDialog = new Dialog(CreateAccountActivity.this);
                    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    mDialog.setContentView(R.layout.terms_and_conditions_text);
                    WebView webView = (WebView) mDialog.findViewById(R.id.webView);
                    webView.getSettings().setDisplayZoomControls(true);
                    mDialog.findViewById(R.id.container).setLayoutParams(new RelativeLayout.LayoutParams(
                            webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                            webkul.opencart.mobikul.helper.Utils.getDeviceScrenHeight()));
                    mDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });
                    try {
                        webView.loadData(backresult.getAgreeInfo().getData().getDescription(), "text/html; charset=UTF-8", null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mDialog.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });
                    mDialog.show();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(clickableSpan, 29, privatePolicy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            CheckBox tAndC = createAccountBinding.tAndC;
            tAndC.setText(ss);
            tAndC.setMovementMethod(LinkMovementMethod.getInstance());
            try {
                String[] countries = new String[backresult.getCountryData().size()];
                for (int i = 0; i < backresult.getCountryData().size(); i++) {
                    countries[i] = backresult.getCountryData().get(i).getName();
                    String code = backresult.getCountryData().get(i).getCountryId();
                    if (countryId != null) {
                        if (countryId.equals(code)) {
                            countryPosition = i;
                        }
                    }
                }

                Spinner dropdown = createAccountBinding.countrySpinner;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateAccountActivity.this, android.R.layout.simple_spinner_dropdown_item, countries);
                dropdown.setAdapter(adapter);
                dropdown.setSelection(countryPosition);
                dropdown.setOnItemSelectedListener(new OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                        try {
                            country_id = backresult.getCountryData().get(position).getCountryId();
                            accountHandler.getCountryId(country_id);
                            if (!(backresult.getCountryData().get(position).getZone().size() == 0)) {
                                String[] states = new String[backresult.getCountryData().get(position).getZone().size()];
                                for (int i = 0; i < backresult.getCountryData().get(position).getZone().size(); i++) {
                                    states[i] = backresult.getCountryData().get(position).getZone().get(i).getName();
                                    String stateCode = backresult.getCountryData().get(position).getZone().get(i).getZoneId();
                                }

                                final int temp = position;
                                stateDropdown.setOnItemSelectedListener(new OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                                        try {
                                            state_id = backresult.getCountryData().get(temp).getZone().get(position).getZoneId();
                                            accountHandler.getSateID(state_id);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(
                                            AdapterView<?> parent) {
                                        Log.d("jsonErrorStates", "Inside state dropDown");
                                    }
                                });
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                        (CreateAccountActivity.this, android.R.layout.simple_spinner_dropdown_item, states);
                                stateDropdown.setAdapter(adapter);
                                stateDropdown.setSelection(statePosition);
                                statePosition = 0;
                            } else {
                                String[] states = {"None"};
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateAccountActivity.this, android.R.layout.simple_spinner_dropdown_item, states);
                                stateDropdown.setAdapter(adapter);
                                state_id = "0";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("jsonErrorCountry", "Inside dropDown" + e);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            spinner.setVisibility(View.GONE);
            createAccountBinding.mainContainer.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        hideKeyword(this);
        super.onBackPressed();
    }

}
