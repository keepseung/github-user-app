package drama.github.user.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * ViewModel의 scope안에서 Disposable 객체를 관리하기 위한 BaseViewModel
 */
abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    // 사용자가 입력한 검색어
    val searchText = MutableLiveData<String?>()

}