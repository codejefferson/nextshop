package com.nextshop.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.nextshop.R
import com.nextshop.mechanism.VerifyNetworkInfo
import com.nextshop.service.model.ProductsItemResponse
import com.nextshop.viewmodel.BaseViewModelFactory
import com.nextshop.ui.detail.DetailFragment
import com.nextshop.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.coroutines.Dispatchers


class GalleryFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModelFactory: BaseViewModelFactory =
        BaseViewModelFactory(
            Dispatchers.Main,
            Dispatchers.IO
        )

    private val viewModel: ProductsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ProductsViewModel::class.java)
    }

    private lateinit var adapter: GalleryAdapter
    private var currentQuery: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildActionBar()
        buildSearchView()
        buildGallery()
        fetchProducts()
        verifyConnectionState()
    }

    private fun buildSearchView() {
        gallery_search.setOnQueryTextListener(this)
        gallery_search.queryHint = resources.getString(R.string.txt_search_hint)
    }

    private fun verifyConnectionState() {
        context.let {
            if (VerifyNetworkInfo.isConnected(it!!)) {
                hideNoConnectionState()
                showStateProgress()
                fetchProducts()
            } else {
                hideStateProgress()
                showNoConnectionState()

                state_without_conn_gallery.setOnClickListener {
                    verifyConnectionState()
                }
            }
        }
    }

    private fun buildActionBar() {
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false) // disable the button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false) // remove the left caret
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.app_name)
    }

    private fun buildGallery() {
        adapter = GalleryAdapter { onClickItem(it) }

        gallery_rv.layoutManager = GridLayoutManager(context, 2)
        gallery_rv.itemAnimator = DefaultItemAnimator()
        gallery_rv.adapter = adapter
    }

    private fun fetchProducts() {
        viewModel.fetchProducts(currentQuery).observe(this, Observer<List<ProductsItemResponse>> { it ->
            if (!it.isNullOrEmpty()) {
                adapter.setData(it)
                hideStateProgress()
                showList()
            }
        })
    }

    private fun showList() {
        gallery_rv.visibility = View.VISIBLE
    }

    private fun showStateProgress() {
        state_progress_gallery.visibility = View.VISIBLE
    }

    private fun hideStateProgress() {
        state_progress_gallery.visibility = View.GONE
    }

    private fun showNoConnectionState() {
        state_without_conn_gallery.visibility = View.VISIBLE
    }

    private fun hideNoConnectionState() {
        state_without_conn_gallery.visibility = View.GONE
    }

    private fun onClickItem(data: ProductsItemResponse) {
        val bundle = Bundle()
        bundle.putString(DetailFragment.KEY_PRODUCT_ID, data.productId)
        findNavController().navigate(R.id.action_gallery_to_detail, bundle, null, null)
    }

    override fun onQueryTextSubmit(p0: String?) = false

    override fun onQueryTextChange(query: String?): Boolean {
        query?.let {
            currentQuery = it
            adapter.filter(it)
        }
        return false
    }
}