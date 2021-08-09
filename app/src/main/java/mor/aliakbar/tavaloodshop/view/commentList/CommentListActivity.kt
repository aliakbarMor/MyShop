package mor.aliakbar.tavaloodshop.view.commentList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityCheckOutBinding
import mor.aliakbar.tavaloodshop.databinding.ActivityCommentListBinding
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import mor.aliakbar.tavaloodshop.view.checkout.CheckOutViewModel
import mor.aliakbar.tavaloodshop.view.common.adapter.CommentAdapter
import javax.inject.Inject

@AndroidEntryPoint
class CommentListActivity : BaseActivity<ActivityCommentListBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCommentListBinding =
        { layoutInflater: LayoutInflater -> ActivityCommentListBinding.inflate(layoutInflater) }
    private val viewModel: CommentListViewModel by viewModels()

    @Inject
    lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.comments.observe(this) {
            commentAdapter.isPreview = false
            commentAdapter.comments = it as ArrayList<Comment>
            binding.commentsRecyclerView.adapter = commentAdapter
        }

        viewModel.progressbarStatusLiveData.observe(this) {
            showProgressbar(it)
        }

        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }
}