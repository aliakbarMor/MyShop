package mor.aliakbar.tavaloodshop.model.source

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mor.aliakbar.tavaloodshop.model.api.ApiService
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import javax.inject.Inject

class CommentRemoteSource @Inject constructor(private val apiService: ApiService) :
    CommentDataSource {

    override fun getAll(productId: Int): Flow<List<Comment>> {
        return flow {
            emit(apiService.getComment(productId.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override fun add(comment: Comment, productId: Int): Flow<Comment> {
        return flow {
            emit(apiService.addComment(JsonObject().apply {
                addProperty("title", comment.title)
                addProperty("content", comment.content)
                addProperty("product_id", productId)
            }))
        }.flowOn(Dispatchers.IO)
    }

}