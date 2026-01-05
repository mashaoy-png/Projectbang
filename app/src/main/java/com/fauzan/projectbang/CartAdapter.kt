package com.fauzan.projectbang

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fauzan.projectbang.databinding.ItemCartBinding
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(private val items: List<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvCartTitle.text = item.title
        holder.binding.tvCartDetails.text = item.details
        holder.binding.tvCartQty.text = "x${item.quantity}"

        // Format Harga ke Rupiah
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        // numberFormat.maximumFractionDigits = 0 // Hilangkan ,00 di belakang jika mau
        holder.binding.tvCartPrice.text = numberFormat.format(item.price * item.quantity)

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .centerCrop()
            .into(holder.binding.imgCartItem)
    }

    override fun getItemCount() = items.size
}