package drama.github.user.presentation.main.bookmark

import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import drama.github.user.R
import drama.github.user.databinding.FragmentBookmarkUserBinding
import drama.github.user.model.SearchUserModel
import drama.github.user.model.UserGroupModel
import drama.github.user.presentation.base.BaseFragment
import drama.github.user.presentation.main.UserGroupAdapter
import drama.github.user.util.Util
import drama.github.user.util.choseng.GetChoSeng
import drama.github.user.util.recyclerview.LineDividerItemDecoration
import drama.github.user.util.recyclerview.SpacingDecoration
import javax.inject.Inject

/**
 * 북마크한 사용자 리스트를 보여주기, 해제하기 기능을 위한 Fragment
 */
class BookmarkUserFragment : BaseFragment<FragmentBookmarkUserBinding, BookmarkUserViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var bookmarkUserViewModel: BookmarkUserViewModel

    override fun getLayoutId(): Int = R.layout.fragment_bookmark_user

    override fun getViewModel(): BookmarkUserViewModel {
        bookmarkUserViewModel = viewModel(BookmarkUserViewModel::class.java, viewModelFactory)
        binding.viewModel = bookmarkUserViewModel
        return bookmarkUserViewModel
    }

    override fun initViews(savedInstanceState: Bundle?) {

        // 리사이클러뷰 설정
        binding.recyclerviewBookmarkUser.apply {
            // 초성별로 라인 생성되도록 함
            addItemDecoration(
                LineDividerItemDecoration(
                    context,
                    LinearLayout.VERTICAL,
                    Util.dpToPixel(context, 1f),
                    ContextCompat.getColor(context, R.color.gray)
                )
            )
            // 초성별로 공백 생성되도록 함
            addItemDecoration(SpacingDecoration(Util.dpToPixel(context, PER_GROUP_SPACE)))
            // 어뎁터 설정
            adapter = UserGroupAdapter().apply {
                bookmarkEvent = { searchUserModel ->
                    changeBookmark(searchUserModel)
                }
                setHasStableIds(true)
            }
        }
    }

    private fun changeBookmark(searchUserModel: SearchUserModel) {
        // 북마크인 상태인 경우에만 북마크 해제 API 호출함
        if (searchUserModel.bookmark) {
            bookmarkUserViewModel.deleteBookmarkUser(searchUserModel.id)
        }
    }

    override fun observeData() {
        bookmarkUserViewModel.bookmarkUserStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is BookmarkUserState.Uninitialized -> {
                    handleLoading(false)
                }
                is BookmarkUserState.Loading -> {
                    handleLoading(true)
                }
                is BookmarkUserState.Success -> {
                    handleLoading(false)
                    handleSuccess(it)
                }
                is BookmarkUserState.Error -> {
                    handleLoading(false)
                    handleError(it)
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressbarBookmarkUser.isVisible = isLoading
    }

    private fun handleError(error: BookmarkUserState.Error) {
        showToastMessage(getString(error.errorMessageId))
    }

    private fun handleSuccess(successResponse: BookmarkUserState.Success) {

        when (successResponse) {
            is BookmarkUserState.Success.GetUser -> {
                bindUserList(successResponse)
            }

            is BookmarkUserState.Success.DeleteBookmark -> {
                handleBookmark(successResponse)
            }
        }

    }

    private fun handleBookmark(successResponse: BookmarkUserState.Success) {
        if (successResponse is BookmarkUserState.Success.DeleteBookmark) {
            showToastMessage(getString(successResponse.messageId))
        }
    }

    private fun updateBookmarkInList(id: Long, bookmark:Boolean) {

        // 북마크 등록, 해제 정보를 리스트 UI에 알려준다.
        (binding.recyclerviewBookmarkUser.adapter as UserGroupAdapter).apply {
            this.userGroupModelList.forEachIndexed { groupIndex, userGroupModel ->
                userGroupModel.userList.forEachIndexed { index, searchUserModel ->
                    // 중첩 리사이클러뷰 안에서 북마크 수정하려는 인덱스만 업데이트 한다.
                    if (searchUserModel.id == id) {
                        searchUserModel.bookmark = bookmark
                        updateBookmark(groupIndex, index)
                        return
                    }
                }

            }
        }
    }

    private fun bindUserList(successResponse: BookmarkUserState.Success.GetUser) {
        // 검색 결과가 없는 경우 없다는 텍스트를 보여준다.
        val _searchUserModelList = successResponse.searchUserModelList
        if (_searchUserModelList.isEmpty()) {
            binding.recyclerviewBookmarkUser.isVisible = false
            binding.textviewNoResultBookmarkUser.isVisible = true
            return
        }
        binding.textviewNoResultBookmarkUser.isVisible = false
        binding.recyclerviewBookmarkUser.isVisible = true

        // 전체 리스트에서 사용하는 초성들을 가져온다.
        val choSengSet = mutableSetOf<String>()
        _searchUserModelList.forEach {
            choSengSet.add(GetChoSeng.getChoseng(it.name))
        }

        // 초성을 그룹으로 하는 사용자 리스트를 만든다.
        val userGroupModelList = ArrayList<UserGroupModel>()
        if (choSengSet.isNotEmpty()) {
            choSengSet.forEach { choSeng ->

                val searchUserModelList: ArrayList<SearchUserModel> = _searchUserModelList
                    .filter { searchUser -> GetChoSeng.getChoseng(searchUser.name) == choSeng }
                    .map {
                        SearchUserModel(
                            id = it.id,
                            name = it.name,
                            profileImageUrl = it.profileImageUrl,
                            bookmark = true
                        )
                    }
                    .toCollection(ArrayList())
                userGroupModelList.add(
                    UserGroupModel(
                        title = choSeng,
                        userList = searchUserModelList
                    )
                )

            }
        }

        (binding.recyclerviewBookmarkUser.adapter as UserGroupAdapter).addUserGroupModel(userGroupModelList)
    }

    companion object {
        const val PER_GROUP_SPACE = 8
    }
}