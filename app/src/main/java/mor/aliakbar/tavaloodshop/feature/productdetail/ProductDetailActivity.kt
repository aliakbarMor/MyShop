package mor.aliakbar.tavaloodshop.feature.productdetail

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.customview.scrollview.ObservableScrollViewCallbacks
import mor.aliakbar.tavaloodshop.customview.scrollview.ScrollState
import mor.aliakbar.tavaloodshop.databinding.ActivityProductDetailBinding
import mor.aliakbar.tavaloodshop.feature.addcomment.AddCommentActivity
import mor.aliakbar.tavaloodshop.feature.commentList.CommentListActivity
import mor.aliakbar.tavaloodshop.feature.common.adapter.CommentAdapter
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import mor.aliakbar.tavaloodshop.services.loaddingImage.FrescoLoadingImage
import mor.aliakbar.tavaloodshop.utils.ActivityUtils.backToPreviousPageListener
import mor.aliakbar.tavaloodshop.utils.ActivityUtils.startActivityWithIdExtra
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.TextUtils.formatPrice
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityProductDetailBinding =
        { layoutInflater: LayoutInflater -> ActivityProductDetailBinding.inflate(layoutInflater) }
    private val viewModel: ProductDetailViewModel by viewModels()

    @Inject
    lateinit var frescoLoadingImage: FrescoLoadingImage

    @Inject
    lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        observes()

    }

    override fun onStart() {
        super.onStart()
        viewModel.getComments()
    }

    private fun initialize() {
        binding.addToCartExtendedFabProductDetail.setOnClickListener {
            runBlocking {
                viewModel.addToCart().flowCatch().collect {
                    showSnackBar(getString(R.string.success_addToCart))
                }
            }
        }

        binding.viewAllCommentsButtonProductDetail.setOnClickListener {
            startActivityWithIdExtra(CommentListActivity::class.java, viewModel.product.value!!.id)
        }

        binding.insertCommentMaterialButtonProductDetail.setOnClickListener {
            startActivityWithIdExtra(AddCommentActivity::class.java, viewModel.product.value!!.id)
        }

        binding.toolbar.onBackButtonClickListener = backToPreviousPageListener()
    }

    private fun observes() {
        viewModel.product.observe(this) { product ->
            binding.apply {
                productPreviousPriceTextViewProductDetail.text =
                    formatPrice(viewModel.product.value!!.currentPrice + viewModel.product.value!!.discount)
                productPreviousPriceTextViewProductDetail.paintFlags =
                    Paint.STRIKE_THRU_TEXT_FLAG
                productCurrentPriceTextViewProductDetail.text =
                    formatPrice(viewModel.product.value!!.currentPrice)
                productTitleTextViewProductDetail.text =
                    viewModel.product.value!!.title
                toolbarTitleTextViewProductDetail.text =
                    viewModel.product.value!!.title
                frescoLoadingImage.loadImage(
                    binding.productImageViewProductDetail, viewModel.product.value!!.imageUrl
                )
                favoriteIv.setOnClickListener {
                    product.isFavorite = !product.isFavorite
                    viewModel.onFavoriteIconClick()
                    if (product.isFavorite)
                        favoriteIv.setImageResource(R.drawable.ic_favorite_fill)
                    else
                        favoriteIv.setImageResource(R.drawable.ic_favorites)
                }
                if (product.isFavorite)
                    favoriteIv.setImageResource(R.drawable.ic_favorite_fill)

                val toolbar = toolbarProductDetail
                val imageProduct = productImageViewProductDetail
                observableScrollViewProductDetail.addScrollViewCallbacks(object :
                    ObservableScrollViewCallbacks {
                    override fun onScrollChanged(
                        scrollY: Int, firstScroll: Boolean, dragging: Boolean
                    ) {
                        toolbar.alpha = scrollY.toFloat() / imageProduct.height
                        imageProduct.translationY = scrollY.toFloat() / 2

                    }

                    override fun onDownMotionEvent() {
                    }

                    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                    }

                })
            }
        }

        viewModel.comments.observe(this) {
            commentAdapter.comments = it as ArrayList<Comment>
            binding.commentsRecyclerViewProductDetail.adapter = commentAdapter

            if (it.size > 3) {
                binding.viewAllCommentsButtonProductDetail.visibility = View.VISIBLE
            }
        }

    }

}