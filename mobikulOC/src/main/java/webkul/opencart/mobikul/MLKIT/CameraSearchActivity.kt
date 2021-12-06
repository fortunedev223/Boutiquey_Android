//package webkul.opencart.mobikul.MLKIT
//
//import android.Manifest
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.hardware.Camera
//import android.os.Bundle
//import android.support.v4.app.ActivityCompat
//import android.support.v4.content.ContextCompat
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.util.Log
//import android.view.View
//import android.widget.LinearLayout
//import android.widget.TextView
//import android.widget.Toast
//import android.widget.ToggleButton
//import com.google.firebase.ml.vision.label.FirebaseVisionLabel
//import com.google.firebase.ml.vision.text.FirebaseVisionText
//import webkul.opencart.mobikul.CategoryActivity
//import webkul.opencart.mobikul.Helper.Utils
//import webkul.opencart.mobikul.R
//import java.io.IOException
//import java.util.*
//
//class CameraSearchActivity : AppCompatActivity() {
//
//    private var preview: CameraSourcePreview? = null
//    private var graphicOverlay: GraphicOverlay? = null
//    private var cameraSource: CameraSource? = null
//    private var resultSpinner: RecyclerView? = null
//    private var resultList: MutableList<FirebaseVisionLabel>? = null
//    private var displayList: MutableList<String>? = null
//    private var displayAdapter: CameraSearchResultAdapter? = null
//    private var resultNumberTv: TextView? = null
//    private var resultContainer: LinearLayout? = null
//    private var hasFlash: Boolean = false
//    private var selectedModel = IMAGE_LABEL_DETECTION
//
//    private val requiredPermissions: Array<String>
//        get() =
//            arrayOf(Manifest.permission.CAMERA, Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//
//    override fun onBackPressed() {
//        this@CameraSearchActivity.setResult(Activity.RESULT_CANCELED)
//        this@CameraSearchActivity.finish()
//    }
//
//    private val TAG: String = "CameraSearchActivity"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_camera_search)
//        resultList = ArrayList()
//        displayList = ArrayList()
//        resultSpinner = findViewById<View>(R.id.results_spinner) as RecyclerView
//        resultSpinner!!.layoutManager = LinearLayoutManager(this@CameraSearchActivity, LinearLayoutManager.VERTICAL, false)
//        displayAdapter = CameraSearchResultAdapter(this@CameraSearchActivity, displayList)
//        resultSpinner!!.adapter = displayAdapter
//        resultContainer = findViewById<View>(R.id.resultsContainer) as LinearLayout
//        resultContainer!!.layoutParams.height = (Utils.getDeviceScrenHeight() * 0.65).toInt()
//        resultNumberTv = findViewById<View>(R.id.resultsMessageTv) as TextView
//        resultNumberTv!!.text = getString(R.string.x_results_found, displayList!!.size)
//        preview = findViewById<View>(R.id.firePreview) as CameraSourcePreview
//        if (preview == null) {
//            Log.d(TAG, "CameraSearchActivity onCreate Preview is null ")
//        }
//        graphicOverlay = findViewById<View>(R.id.fireFaceOverlay) as GraphicOverlay
//        if (graphicOverlay == null) {
//            Log.d(TAG, "CameraSearchActivity onCreate graphicOverlay is null ")
//        }
//        if (intent.hasExtra("selectedModel")) {
//            selectedModel = intent.getStringExtra("selectedModel")
//        }
//        val facingSwitch = findViewById<View>(R.id.facingswitch) as ToggleButton
//        if (Camera.getNumberOfCameras() == 2) {
//            facingSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
//                if (cameraSource != null) {
//                    if (isChecked) {
//                        cameraSource!!.setFacing(CameraSource.CAMERA_FACING_FRONT)
//                    } else {
//                        cameraSource!!.setFacing(CameraSource.CAMERA_FACING_BACK)
//                    }
//                }
//                preview!!.stop()
//                displayList!!.clear()
//                displayAdapter!!.notifyDataSetChanged()
//                startCameraSource()
//            }
//        } else {
//            facingSwitch.isEnabled = false
//            facingSwitch.isChecked = false
//            facingSwitch.visibility = View.GONE
//        }
//
//        hasFlash = applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
//        val flashButton = findViewById<View>(R.id.flashSwitch) as ToggleButton
//
//        if (hasFlash) {
//            flashButton.visibility = View.VISIBLE
//            flashButton.setOnCheckedChangeListener { buttonView, isChecked ->
//                if (cameraSource != null) {
//                    val camera = cameraSource!!.camera
//                    val parameters = camera.parameters
//                    if (isChecked) {
//                        parameters.flashMode = "on"
//                    } else {
//                        parameters.flashMode = "off"
//                    }
//                    camera.parameters = parameters
//                } else {
//                    Toast.makeText(this, getString(R.string.error_while_using_flash), Toast.LENGTH_LONG).show()
//                }
//            }
//
//        } else {
//            flashButton.visibility = View.GONE
//        }
//
//        if (allPermissionsGranted()) {
//            createCameraSource(selectedModel)
//        } else {
//            getRuntimePermissions()
//        }
//    }
//
//    public override fun onResume() {
//        super.onResume()
//        Log.d(TAG, "CameraSearchActivity onResume: ")
//        startCameraSource()
//    }
//
//    /**
//     * Stops the camera.
//     */
//    override fun onPause() {
//        super.onPause()
//        preview!!.stop()
//    }
//
//    public override fun onDestroy() {
//        super.onDestroy()
//        if (cameraSource != null) {
//            cameraSource!!.release()
//        }
//    }
//
//    private fun startCameraSource() {
//        if (cameraSource != null) {
//            try {
//                if (preview == null) {
//                    Log.d(TAG, "CameraSearchActivity startCameraSource resume: Preview is null ")
//                }
//                if (graphicOverlay == null) {
//                    Log.d(TAG, "CameraSearchActivity startCameraSource resume: graphOverlay is null ")
//                }
//                preview!!.start(cameraSource, graphicOverlay)
//            } catch (e: IOException) {
//                Log.d(TAG, "CameraSearchActivity startCameraSource : Unable to start camera source." + e.message)
//                cameraSource!!.release()
//                cameraSource = null
//            }
//
//        }
//    }
//
//    private fun createCameraSource(selectedModel: String) {
//        if (cameraSource == null) {
//            cameraSource = CameraSource(this, graphicOverlay)
//        }
//
//        try {
//            when (selectedModel) {
//                TEXT_DETECTION -> cameraSource!!.setMachineLearningFrameProcessor(TextRecognitionProcessor(this))
//                IMAGE_LABEL_DETECTION -> cameraSource!!.setMachineLearningFrameProcessor(ImageLabelingProcessor(this@CameraSearchActivity!!))
//                else -> cameraSource!!.setMachineLearningFrameProcessor(ImageLabelingProcessor(this@CameraSearchActivity!!))
//            }
//        } catch (e: Exception) {
//            Log.d(TAG, "CameraSearchActivity createCameraSource can not create camera source: " + e.cause)
//            e.printStackTrace()
//        }
//    }
//
//    private fun allPermissionsGranted(): Boolean {
//        for (permission in requiredPermissions) {
//            if (!isPermissionGranted(this, permission)) {
//                return false
//            }
//        }
//        return true
//    }
//
//    private fun getRuntimePermissions() {
//        val allNeededPermissions = ArrayList<String>()
//        for (permission in requiredPermissions) {
//            if (!isPermissionGranted(this, permission)) {
//                allNeededPermissions.add(permission)
//            }
//        }
//
//        if (!allNeededPermissions.isEmpty()) {
//            ActivityCompat.requestPermissions(
//                    this, allNeededPermissions.toTypedArray(), PERMISSION_REQUESTS)
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        if (allPermissionsGranted()) {
//            createCameraSource(selectedModel)
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//
//    fun updateSpinnerFromResults(labelList: List<FirebaseVisionLabel>) {
//        for (visionLabel in labelList) {
//            if (!displayList!!.contains(visionLabel.label) && displayList!!.size <= 9) {
//                displayList!!.add(visionLabel.label)
//                resultList!!.add(visionLabel)
//            }
//        }
//        resultNumberTv!!.text = getString(R.string.x_results_found, displayList!!.size)
//        displayAdapter!!.notifyDataSetChanged()
//    }
//
//    fun updateSpinnerFromTextResults(textresults: FirebaseVisionText) {
//        val blocks = textresults.blocks
//        for (eachBlock in blocks) {
//            for (eachLine in eachBlock.lines) {
//                for (eachElement in eachLine.elements) {
//                    if (!displayList!!.contains(eachElement.text) && displayList!!.size <= 9) {
//                        displayList!!.add(eachElement.text)
//                    }
//                }
//            }
//        }
//        resultNumberTv!!.text = getString(R.string.x_results_found, displayList!!.size)
//        displayAdapter!!.notifyDataSetChanged()
//    }
//
//    fun sendResultBack(position: Int) {
//        val intent = Intent(this, CategoryActivity::class.java)
//        intent.putExtra("search", displayList!!.get(position))
//        startActivity(intent)
//        finish()
//    }
//
//    companion object {
//        private val PERMISSION_REQUESTS = 1
//        val TEXT_DETECTION = "Text Detection"
//        val IMAGE_LABEL_DETECTION = "Product Detection"
//        private fun isPermissionGranted(context: Context, permission: String): Boolean {
//            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
//                Log.d("camersearchActivity", "CameraSearchActivity isPermissionGranted Permission granted : $permission")
//                return true
//            }
//            Log.d("camersearchActivity", "CameraSearchActivity isPermissionGranted: Permission NOT granted -->$permission")
//            return false
//        }
//    }
//
//}
