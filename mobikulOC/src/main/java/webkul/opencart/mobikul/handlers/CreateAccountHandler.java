package webkul.opencart.mobikul.handlers;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.CreateAccountActivity;
import webkul.opencart.mobikul.helper.Constant;
import webkul.opencart.mobikul.callback.CreateAccount;
import webkul.opencart.mobikul.model.AddCustomerModel.AddCustomer;
import webkul.opencart.mobikul.R;
import webkul.opencart.mobikul.networkManager.RetrofitCallback;
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback;
import webkul.opencart.mobikul.twoWayBindingModel.RegisterAccountModel;
import webkul.opencart.mobikul.utils.MakeToast;
import webkul.opencart.mobikul.utils.AppSharedPreference;
import webkul.opencart.mobikul.utils.SweetAlertBox;
import webkul.opencart.mobikul.utils.Validation;
import webkul.opencart.mobikul.databinding.ActivityCreateAccountBinding;


/**
 * Webkul Software. *
 *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
public class CreateAccountHandler implements CreateAccount {
    private Activity mcontext;
    private ActivityCreateAccountBinding createAccountBinding;
    private int is_subscribed;
    private boolean canScrollOnValidation;
    private String customerName;
    private String customerEmail;
    private Callback<AddCustomer> customerLoginCallback;
    private int group_id;
    private String country_id, state_id;
    private int groupId, radioId;

    public CreateAccountHandler(Activity mcontext) {
        this.mcontext = mcontext;
    }

    public void onClickRegisterAccount(View view, RegisterAccountModel model) {
        customerLoginCallback = new Callback<AddCustomer>() {
            @Override
            public void onResponse(Call<AddCustomer> call, Response<AddCustomer> response) {
                SweetAlertBox.Companion.dissmissSweetAlertBox();
                if (response.body().getError() == 1) {
                    new MakeToast().shortToast(mcontext, response.body().getMessage());
                } else {
                    AppSharedPreference.INSTANCE.editBooleanSharedPreference(mcontext, Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_KEY_IS_LOGGED_IN(), true);
                    AppSharedPreference.INSTANCE.editSharedPreference(mcontext, Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_ID(), String.valueOf(response.body().getCustomerId()));
                    AppSharedPreference.INSTANCE.editSharedPreference(mcontext, Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_EMAIL(), customerName);
                    AppSharedPreference.INSTANCE.editSharedPreference(mcontext, Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_NAME(), customerEmail);
                    AppSharedPreference.INSTANCE.setPatner(mcontext, response.body().getPartner());
                    new SweetAlertBox().showPopUp(mcontext, mcontext.getString(R.string.continue2),
                            response.body().getMessage(), mcontext.getResources()
                            .getString(R.string.register), CreateAccountActivity.REDIRECT);
                }
            }

            @Override
            public void onFailure(Call<AddCustomer> call, Throwable t) {

            }
        };
        EditText firstNameField = createAccountBinding.firstname;
        String firstName = firstNameField.getText().toString().trim();
        EditText lastNameField = createAccountBinding.lastname;
        String lastName = lastNameField.getText().toString().trim();
        EditText emailAddrField = createAccountBinding.emailAddress;
        String emailAddr = emailAddrField.getText().toString().trim();
        EditText telephoneField = createAccountBinding.telephone;
        String telephone = telephoneField.getText().toString().trim();
        EditText faxField = createAccountBinding.fax;
        String fax = faxField.getText().toString().trim();
        EditText companyField = createAccountBinding.addBookCompanyValue;
        String company = companyField.getText().toString().trim();
        EditText streetAdd1Field = createAccountBinding.addBookStreetAddValue;
        String streetAdd1 = streetAdd1Field.getText().toString().trim();
        EditText streetAdd2Field = createAccountBinding.addBookStreetAddSecondValue;
        String streetAdd2 = streetAdd2Field.getText().toString().trim();
        EditText cityField = createAccountBinding.addBookCityValue;
        String city = cityField.getText().toString().trim();
        EditText zipField = createAccountBinding.addBookZipValue;
        String zip = zipField.getText().toString().trim();
        EditText passwordField = createAccountBinding.password;
        String password = passwordField.getText().toString().trim();
        EditText confirmationField = createAccountBinding.confirmation;
        String confirmation = confirmationField.getText().toString().trim();
        CheckBox tAndC = createAccountBinding.tAndC;
        Boolean isChecked = tAndC.isChecked();
        Boolean isFormValidated = true, confirmationPasswordFilled = false, passwordFilled = false;
        int agreePolicy = 1;

        is_subscribed = 0;

        if (radioId == createAccountBinding.yes.getId()) {
            is_subscribed = 1;
        }

        if (groupId == createAccountBinding.yes1.getId()) {
            group_id = 1;
        }

        if (firstName.matches("") || Validation.isEmoji(firstName)) {
            createAccountBinding.firstname.requestFocus();
            createAccountBinding.firstname.setError(mcontext.getString(R.string.fname_is_required));
            scrollToView(createAccountBinding.registerScroll, firstNameField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.firstname, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (firstName.length() < 1 || firstName.length() > 32) {
            createAccountBinding.firstname.requestFocus();
            createAccountBinding.firstname.setError(mcontext.getString(R.string.first_name_length));
            scrollToView(createAccountBinding.registerScroll, firstNameField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.firstname, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();

        } else if (lastName.matches("") || Validation.isEmoji(lastName)) {
            createAccountBinding.lastname.requestFocus();
            createAccountBinding.lastname.setError(mcontext.getString(R.string.lname_is_required));
            scrollToView(createAccountBinding.registerScroll, lastNameField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.lastname, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (lastName.length() < 1 || lastName.length() > 32) {
            createAccountBinding.lastname.requestFocus();
            createAccountBinding.lastname.setError(mcontext.getString(R.string.last_name_length));
            scrollToView(createAccountBinding.registerScroll, lastNameField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.lastname, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (emailAddr.matches("")) {
            createAccountBinding.emailAddress.requestFocus();
            createAccountBinding.emailAddress.setError(mcontext.getResources().getString(R.string.email_is_required));
            scrollToView(createAccountBinding.registerScroll, emailAddrField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.emailAddress, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (!Validation.isEmailValid(emailAddr)) {
            createAccountBinding.emailAddress.requestFocus();
            createAccountBinding.emailAddress.setError(mcontext.getResources().getString(R.string.enter_valid_email));
            scrollToView(createAccountBinding.registerScroll, emailAddrField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.emailAddress, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (createAccountBinding.emailAddress.getTag() != null && createAccountBinding.emailAddress.getTag().toString().equals("1")) {
            createAccountBinding.emailAddress.requestFocus();
            createAccountBinding.emailAddress.setError(mcontext.getResources().getString(R.string.email_already_exit));
            scrollToView(createAccountBinding.registerScroll, emailAddrField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.emailAddress, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (emailAddr.length() > 96) {
            createAccountBinding.emailAddress.requestFocus();
            createAccountBinding.emailAddress.setError(mcontext.getResources().getString(R.string.email_length));
            scrollToView(createAccountBinding.registerScroll, emailAddrField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.emailAddress, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (!(telephone.length() > 3 && telephone.length() < 32)) {
            createAccountBinding.telephone.setError(mcontext.getResources().getString(R.string.number_error));
            createAccountBinding.telephone.requestFocus();
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.telephone,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (streetAdd1.matches("") || Validation.isEmoji(streetAdd1)) {
            createAccountBinding.addBookStreetAddValue.requestFocus();
            createAccountBinding.addBookStreetAddValue.setError(mcontext.getResources().getString(R.string.street_address_is_required));
            scrollToView(createAccountBinding.registerScroll, streetAdd1Field);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.addBookStreetAddValue,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (streetAdd1.length() < 3 || streetAdd1.length() > 128) {
            createAccountBinding.addBookStreetAddValue.requestFocus();
            createAccountBinding.addBookStreetAddValue.setError(mcontext.getResources().getString(R.string.address_length));
            scrollToView(createAccountBinding.registerScroll, streetAdd1Field);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.addBookStreetAddValue,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (city.matches("") || Validation.isEmoji(city)) {
            createAccountBinding.addBookCityValue.requestFocus();
            createAccountBinding.addBookCityValue.setError(mcontext.getResources().getString(R.string.city_address_is_required));
            scrollToView(createAccountBinding.registerScroll, cityField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.addBookCityValue,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (city.length() < 2 || city.length() > 128) {
            createAccountBinding.addBookCityValue.requestFocus();
            createAccountBinding.addBookCityValue.setError(mcontext.getResources().getString(R.string.city_length));
            scrollToView(createAccountBinding.registerScroll, cityField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.addBookCityValue,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();

        } else if (zip.matches("") || Validation.isEmoji(zip)) {
            zipField.requestFocus();
            zipField.setError(mcontext.getResources().getString(R.string.zip_is_required));
            scrollToView(createAccountBinding.registerScroll, zipField);
            isFormValidated = false;
        } else if (zip.length() < 1 || zip.length() > 10) {
            zipField.requestFocus();
            zipField.setError(mcontext.getResources().getString(R.string.postcode_length));
            scrollToView(createAccountBinding.registerScroll, zipField);
            isFormValidated = false;
        } else if (password.matches("")) {
            createAccountBinding.password.requestFocus();
            createAccountBinding.passwordTV.setError(mcontext.getResources().getString(R.string.password) + " "
                    + mcontext.getResources().getString(R.string.is_require_text));
            scrollToView(createAccountBinding.registerScroll, passwordField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.password,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (password.length() < 4) {
            createAccountBinding.password.requestFocus();
            createAccountBinding.passwordTV.setError(mcontext.getResources().getString(R.string.password_alert_password_length));
//            createAccountBinding.passwordTV.setPasswordVisibilityToggleEnabled(false);
//            createAccountBinding.confirmPasswordTv.setPasswordVisibilityToggleEnabled(false);
            scrollToView(createAccountBinding.registerScroll, passwordField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.password,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (password.length() < 4 || password.length() > 32) {
            createAccountBinding.password.requestFocus();
            createAccountBinding.passwordTV.setError(mcontext.getResources().getString(R.string.password_length));
//            createAccountBinding.passwordTV.setPasswordVisibilityToggleEnabled(false);
//            createAccountBinding.confirmPasswordTv.setPasswordVisibilityToggleEnabled(false);
            scrollToView(createAccountBinding.registerScroll, passwordField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.password,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (confirmation.matches("") || Validation.isEmoji(confirmation)) {
            createAccountBinding.passwordTV.setErrorEnabled(false);
            createAccountBinding.confirmPasswordTv.requestFocus();
            createAccountBinding.confirmPasswordTv.setError(mcontext.getResources().getString(R.string.confirm_password) + " "
                    + mcontext.getResources().getString(R.string.is_require_text));
//            createAccountBinding.confirmPasswordTv.setPasswordVisibilityToggleEnabled(false);
            scrollToView(createAccountBinding.registerScroll, confirmationField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.confirmPasswordTv,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (!password.equals(confirmation)) {
            createAccountBinding.confirmPasswordTv.requestFocus();
            createAccountBinding.confirmPasswordTv.setError(mcontext.getResources().getString(R.string.password_shud_match));
            scrollToView(createAccountBinding.registerScroll, confirmationField);
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.confirmPasswordTv,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
        } else if (!isChecked) {
            createAccountBinding.confirmPasswordTv.setErrorEnabled(false);
            createAccountBinding.tAndC.requestFocus();
            isFormValidated = false;
            ObjectAnimator.ofFloat(createAccountBinding.tAndC,
                    "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                    .setDuration(1400)
                    .start();
            new MakeToast().shortToast(mcontext, mcontext.getString(R.string.select_i_have_read_and_agree_to_the_privacy_policy_));
        }

        if (isFormValidated) {
            String check = "0";
            String storeName = createAccountBinding.storeName.getText().toString();
            if (createAccountBinding.becomeSeller.isChecked() && createAccountBinding.storeName.getVisibility() == View.VISIBLE) {
                storeName = createAccountBinding.storeName.getText().toString();
            }
            if (createAccountBinding.becomeSeller.isChecked()) {
                check = "1";
            }
            new SweetAlertBox().showProgressDialog(mcontext);
            customerName = firstName + " " + lastName;
            customerEmail = emailAddr;
            RetrofitCallback.INSTANCE.addCustomerCall(mcontext, String.valueOf(group_id), firstName, lastName, emailAddr, telephone,
                    fax, company, streetAdd1, streetAdd2, city, zip, String.valueOf(country_id), String.valueOf(state_id),
                    password, String.valueOf(is_subscribed), String.valueOf(agreePolicy), check,
                    storeName, new RetrofitCustomCallback(customerLoginCallback, mcontext));
        }
    }

    public void scrollToView(final View scrollView, final View view) {
        if (canScrollOnValidation) {
            view.requestFocus();
            final Rect scrollBounds = new Rect();
            scrollView.getHitRect(scrollBounds);
            if (!view.getLocalVisibleRect(scrollBounds)) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        int toScroll = getRelativeTop(view) - getRelativeTop(scrollView);
                        ((ScrollView) scrollView).smoothScrollTo(0, toScroll - 120);
                    }
                });
            }
        }
        canScrollOnValidation = false;
    }

    public int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView()) return myView.getTop();
        else return myView.getTop() + getRelativeTop((View) myView.getParent());
    }

    @Override
    public void getBinding(ActivityCreateAccountBinding createAccountBinding) {
        this.createAccountBinding = createAccountBinding;
    }

    @Override
    public void getSateID(String stateId) {
        state_id = stateId;
    }

    @Override
    public void getCountryId(String countryId) {
        country_id = countryId;
    }

    @Override
    public void getRadioId(int radioId) {
        this.radioId = radioId;
    }

    @Override
    public void getGroupId(int groupId) {
        this.groupId = groupId;

    }


}
