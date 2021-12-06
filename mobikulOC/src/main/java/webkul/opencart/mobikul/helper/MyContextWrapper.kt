package webkul.opencart.mobikul.helper

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import android.support.annotation.RequiresApi

import java.util.Locale

/**
 * Created by manish.choudhary on 23/9/17.
 */

class MyContextWrapper(base: Context) : ContextWrapper(base) {
    companion object {
        @RequiresApi(api = Build.VERSION_CODES.N)
        fun wrap(context: Context, newLocale: Locale): ContextWrapper {
            var context = context
            val res = context.resources
            val configuration = res.configuration
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.locales = localeList
                context = context.createConfigurationContext(configuration)
            } else if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(newLocale)
                context = context.createConfigurationContext(configuration)

            } else {
                configuration.locale = newLocale
                res.updateConfiguration(configuration, res.displayMetrics)
            }

            return ContextWrapper(context)
        }
    }
}
