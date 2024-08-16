package com.example.dev_dyanast

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double
)

class MenuItemsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MenuItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_items)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show back button

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: android.view.MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_featured -> true
                R.id.navigation_menu -> {
                    val intent = Intent(this, MenusActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> true
                else -> false
            }
        }


        val category = intent.getStringExtra("CATEGORY")
        if (category != null) {
            fetchMenuItems(category)
        } else {
            Toast.makeText(this, "Category is missing", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                // Start the CartActivity
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.navigation_menu -> {
                val intent = Intent(this, MenusActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.navigation_profile -> {
                val intent = Intent(this, ProfileEditActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun fetchMenuItems(category: String) {
        RetrofitClient.instance.getMenuItems(category).enqueue(object : Callback<List<MenuItem>> {
            override fun onResponse(call: Call<List<MenuItem>>, response: Response<List<MenuItem>>) {
                if (response.isSuccessful) {
                    val items = response.body()
                    adapter = MenuItemsAdapter(items ?: listOf())
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@MenuItemsActivity, "Failed to fetch menu items", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MenuItem>>, t: Throwable) {
                Toast.makeText(this@MenuItemsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
