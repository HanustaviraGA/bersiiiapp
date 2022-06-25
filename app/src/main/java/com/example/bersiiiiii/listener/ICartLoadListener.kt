package com.example.bersiiiiii.listener

import com.example.bersiiiiii.model.CartModel

interface ICartLoadListener {
    fun onLoadCartSuccess(cartModel: List<CartModel>)
    fun onLoadCartFailed(message:String?)
}