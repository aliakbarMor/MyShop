package mor.aliakbar.tavaloodshop.feature.commentList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.repository.CommentRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowSetProgressBar
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

@HiltViewModel
class CommentListViewModel @Inject constructor(
    state: SavedStateHandle,
    commentRepository: CommentRepository
) : BaseViewModel() {

    var comments = liveData {
        commentRepository.getAll(state.get<Int>(Variable.EXTRA_KEY_ID)!!)
            .flowCatch()
            .flowSetProgressBar(progressbarStatusLiveData)
            .collect { emit(it) }

    }
}