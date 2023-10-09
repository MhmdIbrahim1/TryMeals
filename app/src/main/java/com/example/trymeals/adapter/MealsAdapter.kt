package com.example.trymeals.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.Category
import com.example.trymeals.databinding.CategoryItemBinding


class MealsAdapter : RecyclerView.Adapter<MealsAdapter.ViewHolder>(){

    class ViewHolder(private val itemBinding: CategoryItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: Category) {
            itemBinding.categoryNameTv.text = category.strCategory
            itemBinding.categoryDesTv.text = category.strCategoryDescription
            Glide.with(itemBinding.root.context).load(category.strCategoryThumb)
                .circleCrop()
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(itemBinding.categoryIv)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.bind(category)
    }


}