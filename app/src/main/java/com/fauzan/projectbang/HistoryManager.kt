package com.fauzan.projectbang

import java.util.Date

// Model Data Satu Transaksi (Order)
data class OrderModel(
    val orderId: String,
    val date: Date,            // Waktu transaksi dibuat
    val items: List<CartItem>, // Barang apa saja yang dibeli
    val totalPrice: Int
)

object HistoryManager {
    // List penampung semua riwayat
    private val historyList = mutableListOf<OrderModel>()

    // Fungsi simpan transaksi baru
    fun addOrder(items: List<CartItem>, total: Int) {
        val newOrder = OrderModel(
            orderId = "ORD-${System.currentTimeMillis()}", // ID Unik dari waktu
            date = Date(), // Waktu saat ini
            items = ArrayList(items), // PENTING: Copy list agar tidak ikut terhapus saat cart di-clear
            totalPrice = total
        )
        // Masukkan ke paling atas (terbaru)
        historyList.add(0, newOrder)
    }

    fun getHistory(): List<OrderModel> {
        return historyList
    }
}