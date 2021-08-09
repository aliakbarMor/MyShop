package mor.aliakbar.tavaloodshop.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import mor.aliakbar.tavaloodshop.services.exception.AppExceptionMapper
import org.greenrobot.eventbus.EventBus

object CoroutineUtils {

    fun <T> Flow<T>.flowCatch(): Flow<T> {
        return catch {
            Log.d("error get data", it.message.toString())
            EventBus.getDefault().post(AppExceptionMapper.map(it))
        }
    }

    fun <T> Flow<T>.flowSetProgressBar(progressbarStatusLiveData: MutableLiveData<Boolean>): Flow<T> {
        return onStart {
            progressbarStatusLiveData.value = true
        }.onCompletion {
            progressbarStatusLiveData.value = false
        }
    }

}