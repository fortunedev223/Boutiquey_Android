package webkul.opencart.mobikul.loginlistener;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import webkul.opencart.mobikul.BR;
import webkul.opencart.mobikul.helper.Constant;


import static android.content.Context.MODE_PRIVATE;

public class LoginChecker extends BaseObservable {
    private boolean login;
    private static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private boolean seller;

    public LoginChecker(Context context) {
        SharedPreferences shared = context.getSharedPreferences(Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), MODE_PRIVATE);
        login = shared.getBoolean("isLoggedIn", false);
        seller = Integer.parseInt(shared.getString(Constant.INSTANCE.getIS_SELLER(), "0")) == 1;
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                login = sharedPreferences.getBoolean("isLoggedIn", false);
                notifyPropertyChanged(BR.login);
                seller = Integer.parseInt(sharedPreferences.getString(Constant.INSTANCE.getIS_SELLER(), "0")) == 1;
                notifyPropertyChanged(BR.seller);
            }
        };
        shared.registerOnSharedPreferenceChangeListener(listener);
    }

    @Bindable({"seller"})
    public boolean isSeller() {
        return seller;
    }

    public void setSeller(boolean seller) {
        this.seller = seller;
        notifyPropertyChanged(BR.seller);
    }

    @Bindable
    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
        notifyPropertyChanged(BR.login);
    }

    @Bindable({"login"})
    public boolean isUserLogin() {
        return isLogin();
    }

}
