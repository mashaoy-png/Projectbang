package com.fauzan.projectbang

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fauzan.projectbang.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("EXTRA_TITLE")
        val price = intent.getStringExtra("EXTRA_PRICE")
        val imageUrl = intent.getStringExtra("EXTRA_IMAGE")

        // Tangkap data Kategori
        val category = intent.getStringExtra("EXTRA_CATEGORY")

        binding.tvDetailName.text = title
        binding.tvDetailPrice.text = price
        Glide.with(this).load(imageUrl).centerCrop().into(binding.imgDetail)

        // LOGIKA BARU: Sembunyikan opsi minuman jika Pastry
        if (category == "Pastry") {
            binding.layoutDrinkOptions.visibility = View.GONE
            binding.tvDescription.text = "Nikmati pastry hangat yang renyah dan lembut, cocok untuk teman ngopi anda."
        } else {
            binding.layoutDrinkOptions.visibility = View.VISIBLE
        }

        binding.btnBack.setOnClickListener { finish() }

        binding.btnPlus.setOnClickListener {
            quantity++
            binding.tvQuantity.text = quantity.toString()
        }

        binding.btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
            }
        }

        binding.btnAddToCart.setOnClickListener {
            val service = if (binding.rbDineIn.isChecked) "Dine In" else "Delivery"

            var details = ""

            // Cek apakah opsi minuman aktif (Visible)
            if (binding.layoutDrinkOptions.visibility == View.VISIBLE) {
                val temp = if (binding.rbIce.isChecked) "Ice" else "Hot"
                val size = if (binding.rbNormalSize.isChecked) "Normal" else "Large"
                val sugar = when {
                    binding.rbSugarNormal.isChecked -> "Normal Sugar"
                    binding.rbSugarLess.isChecked -> "Less Sugar"
                    else -> "No Sugar"
                }
                details = "($temp, $size, $sugar)"
            }

            val summary = "$quantity x $title $details [$service] Masuk Keranjang!"
            Toast.makeText(this, summary, Toast.LENGTH_LONG).show()
        }
    }
}