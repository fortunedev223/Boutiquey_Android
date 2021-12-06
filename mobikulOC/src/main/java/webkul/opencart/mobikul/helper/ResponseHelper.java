package webkul.opencart.mobikul.helper;

import android.app.Activity;

import retrofit2.Response;


public class ResponseHelper {

    public static boolean isValidResponse(Activity activity, Response responseObj, boolean showError) {
        if (activity == null || activity.isFinishing())
            return false;

        if (responseObj == null || responseObj.body() == null || responseObj.body().toString().isEmpty()) {
            if (showError) {
            }
            return false;
        }
        return true;
    }

}


