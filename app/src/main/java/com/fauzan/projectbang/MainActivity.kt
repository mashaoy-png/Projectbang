package com.fauzan.projectbang

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.projectbang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // 1. Definisikan Adapter sebagai properti kelas agar bisa diakses fungsi search
    private lateinit var coffeeAdapter: ProductAdapter
    private lateinit var milkAdapter: ProductAdapter
    private lateinit var pastryAdapter: PastryAdapter

    // 2. Simpan Data Asli (Master Data) untuk cadangan saat search dihapus
    private var listCoffeeOriginal = listOf<ProductModel>()
    private var listMilkOriginal = listOf<ProductModel>()
    private var listPastryOriginal = listOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()         // Siapkan data dulu
        setupCategoryList() // Kategori (Bulat)
        setupLists()        // Pasang Adapter ke RecyclerView
        setupSearch()       // Aktifkan fitur search
    }

    // Fungsi untuk mengisi Data Asli
    private fun setupData() {
        listCoffeeOriginal = listOf(
            ProductModel("Kopi Susu", "Rp. 21.000", "https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?q=80&w=400"),
            ProductModel("Cappuccino", "Rp. 21.000", "https://images.unsplash.com/photo-1572442388796-11668a67e53d?q=80&w=400"),
            ProductModel("Americano", "Rp. 21.000", "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?q=80&w=400"),
            ProductModel("Latte", "Rp. 24.000", "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?q=80&w=400")
        )

        listMilkOriginal = listOf(
            ProductModel("Biscoff", "Rp. 25.000", "https://images.unsplash.com/photo-1628588883674-601e35591c29?q=80&w=400"),
            ProductModel("Berry Land", "Rp. 23.000", "https://images.unsplash.com/photo-1594910069196-2a78370c8b6b?q=80&w=400"),
            ProductModel("Matcha", "Rp. 24.000", "https://images.unsplash.com/photo-1515823064-d6e0c04616a7?q=80&w=400")
        )

        listPastryOriginal = listOf(
            ProductModel("Butter Croissant", "Rp. 40.000", "https://images.unsplash.com/photo-1555507036-ab1f4038808a?q=80&w=600"),
            ProductModel("Almond Croissant", "Rp. 45.000", "https://images.unsplash.com/photo-1509440159596-0249088772ff?q=80&w=600"),
            ProductModel("Kouign Amann", "Rp. 40.000", "https://images.unsplash.com/photo-1558326567-98ae2405596b?q=80&w=600")
        )
    }

    private fun setupCategoryList() {
        // Data Kategori Tetap (Tidak kena search)
        val categories = listOf(
            CategoryModel("Special", "https://images.unsplash.com/photo-1541167760496-1628856ab772?q=80&w=200&auto=format&fit=crop"),
            CategoryModel("Coffee", "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?q=80&w=200&auto=format&fit=crop"),
            CategoryModel("Milk Base", "https://images.unsplash.com/photo-1517701604599-bb29b5c7355c?q=80&w=200&auto=format&fit=crop"),
            CategoryModel("Tea", "https://images.unsplash.com/photo-1556679343-c7306c1976bc?q=80&w=200&auto=format&fit=crop"),
            CategoryModel("Pastry", "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?q=80&w=200&auto=format&fit=crop")
        )
        binding.rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = CategoryAdapter(categories)
    }

    private fun setupLists() {
        // Inisialisasi Adapter dengan Data Asli
        coffeeAdapter = ProductAdapter(listCoffeeOriginal)
        milkAdapter = ProductAdapter(listMilkOriginal)
        pastryAdapter = PastryAdapter(listPastryOriginal)

        // Pasang ke RecyclerView
        binding.rvCoffee.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = coffeeAdapter
        }

        binding.rvMilkBase.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = milkAdapter
        }

        binding.rvPastry.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = pastryAdapter
        }
    }

    // === LOGIKA SEARCH UTAMA ===
    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                filterMenu(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterMenu(query: String) {
        // 1. Filter Kopi
        val filteredCoffee = listCoffeeOriginal.filter {
            it.title.contains(query, ignoreCase = true)
        }
        coffeeAdapter.updateData(filteredCoffee)

        // 2. Filter Susu
        val filteredMilk = listMilkOriginal.filter {
            it.title.contains(query, ignoreCase = true)
        }
        milkAdapter.updateData(filteredMilk)

        // 3. Filter Pastry
        val filteredPastry = listPastryOriginal.filter {
            it.title.contains(query, ignoreCase = true)
        }
        pastryAdapter.updateData(filteredPastry)
    }
}