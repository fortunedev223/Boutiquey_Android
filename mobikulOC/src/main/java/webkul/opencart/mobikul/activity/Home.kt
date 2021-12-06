package webkul.opencart.mobikul.activity

import android.databinding.DataBindingUtil
import android.os.Bundle

import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.HomeLayoutBinding

/**
 * Created by manish.choudhary on 12/10/17.
 */

class Home : BaseActivity() {
    private var homeLayoutBinding: HomeLayoutBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeLayoutBinding = DataBindingUtil.setContentView(this, R.layout.home_layout)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }
}
