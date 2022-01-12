package com.dessy.screeningtest_intern.ui

import android.app.Activity
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dessy.screeningtest_intern.DataItem
import com.dessy.screeningtest_intern.databinding.ItemUserBinding
import com.dessy.screeningtest_intern.util.Constant

class UserAdapter(var activity: Activity): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var list = ArrayList<DataItem>()

    fun addList(data: List<DataItem>) {
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class UserViewHolder(private val binding: ItemUserBinding, private val activity: Activity)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem?) {
            if (item != null) {
                val data: String = item.first_name + " " + item.last_name

                binding.tvName.text = data
                binding.tvEmail.text = item.email
                Glide.with(itemView.context)
                    .load(item.avatar)
                    .into(binding.imgAvatar)

                itemView.setOnClickListener {
                    val sharedPref: SharedPreferences = activity.getSharedPreferences(Constant.SHARED_PREF_USER, 0)
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString(Constant.KEY_NAME, data)
                    editor.apply()
                    activity.finish()
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}
