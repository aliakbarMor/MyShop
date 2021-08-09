package mor.aliakbar.tavaloodshop.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val progressbarStatusLiveData = MutableLiveData<Boolean>()

}