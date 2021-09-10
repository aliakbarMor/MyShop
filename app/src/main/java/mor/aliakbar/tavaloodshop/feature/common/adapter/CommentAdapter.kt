package mor.aliakbar.tavaloodshop.feature.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mor.aliakbar.tavaloodshop.databinding.ItemCommentBinding
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import mor.aliakbar.tavaloodshop.utils.DiffUtilCallBack
import javax.inject.Inject


class CommentAdapter @Inject constructor() :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var comments = ArrayList<Comment>()
        set(value) {
            DiffUtil.calculateDiff(DiffUtilCallBack(field, value)).dispatchUpdatesTo(this)
            field = value
        }
    var isPreview = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCommentBinding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = if (comments.size > 3 && isPreview) 3 else comments.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(comments[position])


    inner class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.apply {
                commentTitleTextViewItem.text = comment.title
                commentDateTextViewItem.text = comment.date
                commentAuthorTextViewItem.text = comment.author!!.email
                commentContentTextViewItem.text = comment.content
            }
        }
    }
}