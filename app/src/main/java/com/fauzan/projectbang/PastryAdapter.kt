package com.fauzan.projectbang

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fauzan.projectbang.databinding.ItemPastryBinding

// UBAH 'private val' MENJADI 'private var'
class PastryAdapter(private var items: List<ProductModel>) : RecyclerView.Adapter<PastryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPastryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPastryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvPastryName.text = item.title
        holder.binding.tvPastryPrice.text = item.price

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .centerCrop()
            .into(holder.binding.imgPastry)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("EXTRA_TITLE", item.title)
                putExtra("EXTRA_PRICE", item.price)
                putExtra("EXTRA_IMAGE", item.imageUrl)
                putExtra("EXTRA_CATEGORY", "Pastry")
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = items.size

    // === TAMBAHKAN FUNGSI INI ===
    fun updateData(newItems: List<ProductModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}