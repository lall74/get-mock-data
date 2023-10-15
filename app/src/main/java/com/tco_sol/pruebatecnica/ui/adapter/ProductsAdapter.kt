package com.tco_sol.pruebatecnica.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tco_sol.pruebatecnica.data.model.Product
import com.tco_sol.pruebatecnica.databinding.ListItemProductRvLayoutBinding

class ProductsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Product, ProductsAdapter.ViewHolderProduct>(ProductsDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProduct {
        return ViewHolderProduct.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolderProduct, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(item) }
        holder.bind(item)
    }

    class ViewHolderProduct private constructor(val binding: ListItemProductRvLayoutBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.item = item

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolderProduct {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemProductRvLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolderProduct(binding, parent.context)
            }
        }
    }

    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }
}

class ProductsDiffCallBack : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}