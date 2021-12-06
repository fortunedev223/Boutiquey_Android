package webkul.opencart.mobikul;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.text.Html;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.helper.Constant;
import webkul.opencart.mobikul.model.BaseModel.BaseModel;
import webkul.opencart.mobikul.model.EditAddressBookModel.EditAddressbook;
import webkul.opencart.mobikul.model.GDPRStatus.GdprModel;
import webkul.opencart.mobikul.networkManager.RetrofitCallback;
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback;
import webkul.opencart.mobikul.utils.AppSharedPreference;
import webkul.opencart.mobikul.utils.SweetAlertBox;
import webkul.opencart.mobikul.utils.Validation;
import webkul.opencart.mobikul.databinding.ActivityNewAddressBinding;

public class NewAddressForm extends BaseActivity implements LocationListener {

    private static final String TAG = NewAddressForm.class.getSimpleName();
    public Editor editor;
    ProgressBar spinner;
    Spinner stateDropdown;
    String country_id, state_id;
    boolean isFormValidated = true;
    Intent intent;
    String addressId = null;
    int margin;
    LinearLayout newAddrdataContainer;
    SharedPreferences configShared;
    int radioId;
    boolean isNewAddress = false;
    int countryPosition = 0, statePosition = 0;
    ActionBar actionBar;
    Geocoder geocoder;
    List<Address> addresses;
    private boolean isInternetAvailable;
    private double latitude;
    private double longitude;
    private String country;
    private String state;
    private TextView firstName;
    private TextView lastName;
    private TextView company;
    private TextView street1;
    private TextView zip;
    private TextView cityTv;
    private TextView street2;
    private Callback<EditAddressbook> addressBookCallback;
    private Callback<GdprModel> gdprStatus;
    private Spinner countryDropdown;
    private LocationManager locationManager;
    private ActivityNewAddressBinding newAddressBinding;
    private EditAddressbook addressBook;
    private TextView title;
    private final int newAddressReturn = 201;
    private int setShippinResult;
    private String countryId;
    private Callback<BaseModel> addAddressCallback;

    @Override
    public void onBackPressed() {
        hideKeyword(this);
        setResult(newAddressReturn);
        super.onBackPressed();
    }

