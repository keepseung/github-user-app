package drama.github.user.presentation.main.search

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import drama.github.user.R
import drama.github.user.databinding.FragmentSearchUserBinding
import drama.github.user.model.SearchUserModel
import drama.github.user.model.UserGroupModel
import drama.github.user.presentation.base.BaseFragment
import drama.github.user.presentation.main.UserGroupAdapter
import drama.github.user.util.Util.dpToPixel
import drama.github.user.util.choseng.GetChoSeng
import drama.github.user.util.recyclerview.LineDividerItemDecoration
import drama.github.user.util.recyclerview.SpacingDecoration
import javax.inject.Inject

/**
 * 사용자 이름 조회 북마크 등록 및 해제하기 기능을 위한 Fragment
 */
class SearchUserFragment : BaseFragment<FragmentSearchUserBinding, SearchUserViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var searchUserViewModel: SearchUserViewModel

    override fun getLayoutId(): Int = R.layout.fragment_search_user

    override fun getViewModel(): SearchUserViewModel {
        searchUserViewModel = viewModel(SearchUserViewModel::class.java, viewModelFactory)
        binding.viewModel = searchUserViewModel
        return searchUserViewModel
    }

    override fun initViews(savedInstanceState: Bundle?) {

        // 리사이클러뷰 설정
        binding.recyclerviewSearchUser.apply {

            // 초성별로 라인 생성되도록 함
            addItemDecoration(
                LineDividerItemDecoration(
                    context,
                    LinearLayout.VERTICAL,
                    dpToPixel(context, 1f),
                    ContextCompat.getColor(context, R.color.gray)
                )
            )
            // 초성별로 공백 생성되도록 함
            addItemDecoration(SpacingDecoration(dpToPixel(context, PER_GROUP_SPACE)))
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
        if (searchUserModel.bookmark) {
            searchUserViewModel.deleteBookmarkUser(searchUserModel.id)
        } else {
            searchUserViewModel.addBookmarkUser(searchUserModel.toEntity())
        }
    }

    override fun observeData() {
        searchUserViewModel.searchStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is SearchUserState.Uninitialized -> {
                    handleLoading(false)
                }
                is SearchUserState.Loading -> {
                    handleLoading(true)
                }
                is SearchUserState.Success -> {
                    handleLoading(false)
                    handleSuccess(it)
                }
                is SearchUserState.Error -> {
                    handleLoading(false)
                    handleError(it)
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressbarSearchUser.isVisible = isLoading
    }

    private fun handleError(error: SearchUserState.Error) {
        showToastMessage(getString(error.errorMessageId))
    }

    private fun handleSuccess(successResponse: SearchUserState.Success) {

        when (successResponse) {
            is SearchUserState.Success.GetUser -> {
                bindUserList(successResponse)
            }
            is SearchUserState.Success.AddBookmark -> {
                handleBookmark(successResponse)
            }
            is SearchUserState.Success.DeleteBookmark -> {
                handleBookmark(successResponse)
            }
        }

    }

    private fun handleBookmark(successResponse: SearchUserState.Success) {
        if (successResponse is SearchUserState.Success.AddBookmark) {
            // 북마크 등록 리스트에 반영함
            showToastMessage(getString(successResponse.messageId))
            updateBookmarkInList(successResponse.id, true)
        }
        if (successResponse is SearchUserState.Success.DeleteBookmark) {
            // 북마크 해제 리스트에 반영함
            showToastMessage(getString(successResponse.messageId))
            updateBookmarkInList(successResponse.id, false)
        }
    }

    private fun updateBookmarkInList(id: Long, bookmark:Boolean) {
        // 북마크 등록, 해제 정보를 리스트 UI에 알려준다.
        (binding.recyclerviewSearchUser.adapter as UserGroupAdapter).apply {
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

    private fun bindUserList(successResponse: SearchUserState.Success.GetUser) {
        // 검색 결과가 없는 경우 없다는 텍스트를 보여준다.
        val _searchUserModelList = successResponse.searchUserModelList
        if (_searchUserModelList.isEmpty()) {
            binding.recyclerviewSearchUser.isVisible = false
            binding.textviewNoResultSearchUser.isVisible = true
            return
        }
        binding.textviewNoResultSearchUser.isVisible = false
        binding.recyclerviewSearchUser.isVisible = true

        // 전체 리스트에서 사용하는 초성들을 가져온다.
        val choSengSet = mutableSetOf<String>()
        _searchUserModelList.forEach {
            choSengSet.add(GetChoSeng.getChoseng(it.name))
        }

        // 초성을 그룹으로 하는 사용자 정보 리스트를 만든다.
        val userGroupModelList = ArrayList<UserGroupModel>()
        if (choSengSet.isNotEmpty()) {
            choSengSet.forEach { choSeng ->

                val searchUserModelList: ArrayList<SearchUserModel> = _searchUserModelList
                    .filter { searchUser -> GetChoSeng.getChoseng(searchUser.name) == choSeng }
                    .map {
                        SearchUserModel(
                            id = it.id,
                            name = it.name,
                            profileImageUrl = it.profileImageUrl
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

        (binding.recyclerviewSearchUser.adapter as UserGroupAdapter).addUserGroupModel(userGroupModelList)
    }

    fun setDeleteBookmark(id: Long) {
        updateBookmarkInList(id, false)
    }

    companion object {
        const val PER_GROUP_SPACE = 8
    }
}