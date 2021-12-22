package drama.github.user.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import drama.github.user.R
import drama.github.user.databinding.ActivityMainBinding
import drama.github.user.presentation.base.BaseActivity
import drama.github.user.presentation.main.bookmark.BookmarkUserFragment
import drama.github.user.presentation.main.search.SearchUserFragment
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel

    override fun getLayoutId(): Int = R.layout.activity_main
    override fun getViewModel(): MainViewModel {
        mainViewModel = viewModel(MainViewModel::class.java, viewModelFactory)
        return mainViewModel
    }

    override fun initViews(savedInstanceState: Bundle?) {

        // TabLayout 및 ViewPage 설정
        val fragmentList = ArrayList<Fragment>().apply {
            add(SearchUserFragment())
            add(BookmarkUserFragment())
        }
        val mainViewPagerAdapter = MainViewPagerAdapter(this, fragmentList)

        binding.viewpagerMain.apply {
            adapter = mainViewPagerAdapter
        }

        TabLayoutMediator(binding.tabLayoutMain, binding.viewpagerMain) { tab, position ->
        }.attach()

        binding.tabLayoutMain.apply {
            getTabAt(0)?.text =getString(R.string.tab_api)
            getTabAt(1)?.text =getString(R.string.tab_local)
        }
    }

    override fun observeData() {

    }
}