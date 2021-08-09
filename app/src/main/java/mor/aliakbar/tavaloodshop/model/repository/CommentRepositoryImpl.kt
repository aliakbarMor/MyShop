package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import mor.aliakbar.tavaloodshop.model.source.CommentDataSource
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(private val commentRemoteSource: CommentDataSource) :
    CommentRepository {

    override fun getAll(productId: Int): Flow<List<Comment>> {
        return commentRemoteSource.getAll(productId)
    }

    override fun add(comment: Comment, productId: Int): Flow<Comment> {
        return commentRemoteSource.add(comment, productId)
    }
}