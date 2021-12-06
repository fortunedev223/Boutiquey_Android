package webkul.opencart.mobikul.helper

import android.content.Context
import android.os.AsyncTask
import android.util.Log

import org.jsoup.Jsoup

import java.io.IOException


class VersionChecker(
        val versionCode: GetVersionCode,
        val mContext: Context) : AsyncTask<String, String, String>() {

    private var newVersion = "0.9"

    override fun doInBackground(vararg params: String): String {
        try {
            val document = Jsoup.connect(Constant.getApplicationVersionCheckerUrl(mContext))
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
            if (document != null) {
                Log.d("VersionChecker", "document")
                val element = document.getElementsContainingOwnText("Current Version")
                for (ele in element) {
                    Log.d("VersionChecker", "elements" + "------>" + ele)
                    if (ele.siblingElements() != null) {
                        val sibElements = ele.siblingElements()
                        Log.d("VersionChecker", "sibElements" + "------>" + sibElements)
                        for (sibElement in sibElements) {
                            Log.d("VersionChecker", "sibElements" + "------>" + sibElement.text())
                            newVersion = sibElement.text()
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return newVersion
    }

    override fun onPostExecute(result: String?) {
        if (result != null) {
            versionCode.getVersionCode(result)
        }
        super.onPostExecute(result)
    }

    interface GetVersionCode {
        fun getVersionCode(version: String)
    }
}
