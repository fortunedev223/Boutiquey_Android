package webkul.opencart.mobikul.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import webkul.opencart.mobikul.activity.ReturnInfo
import webkul.opencart.mobikul.eventbus.Events
import webkul.opencart.mobikul.eventbus.GlobalEventBus
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.SealedClass


class OrderRetrunAdapter(val list: ArrayList<SealedClass.OrderReview>, val mContext: Context) : RecyclerView.Adapter<OrderRetrunAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.single_card_order_review, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model: SealedClass.OrderReview = list.get(position)
        holder.orderId.text = model.orderId
        holder.name.text = model.name
        holder.status.text = model.status
        holder.dateAdded.text = model.dateAdded
        holder.returnId.text = model.returnId
        holder.button.setOnClickListener {
            val event: String = Events.StringData(model.returnId).message
            GlobalEventBus.eventBus!!.post(event)
            val intent = Intent(mContext, ReturnInfo::class.java)
            intent.putExtra(Constant.RETURN_ID, model.returnId)
            mContext.startActivity(intent)
        }
    }

    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderId = view.findViewById<TextView>(R.id.order_id)
        val name = view.findViewById<TextView>(R.id.name)
        val status = view.findViewById<TextView>(R.id.status)
        val dateAdded = view.findViewById<TextView>(R.id.date_added)
        val button = view.findViewById<Button>(R.id.view)
        val returnId = view.findViewById<TextView>(R.id.return_id)
    }

}