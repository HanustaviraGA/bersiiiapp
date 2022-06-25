package com.example.bersiiiiii.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bersiiiiii.CartActivty
import com.example.bersiiiiii.R
import com.example.bersiiiiii.eventbus.UpdateCartEvent
import com.example.bersiiiiii.model.CartModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context
import org.greenrobot.eventbus.EventBus
import kotlin.text.StringBuilder

class MyCartAdapter(
    private val context: CartActivty,
    private val cartModelList: List<CartModel>
): RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder>() {


    class MyCartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {

        var btnMinus:ImageView?=null
        var btnPlus:ImageView?=null
        var imageView:ImageView?=null
        var btnDelete:ImageView?=null
        var txtNameCart:TextView?=null
        var txtPriceCart:TextView?=null
        var txtQuantity:TextView?=null

        init {
            btnMinus = itemView.findViewById(R.id.btnMinus) as ImageView
            btnPlus = itemView.findViewById(R.id.btnPlus) as ImageView
            imageView = itemView.findViewById(R.id.imageView) as ImageView
            btnDelete = itemView.findViewById(R.id.btnDelete) as ImageView
            txtNameCart = itemView.findViewById(R.id.txtNameCart) as TextView
            txtPriceCart = itemView.findViewById(R.id.txtPriceCart) as TextView
            txtQuantity = itemView.findViewById(R.id.txtQuantity) as TextView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        return MyCartViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layout_cart_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        Glide.with(context)
            .load(cartModelList[position].image)
            .into(holder.imageView!!)
        holder.txtNameCart!!.text = StringBuilder().append(cartModelList[position].name)
        holder.txtPriceCart!!.text = StringBuilder("Rp.").append(cartModelList[position].price)
        holder.txtQuantity!!.text = StringBuilder("").append(cartModelList[position].quantity )

        holder.btnMinus!!.setOnClickListener{_-> minusCartItem(holder,cartModelList[position])}
        holder.btnPlus!!.setOnClickListener{_-> plusCartItem(holder,cartModelList[position])}
        holder.btnDelete!!.setOnClickListener{_ ->
            val dialog = AlertDialog.Builder(context)
                .setTitle("Delete Item")
                .setMessage("Do you really want to delet item")
                .setNegativeButton("CANCEL"){dialog,_ -> dialog.dismiss()}
                .setPositiveButton("DELETE"){dialog,_ ->

                    notifyItemRemoved(position)
                    FirebaseDatabase.getInstance()
                        .getReference("Cart")
                        .child("CRT")
                        .child(cartModelList[position].key!!)
                        .removeValue()
                        .addOnSuccessListener { EventBus.getDefault().postSticky(UpdateCartEvent()) }

                }
                .create()
            dialog.show()
        }

    }

    private fun plusCartItem(holder: MyCartAdapter.MyCartViewHolder, cartModel: CartModel) {
        cartModel.quantity += 1
        cartModel.totalprice = cartModel.quantity * cartModel.price!!.toFloat()
        holder.txtQuantity!!.text = StringBuilder("").append(cartModel.quantity)
        updateFirebase(cartModel)
    }

    private fun minusCartItem(holder: MyCartViewHolder, cartModel: CartModel) {
        if (cartModel.quantity > 1){
            cartModel.quantity -= 1
            cartModel.totalprice = cartModel.quantity * cartModel.price!!.toFloat()
            holder.txtQuantity!!.text = StringBuilder("").append(cartModel.quantity)
            updateFirebase(cartModel)
        }
    }

    private fun updateFirebase(cartModel: CartModel) {
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("CRT")
            .setValue(cartModel)
            .addOnSuccessListener { EventBus.getDefault().postSticky(UpdateCartEvent()) }
    }

    override fun getItemCount(): Int {
        return cartModelList.size
    }
}