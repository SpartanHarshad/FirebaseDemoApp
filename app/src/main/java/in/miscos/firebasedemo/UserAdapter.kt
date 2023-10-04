package `in`.miscos.firebasedemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import `in`.miscos.firebasedemo.databinding.ItemLayoutBinding

class UserAdapter(var users: List<UserItem>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setData(users[position])
    }

    fun sortUserList(newUsers: List<UserItem>){
        users=newUsers
        notifyDataSetChanged()
    }
    inner class UserViewHolder(val binding:ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(userItem: UserItem) {
            binding.tvAge.text = "${userItem.Age}"
            binding.tvName.text = userItem.name ?: ""
            binding.tvCity.text = userItem.City ?: ""
        }
    }
}