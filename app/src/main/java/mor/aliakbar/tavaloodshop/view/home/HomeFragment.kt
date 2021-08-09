package mor.aliakbar.tavaloodshop.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import mor.aliakbar.tavaloodshop.base.BaseFragment
import mor.aliakbar.tavaloodshop.databinding.FragmentHomeBinding
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.utils.Variable
import mor.aliakbar.tavaloodshop.utils.hideKeyboard
import mor.aliakbar.tavaloodshop.view.common.adapter.ProductAdapter
import mor.aliakbar.tavaloodshop.view.common.adapter.ProductListener
import mor.aliakbar.tavaloodshop.view.productdetail.ProductDetailActivity
import mor.aliakbar.tavaloodshop.view.home.banner.BannerSliderAdapter
import mor.aliakbar.tavaloodshop.view.productlist.ProductListActivity
import javax.inject.Inject
import kotlin.math.abs


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), ProductListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            FragmentHomeBinding.inflate(layoutInflater, viewGroup, b)
        }
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var latestProductAdapter: ProductAdapter

    @Inject
    lateinit var popularProductAdapter: ProductAdapter

    @Inject
    lateinit var searchAdapter: SearchAdapter

    override fun onProductClick(product: Product) {
        val intent = Intent(requireActivity(), ProductDetailActivity::class.java).apply {
            putExtra(Variable.EXTRA_KEY_DATA, product)
        }
        startActivity(intent)
    }

    override fun onFavoriteIconClick(product: Product) {
        if (!product.isFavorite) {
            viewModel.addToFavorite(product)
        } else
            viewModel.removeFromFavorite(product)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        latestProductAdapter.productListener = this
        latestProductAdapter.viewType = Variable.PRODUCT_VIEW_TYPE_ROUNDED
        binding.latestProductRecyclerViewHome.adapter = latestProductAdapter

        popularProductAdapter.productListener = this
        popularProductAdapter.viewType = Variable.PRODUCT_VIEW_TYPE_ROUNDED
        binding.popularProductRecyclerViewHome.adapter = popularProductAdapter

        searchAdapter.onProductClick = {
            startActivity(Intent(requireActivity(), ProductDetailActivity::class.java).apply {
                putExtra(Variable.EXTRA_KEY_DATA, it)
            })
        }
        binding.searchRecycler.adapter = searchAdapter

        observe()
        setListener()

    }

    private fun observe() {
        viewModel.products.observe(viewLifecycleOwner) {
            if (it.first == Variable.PRODUCT_SORT_LATEST)
                latestProductAdapter.products = it.second as ArrayList<Product>
            else if (it.first == Variable.PRODUCT_SORT_POPULAR)
                popularProductAdapter.products = it.second as ArrayList<Product>

        }
        viewModel.progressbarStatusLiveData.observe(viewLifecycleOwner) {
            showProgressbar(it)
        }

        viewModel.banners.observe(viewLifecycleOwner) {
            var sliderJob: Job? = null
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))
                addTransformer { page: View, position: Float ->
                    val r = 1 - abs(position)
                    page.scaleY = 0.85f + r * 0.15f
                }
            }
            binding.bannerSlider.apply {
                adapter = bannerSliderAdapter
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                setPageTransformer(compositePageTransformer)

                val eh = CoroutineExceptionHandler { _, e -> Log.d("exception handler:", "$e") }
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        sliderJob?.cancel()
                        sliderJob = CoroutineScope(Dispatchers.IO + eh).launch {
                            delay(5000)
                            binding.bannerSlider.post {
                                if (it.size - 1 != binding.bannerSlider.currentItem)
                                    binding.bannerSlider.currentItem =
                                        binding.bannerSlider.currentItem + 1
                                else
                                    binding.bannerSlider.currentItem = 0
                            }

                        }
                    }
                })
            }
            binding.sliderIndicatorHome.setViewPager2(binding.bannerSlider)
        }

        viewModel.searchProducts.observe(viewLifecycleOwner) {
            if (it != null) {
                searchAdapter.products = it as ArrayList<Product>
                binding.searchRecycler.visibility = View.VISIBLE
                binding.layoutBelowSearch.visibility = View.GONE
            } else {
                binding.searchRecycler.visibility = View.GONE
                binding.layoutBelowSearch.visibility = View.VISIBLE
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListener() {

        binding.latestViewAllButtonHome.setOnClickListener {
            startActivity(Intent(requireActivity(), ProductListActivity::class.java).apply {
                putExtra(Variable.EXTRA_KEY_DATA, Variable.PRODUCT_SORT_LATEST)
            })
        }

        binding.popularViewAllButtonHome.setOnClickListener {
            startActivity(Intent(requireActivity(), ProductListActivity::class.java).apply {
                putExtra(Variable.EXTRA_KEY_DATA, Variable.PRODUCT_SORT_POPULAR)
            })
        }


        binding.searchEditText.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.rawX >= (binding.searchEditText.right - (binding.searchEditText.compoundDrawables[2].bounds.width() + 32))) {
                viewModel.search(binding.searchEditText.text.toString())
                hideKeyboard(requireActivity())
                return@OnTouchListener true
            }
            return@OnTouchListener false
        })
        binding.searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(binding.searchEditText.text.toString())
                hideKeyboard(requireActivity())
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
    }

}