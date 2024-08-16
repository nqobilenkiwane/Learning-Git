package com.example.dev_dyanast

data class ReceiptResponse(
    val receiptId: String,
    val items: List<ReceiptItem>,
    val totalPrice: Double
)

data class ReceiptItem(
    val name: String,
    val quantity: Int,
    val price: Double
)
