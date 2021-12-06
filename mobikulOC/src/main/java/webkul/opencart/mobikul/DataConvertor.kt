package webkul.opencart.mobikul

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import webkul.opencart.mobikul.model.GetHomePage.*


class DataConvertor {
    private val gson = Gson()

    @TypeConverter
    fun fromStingToHomeData(value: String?): HomeDataModel? {
        return if (value == null) null else gson.fromJson(value, HomeDataModel::class.java)
    }

    @TypeConverter
    fun homeDataToString(homeDataModel: HomeDataModel?): String? {
        val jsonString = gson.toJson(homeDataModel)
        return jsonString
    }

}