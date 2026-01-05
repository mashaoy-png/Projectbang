package com.fauzan.projectbang

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.projectbang.databinding.ItemHistoryBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(private val orders: List<OrderModel>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]

        // 1. DATA UTAMA
        holder.binding.tvOrderID.text = order.orderId

        // 2. TANGGAL (Simple)
        // Format: "Dipesan pada 20 Desember 2025"
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("in", "ID"))
        val dateString = dateFormat.format(order.date)
        holder.binding.tvOrderDate.text = "Dipesan pada $dateString"

        // 3. HARGA
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        holder.binding.tvOrderTotal.text = currencyFormat.format(order.totalPrice)

        // 4. ITEM SUMMARY
        val itemNames = order.items.joinToString(", ") { it.title }
        holder.binding.tvItemsSummary.text = itemNames

        // 5. TOTAL QTY
        var totalQty = 0
        for(item in order.items) totalQty += item.quantity
        holder.binding.tvTotalItems.text = "$totalQty item"
    }

    override fun getItemCount() = orders.size
}