package webkul.opencart.mobikul.deliveryboy

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.DeliveryboyTrackBinding
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.dagger.ContextModule
import webkul.opencart.mobikul.dagger.DaggerAppComponent
import webkul.opencart.mobikul.deliveryboy.Model.LocationModel
import webkul.opencart.mobikul.deliveryboy.Model.Track
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.connection.MySingleton
import java.util.*


class DeliveryBoy : AppCompatActivity(), OnMapReadyCallback {

    var mapView: MapView? = null
    var map: GoogleMap? = null
    var mLocationList = ArrayList<Track>()
    lateinit var mBinding: DeliveryboyTrackBinding
    lateinit var id: String
    val LngList = ArrayList<LatLng>()
    val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.deliveryboy_track)
        id = intent.getStringExtra("id")
        mapView = mBinding.map
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        makeApiCall()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateList()
            }
        }, 0, 200000)
    }

    private fun updateList() {
        DaggerAppComponent.builder().contextModule(ContextModule(this)).build()
                .getApiInterface()?.getDboyLocation(AppSharedPreference.getWkToken(this, ""), id)
                ?.enqueue(object : Callback<LocationModel> {
                    override fun onFailure(call: Call<LocationModel>?, t: Throwable?) {
                    }

                    override fun onResponse(call: Call<LocationModel>?, response: Response<LocationModel>?) {
                        if (response?.body()?.error != 1) {
                            if (response?.body()?.tracks?.size != null &&
                                    response.body()?.tracks?.size != 0) {
                                LngList.clear()
                                mLocationList = response.body()?.tracks!!
                                mLocationList.map {
                                    it.lon?.toDouble()?.let { it1 ->
                                        it.lat?.toDouble()?.let { it2 -> LatLng(it2, it1) }
                                    }?.let { it2 -> LngList.add(it2) }
                                }
                                drawPolyLineOnMap(LngList)
                            }
                        }
                    }
                })
    }

    private var mLatitude: String? = null

    private var mLongtitude: String? = null

    private fun getShippingAddress(address: String?) {
        val mURL = "https://maps.googleapis.com/maps/api/geocode/json?address=$address&key=${this.resources.getString(R.string.map_key)}"
        Log.d("URL", "==========>" + mURL)
        val request = JsonObjectRequest(Request.Method.POST, mURL, JSONObject(),
                com.android.volley.Response.Listener { response ->
                    try {
                        Log.d("Response", "===========>" + response.toString())
                        val `object` = JSONObject(response.toString())
                        if (`object`.has("results")) {
                            if (`object`.getJSONArray("results").length() != 0) {
                                if (`object`.getJSONArray("results").getJSONObject(0).has("geometry")) {
                                    mLatitude = `object`.getJSONArray("results")
                                            .getJSONObject(0)
                                            .getJSONObject("geometry")
                                            .getJSONObject("location")
                                            .getString("lat")
                                    mLongtitude = `object`.getJSONArray("results")
                                            .getJSONObject(0)
                                            .getJSONObject("geometry")
                                            .getJSONObject("location")
                                            .getString("lng")
                                    Log.d("LocationLat", "=====>" + mLongtitude + "======>" + mLatitude)
                                    map?.clear()
                                    map?.addMarker(MarkerOptions().title("Customer")
                                            .position(LatLng(mLatitude?.toDouble()!!,
                                                    mLongtitude?.toDouble()!!))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.dboy_home)))
                                    LngList.add(LatLng(mLatitude?.toDouble()!!, mLongtitude?.toDouble()!!))
                                    drawPolyLineOnMap(LngList)
                                }

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                com.android.volley.Response.ErrorListener {
                    Log.d("Exception", "======>" + it.toString())
                })
        MySingleton.getInstance(this).addToRequestQueue(request)
    }

    private fun makeApiCall() {
        DaggerAppComponent.builder().contextModule(ContextModule(this)).build()
                .getApiInterface()?.getDboyLocation(AppSharedPreference.getWkToken(this, ""), id)
                ?.enqueue(object : Callback<LocationModel> {
                    override fun onFailure(call: Call<LocationModel>?, t: Throwable?) {
                    }

                    override fun onResponse(call: Call<LocationModel>?, response: Response<LocationModel>?) {
                        if (response?.body()?.error != 1) {
                            if (response?.body()?.tracks?.size != null &&
                                    response.body()?.tracks?.size != 0) {
                                mLocationList = response.body()?.tracks!!
                                mLocationList.map {
                                    it.lon?.toDouble()?.let { it1 ->
                                        it.lat?.toDouble()?.let { it2 -> LatLng(it2, it1) }
                                    }?.let { it2 -> LngList.add(it2) }
                                }
                                getShippingAddress(intent.getStringExtra("address"))
                            }
                        }
                    }
                })
    }

    fun drawPolyLineOnMap(list: ArrayList<LatLng>) {
        val polyOptions = PolylineOptions()
        polyOptions.color(Color.RED)
        polyOptions.width(20f)
        polyOptions.geodesic(true)
        polyOptions.pattern(arrayListOf(Dot(), Gap(20f)))
        polyOptions.addAll(list)
//        map?.clear()
        map?.addPolyline(polyOptions)
        map?.addMarker(MarkerOptions().title("DeliveryBoy")
                .position(LatLng(list.get(0).latitude,
                        list.get(0).longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery_boy)))
        val builder = LatLngBounds.Builder()
        for (latLng in list) {
            builder.include(latLng)
        }
        val bounds = builder.build()
        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, 8)
        map?.animateCamera(cu)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
        mapView?.onStop()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        map = p0
        map?.getUiSettings()?.setMyLocationButtonEnabled(false);
    }

}


