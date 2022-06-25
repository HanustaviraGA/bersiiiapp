package com.example.bersiiiiii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bersiiiiii.adapter.MyProductAdapter
import com.example.bersiiiiii.eventbus.UpdateCartEvent
import com.example.bersiiiiii.listener.ICartLoadListener
import com.example.bersiiiiii.listener.IDProductLoadListener
import com.example.bersiiiiii.model.CartModel
import com.example.bersiiiiii.model.ProductModel
import com.example.bersiiiiii.util.SpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_productlist.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class productlist : AppCompatActivity(), IDProductLoadListener, ICartLoadListener {

    lateinit var productLoadListener: IDProductLoadListener
    lateinit var cartLoadListener: ICartLoadListener

    override fun onStart(){
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop(){
        super.onStop()
        if (EventBus.getDefault().hasSubscriberForEvent(UpdateCartEvent::class.java))
            EventBus.getDefault().removeStickyEvent(UpdateCartEvent::class.java)
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onUpdateCartEvent(event: UpdateCartEvent){
        countCartFromFirebase()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productlist)
        init()
        loadProductFromFirebase()
        countCartFromFirebase()
        supportActionBar?.hide()
        val bckbtn = findViewById<ImageView>(R.id.backbtn)
        bckbtn.setOnClickListener{
            val intent = Intent(this, menuapp::class.java)
            startActivity(intent)
        }
    }

    private fun countCartFromFirebase() {
        val cartModels : MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("CRT")
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children){
                        val cartModel = cartSnapshot.getValue(CartModel::class.java)
                        cartModel!!.key = cartSnapshot!!.key
                        cartModels.add(cartModel)
                    }
                    cartLoadListener.onLoadCartSuccess(cartModels)
                }

                override fun onCancelled(error: DatabaseError) {
                    cartLoadListener.onLoadCartFailed(error.message)
                }

            })
    }

    private fun loadProductFromFirebase() {
        val productModels : MutableList<ProductModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Product")
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){
                    if(snapshot.exists()){
                        for(productSnapshot in snapshot.children){
                            val productModel = productSnapshot.getValue(ProductModel::class.java)
                            productModel!!.key = productSnapshot.key
                            productModels.add(productModel)
                        }
                        productLoadListener.onLoadSuccess(productModels)
                    }
                    else
                        productLoadListener.onLoadFailed("Product items not exists")
                }

                override fun onCancelled(error:DatabaseError){
                    productLoadListener.onLoadFailed(error.message)
                }
            })
    }

    private fun init(){
        productLoadListener = this
        cartLoadListener = this

        val gridLayoutManager = GridLayoutManager(this, 2)
        recyle_product.layoutManager = gridLayoutManager
        recyle_product.addItemDecoration(SpaceItemDecoration())

        btnCart.setOnClickListener{ startActivity(Intent(this,CartActivty::class.java))}
    }
    override fun onLoadSuccess(productlist: List<ProductModel>?) {
        val adapter = MyProductAdapter(this, productlist!!,cartLoadListener)
        recyle_product.adapter = adapter
    }

    override fun onLoadFailed(message:String?) {
        Snackbar.make(mainLayout,message!!,Snackbar.LENGTH_LONG).show()
    }

    override fun onLoadCartSuccess(cartModelList: List<CartModel>) {
        var cartSum = 0
        for(cartModel in cartModelList!!) cartSum+= cartModel!!.quantity
        badge!!.setNumber(cartSum)
    }

    override fun onLoadCartFailed(message: String?) {
        Snackbar.make(mainLayout,message!!,Snackbar.LENGTH_LONG).show()
    }
}