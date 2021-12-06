package webkul.opencart.mobikul.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.FragmentDashBoardMyReviewBinding


class DashBoardMyReview : Fragment() {
    private var reviewBinding: FragmentDashBoardMyReviewBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        reviewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board_my_review, container, false)
        // Inflate the layout for this fragment
        return reviewBinding!!.root
    }

}// Required empty public constructor
