package webkul.opencart.mobikul.callback

import java.util.ArrayList
import java.util.HashMap

import webkul.opencart.mobikul.CustomoptionData

/**
 * Created by manish.choudhary on 3/8/17.
 */

interface OnCustomPasser {
    fun customData(list: ArrayList<CustomoptionData>)
    fun getFileCode(fileCode: String)
    fun getChecklist(map: HashMap<*, *>)
    fun clearCheckList(id: String)
    fun getRadioProductId(key: Int, id: Int)
    fun getSpinnerProductId(key: Int, id: Int)
}
