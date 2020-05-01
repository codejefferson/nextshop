package com.nextshop.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.nextshop.R
import com.nextshop.mechanism.VerifyNetworkInfo
import com.nextshop.service.model.ProductDetailResponse
import com.nextshop.viewmodel.BaseViewModelFactory
import com.nextshop.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.Dispatchers
import me.relex.circleindicator.CircleIndicator

class DetailFragment : Fragment() {

    private val viewModelFactory: BaseViewModelFactory = BaseViewModelFactory(Dispatchers.Main, Dispatchers.IO)

    private val viewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ProductViewModel(Dispatchers.Main, Dispatchers.IO)::class.java)
    }

    companion object {
        const val KEY_PRODUCT_ID = "KEY_PRODUCT_ID"
    }

    private lateinit var productId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.arguments.let {
            productId = it?.getString(KEY_PRODUCT_ID) ?: ""
        }
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildActionBar()
        populateUi()
    }

    private fun buildActionBar() {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        activity.supportActionBar?.title = resources.getString(R.string.txt_progress)
    }

    private fun populateUi() {
        verifyConnectionState()
    }

    private fun verifyConnectionState() {
        context.let {
            if (VerifyNetworkInfo.isConnected(it!!)) {
                hideNoConnectionState()
                showStateProgress()
                fetchProduct()
            } else {
                hideStateProgress()
                showNoConnectionState()

                state_without_conn_detail.setOnClickListener {
                    verifyConnectionState()
                }
            }
        }
    }

    private fun hideStateProgress() {
        state_progress_detail.visibility = View.GONE
    }

    private fun showStateProgress() {
        state_progress_detail.visibility = View.VISIBLE
    }

    private fun showNoConnectionState() {
        state_without_conn_detail.visibility = View.VISIBLE
    }

    private fun hideNoConnectionState() {
        state_without_conn_detail.visibility = View.GONE
    }

    private fun fetchProduct() {
        viewModel.fetchProduct(productId).observe(this, Observer<ProductDetailResponse> {
            hideStateProgress()
            showProduct(it)
            product_container_content.visibility = View.VISIBLE
        })
    }

    private fun showProduct(product: ProductDetailResponse) {
        // photo
        context?.let {
            val viewPager = product_viewpager as ViewPager
            viewPager.adapter = ProductImagesPagerAdapter(it, product.productImages)

            val circleIndicator = product_viewpager_circle_indicator as CircleIndicator
            circleIndicator.setViewPager(viewPager)
        }

        // name
        product_name.text = product.productName
        (activity as AppCompatActivity).supportActionBar?.title = product.productName

        // price
        product_price.text = "€${product.salesPriceIncVat}"

        // rating
        product_rating.text = getString(
            R.string.txt_rating,
            product.reviewInformation.reviewSummary.reviewAverage.toString(),
            product.reviewInformation.reviewSummary.reviewCount.toString()
        )

        // description
        product_description.text = HtmlCompat.fromHtml(product.productText, FROM_HTML_MODE_COMPACT)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }
}