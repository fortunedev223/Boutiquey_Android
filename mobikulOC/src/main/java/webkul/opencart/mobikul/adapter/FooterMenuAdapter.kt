package webkul.opencart.mobikul.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import webkul.opencart.mobikul.activity.CMSPage
import webkul.opencart.mobikul.databinding.FooterMenuLayoutBinding
import webkul.opencart.mobikul.model.GetHomePage.FooterMenu
import java.util.ArrayList

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class FooterMenuAdapter(val mContext: Context, val footerMenu: ArrayList<FooterMenu>)
    : RecyclerView.Adapter<FooterMenuAdapter.MyHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyHolder {
        val binding = FooterMenuLayoutBinding.inflate(LayoutInflater.from(mContext), p0, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return footerMenu.size
    }

    override fun onBindViewHolder(p0: MyHolder, p1: Int) {
        p0.binding.title.text = footerMenu.get(p1).title
        p0.binding.title.setOnClickListener {
            val intent = Intent(mContext, CMSPage::class.java)
            intent.putExtra("title", footerMenu.get(p1)?.title)
            intent.putExtra("id", footerMenu.get(p1).information_id)
            mContext.startActivity(intent)
        }
        p0.binding.executePendingBindings()
    }


    class MyHolder(val binding: FooterMenuLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}

