package com.example.bersiiiiii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bersiiiiii.adapter.MyProductAdapterBath
import com.example.bersiiiiii.adapter.MyProductAdapterCook
import com.example.bersiiiiii.listener.IDProductLoadListener
import com.example.bersiiiiii.model.ProductModel
import com.example.bersiiiiii.util.SpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_productlist.*

class  prdctCook : AppCompatActivity(), IDProductLoadListener {

    lateinit var productLoadListener: IDProductLoadListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productlist)
        init()
        loadProductFromFirebase()
        supportActionBar?.hide()
        val bckbtn = findViewById<ImageView>(R.id.backbtn)
        bckbtn.setOnClickListener{
            val intent = Intent(this, menuapp::class.java)
            startActivity(intent)
        }
    }

    private fun loadProductFromFirebase() {
        val productModels : MutableList<ProductModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Cook")
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

        val gridLayoutManager = GridLayoutManager(this, 2)
        recyle_product.layoutManager = gridLayoutManager
        recyle_product.addItemDecoration(SpaceItemDecoration())
    }
    override fun onLoadSuccess(prdctCook: List<ProductModel>?) {
        val adapter = MyProductAdapterCook(this,prdctCook!!)
        recyle_product.adapter = adapter
    }

    override fun onLoadFailed(message:String?) {
        Snackbar.make(mainLayout,message!!,Snackbar.LENGTH_LONG).show()
    }
}
