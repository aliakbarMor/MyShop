package mor.aliakbar.tavaloodshop.view.addcomment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityAddCommentBinding
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import mor.aliakbar.tavaloodshop.utils.Variable
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class AddCommentActivity : BaseActivity<ActivityAddCommentBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityAddCommentBinding =
        { layoutInflater -> ActivityAddCommentBinding.inflate(layoutInflater) }
    val viewModel: AddCommentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.commentStatus.observe(this) {
            if (it != null) {
                binding.addCommentBottom.visibility = View.GONE
                showSnackBar(getString(R.string.addingCommentSuccessfully))
                Timer("finishAddCommentActivity").schedule(3000) {
                    finish()
                }
            }
        }

        binding.addCommentBottom.setOnClickListener {
            if (!checkError()) {
                viewModel.addComment(
                    Comment(
                        id = 0,
                        title = binding.commentTitleEt.text.toString(),
                        content = binding.commentContentEt.text.toString()
                    )
                )
            }
        }

        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }

    private fun checkError(): Boolean {
        var error = false
        if (binding.commentTitleEt.text.isNullOrEmpty()) {
            binding.commentTitleEt.error = getString(R.string.commentTitleError)
            error = true
        }
        if (binding.commentContentEt.text.isNullOrEmpty()) {
            binding.commentContentEt.error = getString(R.string.commentContentError)
            error = true
        }
        return error
    }
}