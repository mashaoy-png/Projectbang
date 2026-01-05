package com.fauzan.projectbang

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.projectbang.databinding.ActivityCartBinding
import java.text.NumberFormat
import java.util.Locale

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCartList()
        setupBottomNav()

        // Tombol Checkout
        binding.btnCheckout.setOnClickListener {
            if (CartManager.getItems().isEmpty()) {
                Toast.makeText(this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show()
            } else {
                // Proses Checkout (Simulasi)
                Toast.makeText(this, "Pesanan Berhasil Dibuat! \uD83D\uDE80", Toast.LENGTH_LONG).show()

                // Kosongkan Keranjang
                CartManager.clearCart()

                // Refresh Tampilan (kembali kosong)
                setupCartList()
            }
        }
        binding.btnCheckout.setOnClickListener {
            if (CartManager.getItems().isEmpty()) {
                Toast.makeText(this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show()
            } else {
                // 1. SIMPAN KE HISTORY
                val currentItems = CartManager.getItems()
                val totalHarga = CartManager.getTotalPrice()

                HistoryManager.addOrder(currentItems, totalHarga)

                // 2. Feedback User
                Toast.makeText(this, "Pesanan Berhasil! Cek Riwayat ya.", Toast.LENGTH_LONG).show()

                // 3. Bersihkan Keranjang
                CartManager.clearCart()

                // 4. Refresh Tampilan Cart (jadi kosong)
                setupCartList()

                // 5. (Opsional) Langsung arahkan ke halaman History
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    // Agar saat kembali dari Home, cart ter-refresh
    override fun onResume() {
        super.onResume()
        setupCartList()
    }

    private fun setupCartList() {
        val cartItems = CartManager.getItems()

        if (cartItems.isEmpty()) {
            binding.rvCartItems.visibility = View.GONE
            binding.tvEmptyCart.visibility = View.VISIBLE
            binding.tvTotalPrice.text = "Rp 0"
        } else {
            binding.rvCartItems.visibility = View.VISIBLE
            binding.tvEmptyCart.visibility = View.GONE

            // Setup Adapter
            binding.rvCartItems.layoutManager = LinearLayoutManager(this)
            binding.rvCartItems.adapter = CartAdapter(cartItems)

            // Hitung Total
            val total = CartManager.getTotalPrice()
            val localeID = Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            binding.tvTotalPrice.text = numberFormat.format(total)
        }
    }

    private fun setupBottomNav() {
        binding.navHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        binding.navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }
    }
}