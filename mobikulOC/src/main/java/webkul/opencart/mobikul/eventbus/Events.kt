package webkul.opencart.mobikul.eventbus

import org.json.JSONObject


sealed class Events {
    data class StringData(val message:String)
    data class JsonData(val response:JSONObject)
}