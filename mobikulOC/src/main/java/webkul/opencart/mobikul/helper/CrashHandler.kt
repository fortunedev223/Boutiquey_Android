package webkul.opencart.mobikul.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

object CrashHandler {

    fun navigateTo(context: Context, intent: Intent) {
        val clx = context.javaClass
        val annotations = clx.annotations
        for (annot in annotations) {
            if (annot is CrashHandle) {
//                addHandler(annot, context, intent)
            }
        }
    }


    private fun addHandler(annot: CrashHandle, context: Context, intent: Intent) {
        val handler = object : InvocationHandler {
            override fun invoke(p0: Any?, p1: Method?, p2: Array<out Any>?): Any {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        val exceptionhandler = object : Thread.UncaughtExceptionHandler {
            override fun uncaughtException(p0: Thread?, p1: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        val myInterface: Thread.UncaughtExceptionHandler = Proxy.newProxyInstance(exceptionhandler.javaClass.classLoader,
                listOf(exceptionhandler.javaClass).toTypedArray(), handler) as Thread.UncaughtExceptionHandler
        val clazz = context.javaClass
    }

}

