package drama.github.user.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Job

/**
 * Databinding과 ViewModel을 정의하는 기본 프레그먼트
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : DaggerFragment() {
    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getViewModel(): VM
    abstract fun initViews(savedInstanceState: Bundle?)
    abstract fun observeData()

    protected lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =inflateDataBinding(inflater, getLayoutId(), container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(savedInstanceState)
        getViewModel()
        observeData()
    }

    fun <T : ViewDataBinding> inflateDataBinding(
        inflater: LayoutInflater,
        layoutId: Int,
        parent: ViewGroup?
    ): T {
        val binding = DataBindingUtil.inflate<T>(inflater, layoutId, parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding
    }

    fun <T : ViewModel> viewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass)
    }

    fun <T : ViewModel> viewModel(modelClass: Class<T>, factory: ViewModelProvider.Factory): T {
        return ViewModelProvider(this, factory).get(modelClass)
    }

    fun showToastMessage(msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}