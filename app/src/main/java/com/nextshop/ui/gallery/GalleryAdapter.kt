package com.nextshop.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nextshop.R
import com.nextshop.service.model.ProductsItemResponse
import java.util.*


class GalleryAdapter(val onClickItem: (ProductsItemResponse) -> Unit) : RecyclerView.Adapter<GalleryItemViewHolder>() {

    private var items = mutableListOf<ProductsItemResponse>()
    private var visibleItems = mutableListOf<ProductsItemResponse>()

    fun setData(items: List<ProductsItemResponse>) {
        this.items.clear()
        this.items.addAll(items)

        this.visibleItems.clear()
        this.visibleItems.addAll(items)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = visibleItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemViewHolder = GalleryItemViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_gallery,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.data = visibleItems[position]
        holder.view.setOnClickListener { onClickItem(visibleItems[position]) }
    }

    fun filter(query: String) {
        val queryLowerCase = query.toLowerCase(Locale.getDefault())
        visibleItems.clear()
        if (queryLowerCase.isEmpty()) {
            visibleItems.addAll(items)
        } else {
            for (items in items) {
                if (items.productName.toLowerCase(Locale.getDefault()).contains(query)) {
                    visibleItems.add(items)
                }
            }
        }
        notifyDataSetChanged()
    }
}