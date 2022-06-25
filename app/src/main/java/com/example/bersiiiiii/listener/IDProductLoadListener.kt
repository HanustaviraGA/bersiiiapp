package com.example.bersiiiiii.listener

import com.example.bersiiiiii.model.ProductModel
import com.example.bersiiiiii.productlist

interface IDProductLoadListener {
    fun onLoadSuccess(productlist:List<ProductModel>?)
    fun onLoadFailed(message:String?)

}