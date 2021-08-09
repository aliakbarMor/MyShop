package mor.aliakbar.tavaloodshop.view.productdetail

import android.content.Intent
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
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import mor.aliakbar.tavaloodshop.services.FrescoLoadingImage
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.Variable
import mor.aliakbar.tavaloodshop.utils.formatPrice
import mor.aliakbar.tavaloodshop.view.addcomment.AddCommentActivity
import mor.aliakbar.tavaloodshop.view.commentList.CommentListActivity
import mor.aliakbar.tavaloodshop.view.common.adapter.CommentAdapter
import javax.inject.Inject

@AndroidEntryPoint
class
ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityProductDetailBinding =
        { layoutInflater: LayoutInflater -> ActivityProductDetailBinding.inflate(layoutInflater) }
    private val viewModel: ProductDetailViewModel by viewModels()

    @Inject
    lateinit var frescoLoadingImage: FrescoLoadingImage

    @Inject
    lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()
        setListener()

        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }

    private fun setListener() {
        binding.viewAllCommentsButtonProductDetail.setOnClickListener {
            startActivity(Intent(this, CommentListActivity::class.java).apply {
                putExtra(Variable.EXTRA_KEY_ID, viewModel.product.value!!.id)
            })
        }

        binding.addToCartExtendedFabProductDetail.setOnClickListener {
            runBlocking {
                viewModel.addToCart().flowCatch()
                    .collect {
                        showSnackBar(getString(R.string.success_addToCart))
                    }
            }
        }
        binding.insertCommentMaterialButtonProductDetail.setOnClickListener {
            startActivity(Intent(this, AddCommentActivity::class.java).apply {
                putExtra(Variable.EXTRA_KEY_ID, viewModel.product.value!!.id)
            })
        }

    }

    private fun observe() {

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

                val toolbar = binding.toolbarProductDetail
                val imageProduct = binding.productImageViewProductDetail
                binding.observableScrollViewProductDetail.addScrollViewCallbacks(object :
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

    override fun onStart() {
        super.onStart()
        viewModel.getComments()
    }
}