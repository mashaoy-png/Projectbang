package com.fauzan.projectbang

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.projectbang.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupList() {
        val historyData = HistoryManager.getHistory()

        if (historyData.isEmpty()) {
            binding.rvHistory.visibility = View.GONE
            binding.tvEmptyHistory.visibility = View.VISIBLE
        } else {
            binding.rvHistory.visibility = View.VISIBLE
            binding.tvEmptyHistory.visibility = View.GONE

            binding.rvHistory.layoutManager = LinearLayoutManager(this)
            binding.rvHistory.adapter = HistoryAdapter(historyData)
        }
    }
}