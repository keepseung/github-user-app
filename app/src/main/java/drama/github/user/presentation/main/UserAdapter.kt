package drama.github.user.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import drama.github.user.R
import drama.github.user.databinding.ItemUserBinding
import drama.github.user.model.SearchUserModel
import drama.github.user.util.extension.load

/**
 * 사용자 1명에 대한 정보를 보여주기 위한 어뎁터
 */
class UserAdapter : RecyclerView.Adapter<UserAdapter.SearchUserViewHolder>() {
    var userModelList: ArrayList<SearchUserModel> = ArrayList()
    var bookmarkClickListener: ((SearchUserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchUserViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        holder.bind(userModelList[position])
    }

    override fun getItemId(position: Int): Long {
        return userModelList[position].id
    }
    override fun getItemCount(): Int = userModelList.size

    inner class SearchUserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(searchUserModel: SearchUserModel) {
            binding.userModel = searchUserModel
            binding.imageviewUser.load(searchUserModel.profileImageUrl)
            binding.imageviewBookmark.isSelected = searchUserModel.bookmark

            binding.imageviewBookmark.setOnClickListener {
                bookmarkClickListener?.invoke(searchUserModel)
            }
        }
    }

    fun addUserModel(userModelList: ArrayList<SearchUserModel>){
        this.userModelList = userModelList
        notifyDataSetChanged()
    }
}