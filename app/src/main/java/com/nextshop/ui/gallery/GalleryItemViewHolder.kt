package com.nextshop.ui.gallery

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nextshop.R
import com.nextshop.service.model.ProductItemResponse
import kotlinx.android.synthetic.main.item_gallery.view.*

class GalleryItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    var data: ProductItemResponse? = null
        set(value) {
            field = value
            value?.let {
                populateThumbnail(value)
                populateProductName(value)
                populateProductPrice(value)
            }
        }

    private fun populateProductPrice(value: ProductItemResponse) {
        view.item_gallery_product_price.text = "€${value.salesPriceIncVat}"
    }

    private fun populateProductName(value: ProductItemResponse) {
        view.item_gallery_product_name.text = value.productName
    }

    private fun populateThumbnail(data: ProductItemResponse) {
        data.let {
            it.productImage.let {imageUrl ->
                Glide.with(view.context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_placeholder)
                    .into(view.item_gallery_thumbnail)
                view.item_gallery_thumbnail.visibility = View.VISIBLE
            }
        }
    }


}