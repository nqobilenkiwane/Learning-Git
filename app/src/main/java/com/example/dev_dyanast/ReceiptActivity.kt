package com.example.dev_dyanast


import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiptActivity : AppCompatActivity() {

    private lateinit var orderNumberTextView: TextView
    private lateinit var itemsContainer: LinearLayout
    private lateinit var totalPriceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        orderNumberTextView = findViewById(R.id.orderNumber)
        itemsContainer = findViewById(R.id.itemsContainer)
        totalPriceTextView = findViewById(R.id.totalPrice)

        val receiptId = intent.getStringExtra("receiptId") ?: return

        fetchReceipt(receiptId)
    }

    private fun fetchReceipt(receiptId: String) {
        RetrofitClient.instance.getReceipt(receiptId).enqueue(object : Callback<ReceiptResponse> {
            override fun onResponse(call: Call<ReceiptResponse>, response: Response<ReceiptResponse>) {
                if (response.isSuccessful) {
                    val receipt = response.body()
                    orderNumberTextView.text = "Order Number: ${receipt?.receiptId}"
                    totalPriceTextView.text = "Total Price: R${receipt?.totalPrice ?: 0.0}"

                    receipt?.items?.forEach { item ->
                        val itemView = layoutInflater.inflate(R.layout.item_receipt, itemsContainer, false)
                        val itemNameTextView: TextView = itemView.findViewById(R.id.itemName)
                        val itemQuantityTextView: TextView = itemView.findViewById(R.id.itemQuantity)
                        val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPrice)

                        itemNameTextView.text = item.name
                        itemQuantityTextView.text = "Quantity: ${item.quantity}"
                        itemPriceTextView.text = "Price: R${item.price}"

                        itemsContainer.addView(itemView)
                    }
                } else {
                    // Handle failure
                }
            }

            override fun onFailure(call: Call<ReceiptResponse>, t: Throwable) {
                // Handle error
            }
        })
    }
}
