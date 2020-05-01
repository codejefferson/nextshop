package com.nextshop.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.nextshop.R

class ProductImagesPagerAdapter(private val context: Context, private val images: List<String>) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, null) as ViewGroup

        populateImage(view, position)

        collection.addView(view)
        return view
    }

    private fun populateImage(view: ViewGroup, position: Int) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        Glide.with(imageView)
            .load(images[position])
            .error(R.drawable.ic_placeholder)
            .into(imageView)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getCount(): Int {
        return images.size
    }

}
