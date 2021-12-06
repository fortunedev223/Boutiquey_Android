package webkul.opencart.mobikul.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webkul.opencart.mobikul.handlers.MainActivityHandler;


public class Validation {

    public static int valid = 0;

    public static boolean checkDescription(String desc) {
        return desc != null && (desc.length() > 2);
    }


    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    public static boolean validate(Object data) {
        return data != null;
    }

    public static boolean isEmoji(String message) {
        return message.matches("(?:[\uD83C\uDF00-\uD83D\uDDFF]|[\uD83E\uDD00-\uD83E\uDDFF]|" +
                "[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|" +
                "[\u2600-\u26FF]\uFE0F?|[\u2700-\u27BF]\uFE0F?|\u24C2\uFE0F?|" +
                "[\uD83C\uDDE6-\uD83C\uDDFF]{1,2}|" +
                "[\uD83C\uDD70\uD83C\uDD71\uD83C\uDD7E\uD83C\uDD7F\uD83C\uDD8E\uD83C\uDD91-\uD83C\uDD9A]\uFE0F?|" +
                "[\u0023\u002A\u0030-\u0039]\uFE0F?\u20E3|[\u2194-\u2199\u21A9-\u21AA]\uFE0F?|[\u2B05-\u2B07\u2B1B\u2B1C\u2B50\u2B55]\uFE0F?|" +
                "[\u2934\u2935]\uFE0F?|[\u3030\u303D]\uFE0F?|[\u3297\u3299]\uFE0F?|" +
                "[\uD83C\uDE01\uD83C\uDE02\uD83C\uDE1A\uD83C\uDE2F\uD83C\uDE32-\uD83C\uDE3A\uD83C\uDE50\uD83C\uDE51]\uFE0F?|" +
                "[\u203C\u2049]\uFE0F?|[\u25AA\u25AB\u25B6\u25C0\u25FB-\u25FE]\uFE0F?|" +
                "[\u00A9\u00AE]\uFE0F?|[\u2122\u2139]\uFE0F?|\uD83C\uDC04\uFE0F?|\uD83C\uDCCF\uFE0F?|" +
                "[\u231A\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA]\uFE0F?)+");
    }

    public static boolean checkCode(String code, String data) {
        if (code.equals(data)) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isSeller(MainActivityHandler handler) {
        Log.d("Seller", "isSeller:--------> " + AppSharedPreference.INSTANCE.isSeller(handler.getMContext()));
        return AppSharedPreference.INSTANCE.isSeller(handler.getMContext()) == 1;
    }

    public static boolean checkStock(String stock) {
        if (stock != null) {
            if (stock.equals("In Stock")) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean checkForLength(String string) {
        return string.length() > 2;
    }

    public static boolean checkForNull(String string) {
        if (string != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkSpecialPrice(String check) {
        if (check == null) {
            return false;
        } else {
            return (check.contains(".") ? ((Float.parseFloat(check) > 0) ? true : false) : ((Integer.parseInt(check) > 0) ? true : false));
        }
    }

    public static boolean checkPrevNext(List list) {
        if (list != null) {
            if (list.size() != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean checkSpecial(String check) {
        if (check != null) {
            return !check.equals("false");
        } else {
            return check != null;
        }
    }

    public static String checkSpecialAtHome(String special, String formated, String price) {
        if (special == null && formated == null) {
            return price;
        } else {
            return (special.contains(".")) ? ((Float.parseFloat(special) > 0) ? formated : price) : ((Integer.parseInt(special) > 0) ? formated : price);
        }
    }

    public static boolean checkColor(String password) {
        return password.length() >= 4;
    }

    public static boolean checkInt(int check) {
        return check != 0;
    }

    public static boolean checkUsernameColor(String username) {
        return isEmailValid(username);
    }

    @NonNull
    public static String isValidUsername(String username) {
        if (username.equals("")) {
            valid = 0;
            return "Enter Your Email";
        } else {
            if (isEmailValid(username)) {
                valid = 1;
                return "Valid Email";
            } else {
                valid = 0;
                return "Invalid Email";
            }
        }
    }

    @NonNull
    public static String isValidPassword(String password) {
        if (password.equals("")) {
            valid = 1;
            return "Enter Your Password";
        } else {
            if (password.length() <= 3) {
                valid = 1;
                return "Your Password Must Have at least 4 Characters";
            } else {
                valid = 2;
                return "Valid Password";
            }
        }
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
