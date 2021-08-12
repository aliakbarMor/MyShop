package mor.aliakbar.tavaloodshop.feature.addcomment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import mor.aliakbar.tavaloodshop.model.repository.CommentRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

@HiltViewModel
class AddCommentViewModel @Inject constructor(
    state: SavedStateHandle,
    private val commentRepository: CommentRepository
) : BaseViewModel() {

    private val productId = state.get<Int>(Variable.EXTRA_KEY_ID)!!
    val commentStatus = MutableLiveData<Comment>()

    fun addComment(comment: Comment) {
        viewModelScope.launch {
            commentRepository.add(comment, productId).flowCatch().collect {
                commentStatus.value = it
            }
        }
    }
}