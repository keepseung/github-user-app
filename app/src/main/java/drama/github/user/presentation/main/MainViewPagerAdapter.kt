package drama.github.user.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(
    fa: FragmentActivity,
    val pages: List<Fragment>
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = pages.size
    override fun createFragment(position: Int): Fragment = pages[position]
}