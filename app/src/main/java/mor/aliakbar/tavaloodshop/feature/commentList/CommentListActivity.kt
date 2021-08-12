package mor.aliakbar.tavaloodshop.feature.commentList

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityCommentListBinding
import mor.aliakbar.tavaloodshop.feature.common.adapter.CommentAdapter
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import mor.aliakbar.tavaloodshop.utils.ActivityUtils.backToPreviousPageListener
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

        initialize()
        observes()

    }

    private fun observes() {
        viewModel.comments.observe(this) {
            commentAdapter.isPreview = false
            commentAdapter.comments = it as ArrayList<Comment>
            binding.commentsRecyclerView.adapter = commentAdapter
        }

        viewModel.progressbarStatusLiveData.observe(this) {
            showProgressbar(it)
        }
    }

    private fun initialize() {
        binding.toolbar.onBackButtonClickListener = backToPreviousPageListener()
    }
}