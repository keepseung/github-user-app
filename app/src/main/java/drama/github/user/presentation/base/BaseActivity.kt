package drama.github.user.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity

/**
 * Databinding과 ViewModel을 정의하는 기본 액티비티
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : DaggerAppCompatActivity() {
    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun initViews(savedInstanceState: Bundle?)
    abstract fun getViewModel(): VM
    abstract fun observeData()

    protected lateinit var binding: VB


    fun <T : ViewModel> viewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass)
    }

    fun <T : ViewModel> viewModel(modelClass: Class<T>, factory: ViewModelProvider.Factory): T {
        return ViewModelProvider(this, factory).get(modelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = contentViewDataBinding(getLayoutId())
        observeData()
        initViews(savedInstanceState)
    }

    fun <T : ViewDataBinding> contentViewDataBinding(
        layoutId: Int
    ): T {
        val binding = DataBindingUtil.setContentView<T>(this, layoutId)
        binding.lifecycleOwner = this
        return binding
    }

}