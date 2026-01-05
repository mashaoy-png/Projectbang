package com.fauzan.projectbang

// Model Data khusus untuk item di keranjang
// Kita tambahkan detail seperti quantity, size, sugar, dll.
data class CartItem(
    val title: String,
    val price: Int,
    val imageUrl: String,
    var quantity: Int,
    val details: String   // Contoh: "Ice, Normal, Less Sugar"
)

// Singleton Object: menghidupkan data selama aplikasi masih berjalan
object CartManager {
    // List untuk menampung barang belanjaan
    private val cartList = mutableListOf<CartItem>()

    // Fungsi tambah barang
    fun addItem(item: CartItem) {
        // Cek apakah barang yang sama persis sudah ada?
        val existingItem = cartList.find {
            it.title == item.title && it.details == item.details
        }

        if (existingItem != null) {
            // Jika sudah ada, tambahkan jumlahnya saja
            existingItem.quantity += item.quantity
        } else {
            // Jika belum, masukkan sebagai item baru
            cartList.add(item)
        }
    }

    // Fungsi ambil semua barang
    fun getItems(): List<CartItem> {
        return cartList
    }

    // Fungsi hitung total harga
    fun getTotalPrice(): Int {
        var total = 0
        for (item in cartList) {
            total += (item.price * item.quantity)
        }
        return total
    }

    // Fungsi hapus/kosongkan keranjang (setelah checkout)
    fun clearCart() {
        cartList.clear()
    }
}