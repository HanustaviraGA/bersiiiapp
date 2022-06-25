package com.example.bersiiiiii.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bersiiiiii.R
import com.example.bersiiiiii.eventbus.UpdateCartEvent
import com.example.bersiiiiii.listener.ICartLoadListener
import com.example.bersiiiiii.listener.IRecylerClickListener
import com.example.bersiiiiii.model.CartModel
import com.example.bersiiiiii.model.ProductModel
import com.example.bersiiiiii.productlist
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.greenrobot.eventbus.EventBus
import java.lang.StringBuilder

class MyProductAdapter(
    private val context: productlist,
    private val list:List<ProductModel>,
    private val cartListener:ICartLoadListener
): RecyclerView.Adapter<MyProductAdapter.MyProductViewHolder>(){

    class MyProductViewHolder(itemView:android.view.View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var imageView: ImageView?=null
        var txtName: TextView?=null
        var txtPrice: TextView?=null

        private var clickListener:IRecylerClickListener? = null

        fun setClickListener(clickListener: IRecylerClickListener){
            this.clickListener = clickListener;
        }

        init {
            imageView = itemView.findViewById(R.id.productimg) as ImageView
            txtName = itemView.findViewById(R.id.txtName) as TextView
            txtPrice = itemView.findViewById(R.id.txtPrice) as TextView

            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            clickListener!!.onItemClickListener(v,adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductViewHolder {
        return MyProductViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layput_product_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyProductViewHolder, position: Int) {
        Glide.with(context)
            .load(list[position].image)
            .into(holder.imageView!!)
        holder.txtName!!.text = StringBuilder().append(list[position].name)
        holder.txtPrice!!.text = StringBuilder("Rp.").append(list[position].price)

        holder.setClickListener(object:IRecylerClickListener{
            override fun onItemClickListener(view: View?, position: Int) {
                addToCart(list[position])
            }
        })
    }

    private fun addToCart(productModel: ProductModel) {
        val userCart = FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("CRT")

        userCart.child(productModel.key!!)
            .addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val cartModel = snapshot.getValue(CartModel::class.java)
                    val updateData: MutableMap<String, Any> = HashMap()
                    cartModel!!.quantity = cartModel!!.quantity+1;
                    updateData["quantity"] = cartModel!!.quantity
                    updateData["totalPrice"] = cartModel!!.quantity * cartModel.price!!.toFloat()

                    userCart.child(productModel.key!!)
                        .updateChildren(updateData)
                        .addOnSuccessListener {
                            EventBus.getDefault().postSticky(UpdateCartEvent())
                            cartListener.onLoadCartFailed("Success add to Cart")
                        }
                        .addOnFailureListener { e-> cartListener.onLoadCartFailed(e.message) }
                }
                else{
                    val cartModel = CartModel()
                    cartModel.key = productModel.key
                    cartModel.name = productModel.name
                    cartModel.image = productModel.image
                    cartModel.price = productModel.price
                    cartModel.quantity = 1
                    cartModel.totalprice = productModel.price!!.toFloat()

                    userCart.child(productModel.key!!)
                        .setValue(cartModel)
                        .addOnSuccessListener {
                            EventBus.getDefault().postSticky(UpdateCartEvent())
                            cartListener.onLoadCartFailed("Success add to Cart")
                        }
                        .addOnFailureListener { e-> cartListener.onLoadCartFailed(e.message) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cartListener.onLoadCartFailed(error.message)
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