    public void isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        isInternetAvailable = !(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable());
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnline();
        if (!isInternetAvailable) {
            showDialog(this);
        } else {
            newAddressBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_address);
            setToolbarLoginActivity(newAddressBinding.toolbar.findViewById(R.id.toolbar));
            setSupportActionBar(getToolbarLoginActivity());
            actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            String text1;
            text1 = "<B>" + newAddressBinding.addBookCountryTitle.getText().toString() + "<B>" + "<font color=#FF2107>*</font>";
            newAddressBinding.addBookCountryTitle.setText(Html.fromHtml(text1));
            text1 = "<B>" + newAddressBinding.addBookStateTitle.getText().toString() + "<B>" + "<font color=#FF2107>*</font>";
            newAddressBinding.addBookStateTitle.setText(Html.fromHtml(text1));
            countryDropdown = newAddressBinding.countrySpinner;
            stateDropdown = newAddressBinding.statesSpinner;
            intent = getIntent();
            if (intent.hasExtra("addressId")) {
                addressId = intent.getStringExtra("addressId");
                title = (TextView) newAddressBinding.toolbar.findViewById(R.id.title);
                title.setText(getString(R.string.edit_address));
            } else {
                isNewAddress = true;
                title = (TextView) newAddressBinding.toolbar.findViewById(R.id.title);
                title.setText(getString(R.string.add_new_address));
            }

            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey("activity_title")) {
                    getSupportActionBar().setTitle(getResources().getString(R.string.add_new_address));
                }
            }


            addressBookCallback = new Callback<EditAddressbook>() {
                @Override
                public void onResponse(Call<EditAddressbook> call, Response<EditAddressbook> response) {
                    addressBook = response.body();
                    SweetAlertBox.Companion.dissmissSweetAlertBox();
                    if (addressBook.getCountryId() != null) {
                        countryId = addressBook.getCountryId();
                    }

                    if (AppSharedPreference.INSTANCE.getGdprStatus(NewAddressForm.this).equals("1") && response.body().getGdprStatus() == 1) {
                        newAddressBinding.checkboxGdpr.setVisibility(View.VISIBLE);
                        setSpannable(response.body().getGdprContent());
                        newAddressBinding.changePasswordInfo.setVisibility(View.GONE);
                    } else {
                        newAddressBinding.changePasswordInfo.setVisibility(View.GONE);
                        newAddressBinding.checkboxGdpr.setVisibility(View.GONE);
                    }

                    if (addressBook.getData() != null) {
                        newAddressBinding.addBookfirstnameValue.setText(addressBook.getData().getFirstname());
                        newAddressBinding.addBooklastnameValue.setText(addressBook.getData().getLastname());
                        newAddressBinding.addBookCompanyValue.setText(addressBook.getData().getCompany());
                        newAddressBinding.addBookStreetAddValue.setText(addressBook.getData().getAddress1());
                        newAddressBinding.addBookStreetAddSecondValue.setText(addressBook.getData().getAddress2());
                        newAddressBinding.addBookCityValue.setText(addressBook.getData().getCity());
                        newAddressBinding.addBookZipValue.setText(addressBook.getData().getPostcode());
                    }
                    if (addressBook.getDefault() != null) {
                        if (addressBook.getDefault() == 1) {
                            newAddressBinding.yes.setChecked(true);
                        }
                    }
                    String[] countries = new String[addressBook.getCountryData().size()];
                    for (int i = 0; i < addressBook.getCountryData().size(); i++) {
                        countries[i] = addressBook.getCountryData().get(i).getName();
                        String code = addressBook.getCountryData().get(i).getCountryId();
                        if (addressBook.getData() != null) {
                            if (addressBook.getData().getCountryId().equals(code)) {
                                countryPosition = i;
                            }
                        }
                        if (countryId != null) {
                            if (countryId.equals(code)) {
                                countryPosition = i;
                            }
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(NewAddressForm.this, android.R.layout.simple_spinner_dropdown_item, countries);
                    countryDropdown.setAdapter(adapter);
                    countryDropdown.setSelection(countryPosition);
                    countryPosition = 0;
                    countryDropdown.setOnItemSelectedListener(new OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                            try {
                                country_id = addressBook.getCountryData().get(position).getCountryId();
                                if (!(addressBook.getCountryData().get(position).getZone().size() == 0)) {
                                    String[] states = new String[addressBook.getCountryData().get(position).getZone().size()];
                                    for (int i = 0; i < addressBook.getCountryData().get(position).getZone().size(); i++) {
                                        states[i] = addressBook.getCountryData().get(position).getZone().get(i).getName();
                                        String stateCode = addressBook.getCountryData().get(position).getZone().get(i).getZoneId();
                                        Log.d("ZoneID", "onItemSelected:-----> " + states[i]);
                                        if (states[i].equalsIgnoreCase(state))
                                            statePosition = i;
                                        if (addressBook.getData() != null) {
                                            Log.d("NewAddress", "onItemSelected: :----------->" + addressBook.getData().getZoneId() + stateCode);
                                            if (stateCode.equals(addressBook.getData().getZoneId())) {
                                                statePosition = i;
                                            }
                                        }
                                    }

                                    final int temp = position;
                                    stateDropdown.setOnItemSelectedListener(new OnItemSelectedListener() {
                                        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                                            state_id = addressBook.getCountryData().get(temp).getZone().get(position).getZoneId();
                                        }

                                        @Override
                                        public void onNothingSelected(
                                                AdapterView<?> parent) {
                                            Log.d("jsonErrorStates", "Inside state dropDown");
                                        }
                                    });

                                    ArrayAdapter<String> adapter = new ArrayAdapter<>
                                            (NewAddressForm.this, android.R.layout.simple_spinner_dropdown_item, states);
                                    stateDropdown.setAdapter(adapter);
                                    stateDropdown.setSelection(statePosition);
                                    statePosition = 0;
                                } else {
                                    String[] states = {"None"};
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(NewAddressForm.this, android.R.layout.simple_spinner_dropdown_item, states);
                                    stateDropdown.setAdapter(adapter);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("Error", "Inside dropDown" + e);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                        }
                    });
                }

                @Override
                public void onFailure(Call<EditAddressbook> call, Throwable t) {

                }
            };
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
                newAddressBinding.fetchAddressButton.setVisibility(View.GONE);
            }
            firstName = newAddressBinding.addBookfirstnameValue;
            lastName = newAddressBinding.addBooklastnameValue;
            company = newAddressBinding.addBookCompanyValue;
            street1 = newAddressBinding.addBookStreetAddValue;
            street2 = newAddressBinding.addBookStreetAddSecondValue;
            cityTv = newAddressBinding.addBookCityValue;
            zip = newAddressBinding.addBookZipValue;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            margin = (int) (10 * getResources().getDisplayMetrics().density);
            newAddrdataContainer = newAddressBinding.addrDataContainer;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(margin, margin, margin, margin);
            newAddrdataContainer.setLayoutParams(layoutParams);
            CardView newAddrContainer = newAddressBinding.newAddrContainer;
            newAddrContainer.setVisibility(View.VISIBLE);
            JSONObject jo = new JSONObject();
            if (!isNewAddress)
                try {
                    jo.put("address_id", addressId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            new SweetAlertBox().showProgressDialog(NewAddressForm.this);
            RetrofitCallback.INSTANCE.addAddressBook(NewAddressForm.this, addressId, new RetrofitCustomCallback(addressBookCallback, this));
        }
    }

    private void setSpannable(String msg) {
        Log.d(TAG, "setSpannable: message " + msg);
        String gdprValue = getResources().getString(R.string.gdpr_check_value);
        Spannable ss = new SpannableString(gdprValue);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Dialog mDialog = new Dialog(NewAddressForm.this);
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
        newAddressBinding.checkboxGdpr.setText(ss);
        newAddressBinding.checkboxGdpr.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void getYourLocation(View v) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Log.d("TAG", "onCreate: No GPS");
                buildAlertMessageNoGps();
            }
            Log.d("TAG", "onCreate: ");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Log.d("TAG", "onCreate: No GPS");
                        buildAlertMessageNoGps();
                    }
                    Log.d("TAG", "onCreate: ");
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        Snackbar snackbar = Snackbar.make(getCurrentFocus(),
                                getResources().getString(R.string.allow_permission_setting), Snackbar.LENGTH_LONG);
                        snackbar.setAction(getResources().getString(R.string.settings), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (this == null) {
                                    return;
                                }
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        });
                        snackbar.show();
                    }
                }
                break;

            default:
                break;
        }
    }

    public void saveData(View v) {
        EditText firstNameField = newAddressBinding.addBookfirstnameValue;
        String firstName = firstNameField.getText().toString().trim();
        EditText lastNameField = newAddressBinding.addBooklastnameValue;
        String lastName = lastNameField.getText().toString().trim();
        EditText companyField = newAddressBinding.addBookCompanyValue;
        String company = companyField.getText().toString().trim();
        EditText streetAdd1Field = newAddressBinding.addBookStreetAddValue;
        String streetAdd1 = streetAdd1Field.getText().toString().trim();
        EditText streetAdd2Field = newAddressBinding.addBookStreetAddSecondValue;
        String streetAdd2 = streetAdd2Field.getText().toString().trim();
        EditText cityField = newAddressBinding.addBookCityValue;
        String city = cityField.getText().toString().trim();
        EditText zipField = newAddressBinding.addBookZipValue;
        String zip = zipField.getText().toString().trim();
        String state;
        int defaultAddress = 0;
        isFormValidated = true;
        state = state_id;

        if (radioId == R.id.yes) {
            defaultAddress = 1;
        }
        if (newAddressBinding.yes.isChecked()) {
            defaultAddress = 1;
        }
        if (firstName.matches("") || Validation.isEmoji(firstName)) {
            firstNameField.requestFocus();
            firstNameField.setError(getResources().getString(R.string.fname_is_required));
            isFormValidated = false;
        } else if (firstName.length() < 1 || firstName.length() > 32) {
            firstNameField.requestFocus();
            firstNameField.setError(getResources().getString(R.string.first_name_length));
            isFormValidated = false;
        } else if (lastName.matches("") || Validation.isEmoji(lastName)) {
            lastNameField.requestFocus();
            lastNameField.setError(getResources().getString(R.string.lname_is_required));
            isFormValidated = false;
        } else if (lastName.length() < 1 || lastName.length() > 32) {
            lastNameField.requestFocus();
            lastNameField.setError(getResources().getString(R.string.last_name_length));
            isFormValidated = false;
        } else if (streetAdd1.matches("") || Validation.isEmoji(streetAdd1)) {
            streetAdd1Field.requestFocus();
            streetAdd1Field.setError(getResources().getString(R.string.street_address_is_required));
            isFormValidated = false;
        } else if (streetAdd1.length() < 3 || streetAdd1.length() > 128) {
            streetAdd1Field.requestFocus();
            streetAdd1Field.setError(getResources().getString(R.string.address_length));
            isFormValidated = false;
        } else if (city.matches("") || Validation.isEmoji(city)) {
            cityField.requestFocus();
            cityField.setError(getResources().getString(R.string.city_address_is_required));
            isFormValidated = false;
        } else if (city.length() < 2 && city.length() > 128) {
            cityField.requestFocus();
            cityField.setError(getResources().getString(R.string.city_length));
            isFormValidated = false;
        } else if (zip.matches("") || Validation.isEmoji(zip)) {
            zipField.requestFocus();
            zipField.setError(getResources().getString(R.string.zip_is_required));
            isFormValidated = false;
        } else if (zip.length() < 2 || zip.length() > 10) {
            zipField.requestFocus();
            zipField.setError(getResources().getString(R.string.postcode_length));
            isFormValidated = false;
        } else if (newAddressBinding.checkboxGdpr.getVisibility() == View.VISIBLE && !newAddressBinding.checkboxGdpr.isChecked()) {
            SweetAlertBox.Companion.getInstance().showErrorPopUp(this, "Error", "Please select GDPR option.");
            isFormValidated = false;
        }

        if (isFormValidated) {
            JSONObject jo = new JSONObject();
            SharedPreferences customerDataShared = getSharedPreferences(Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), MODE_PRIVATE);
            try {
                jo.put("customer_id", customerDataShared.getString("customerId", ""));
                if (!isNewAddress)
                    jo.put("address_id", addressId);
                jo.put("firstname", firstName);
                jo.put("lastname", lastName);
                jo.put("company", company);
                jo.put("address_1", streetAdd1);
                jo.put("address_2", streetAdd2);
                jo.put("city", city);
                jo.put("zone_id", state);
                jo.put("postcode", zip);
                jo.put("country_id", country_id);
                jo.put("default", defaultAddress);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            addAddressCallback = new Callback<BaseModel>() {
                @Override
                public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                    SweetAlertBox.Companion.dissmissSweetAlertBox();
                    addAddressResponse(response.body());
                }

                @Override
                public void onFailure(Call<BaseModel> call, Throwable t) {
                    t.getStackTrace();
                }

            };
            new SweetAlertBox().showProgressDialog(NewAddressForm.this);
            if (!isNewAddress) {
                RetrofitCallback.INSTANCE.addAddress(this, customerDataShared.getString("customerId", ""),
                        addressId, firstName, lastName, company, streetAdd1, streetAdd2, city, state, zip, country_id, defaultAddress,
                        new RetrofitCustomCallback<BaseModel>(addAddressCallback, this));
            } else {
                RetrofitCallback.INSTANCE.addAddress(this, customerDataShared.getString("customerId", ""),
                        addressId, firstName, lastName, company, streetAdd1, streetAdd2, city, state, zip, country_id, defaultAddress,
                        new RetrofitCustomCallback<BaseModel>(addAddressCallback, this));
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        try {
            Log.d("location", latitude + "-------" +
                    longitude);
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            zip.setText(postalCode);
            street1.setText(address);
            cityTv.setText(city);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < countryDropdown.getAdapter().getCount(); i++) {
            if (countryDropdown.getAdapter().getItem(i).toString().equalsIgnoreCase(country))
                countryDropdown.setSelection(i);
        }
        newAddressBinding.fetchAddressButton.setEnabled(false);
        newAddressBinding.fetchAddressButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
        newAddressBinding.fetchAddressButton.setBackgroundResource(R.color.light_gray_color1);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void addAddressResponse(BaseModel backresult) {
        try {
            Log.d(TAG, "addAddressResponse: " + backresult);

            AlertDialog.Builder alert = new AlertDialog.Builder(NewAddressForm.this, R.style.AlertDialogTheme).setTitle(getResources().getString(R.string.message))
                    .setPositiveButton(getResources().getString(R.string.ok), new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    try {
                                        if (backresult.getError() == 0) {
                                            setResult(Activity.RESULT_OK);
                                        } else {
                                            setResult(Activity.RESULT_CANCELED);
                                        }
                                        NewAddressForm.this.finish();
                                    } catch (Exception e) {
                                        Log.d("Erroe", "");
                                        NewAddressForm.this.finish();
                                    }
                                }
                            }
                    );
            alert.setMessage(backresult.getMessage()).show();
        } catch (Exception e) {
            Log.d("Exception on post", e.toString());
            e.printStackTrace();
        }
    }

}