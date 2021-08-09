package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.Comment

interface CommentRepository {

    fun getAll(productId: Int): Flow<List<Comment>>

    fun add(comment: Comment, productId: Int): Flow<Comment>

}