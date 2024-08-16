package com.example.dev_dyanast

data class CheckoutRequest(
    val userId: String
)

data class CheckoutResponse(
    val id: String,
    val message: String,
    val totalPrice: Double,
    val items: List<Item>

)

data class Item(
    val id: String,
    val productId: String,
    val name: String,
    val imageUrl: String,
    val quantity: Int,
    val price: Double
)

