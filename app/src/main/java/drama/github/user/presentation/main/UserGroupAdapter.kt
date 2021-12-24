package drama.github.user.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import drama.github.user.R
import drama.github.user.databinding.ItemUserGroupBinding
import drama.github.user.model.SearchUserModel
import drama.github.user.model.UserGroupModel

/**
 * 사용자 초성을 기준으로 사용자 리스트를 보여주기 위한 어뎁터
 */
class UserGroupAdapter : RecyclerView.Adapter<UserGroupAdapter.SearchUserGroupViewHolder>() {
    var userGroupModelList: ArrayList<UserGroupModel> = ArrayList()
    private var userRecyclerViewAdapterList = ArrayList<UserAdapter>()
    var bookmarkEvent: ((SearchUserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserGroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchUserGroupViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_user_group,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchUserGroupViewHolder, position: Int) {
        holder.bind(userGroupModelList[position])
    }

    override fun getItemCount(): Int = userGroupModelList.size

    override fun getItemId(position: Int): Long {
        return userGroupModelList[position].title.hashCode().toLong()
    }

    // 실제 북마크 추가 해제한 아이템만 변화를 준다.
    fun updateBookmark(groupPosition: Int, userPosition:Int){
        userRecyclerViewAdapterList[groupPosition].notifyItemChanged(userPosition)
    }

    inner class SearchUserGroupViewHolder(val binding: ItemUserGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userGroupModel: UserGroupModel) {
            binding.userGroupModel = userGroupModel

            val userAdapter = UserAdapter().apply {
                addUserModel(userGroupModel.userList)
                this.bookmarkClickListener = {
                    bookmarkEvent?.invoke(it)
                }
                setHasStableIds(true)
            }
            userRecyclerViewAdapterList.add(userAdapter)
            binding.recyclerviewUser.apply {
                adapter = userAdapter

                itemAnimator = null
            }
        }
    }

    fun addUserGroupModel(userModelList: ArrayList<UserGroupModel>){
        userRecyclerViewAdapterList = ArrayList()
        this.userGroupModelList = userModelList
        notifyDataSetChanged()
    }
}