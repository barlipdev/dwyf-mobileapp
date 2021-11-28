package com.barlipdev.dwyf.app.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.databinding.ShoppinglistItemBinding
import com.barlipdev.dwyf.network.responses.ShoppingList

class ShoppingListsAdapter(val clickListener: ShoppingListListener, val shoppingLists: List<ShoppingList>): RecyclerView.Adapter<ShoppingListsAdapter.ShoppingListViewHolder>() {

    class ShoppingListViewHolder(private val binding: ShoppinglistItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: ShoppingListListener, shoppingList: ShoppingList){
            binding.shoppingList = shoppingList
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingListViewHolder{
        return ShoppingListViewHolder(ShoppinglistItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val shoppingList = shoppingLists[position]
        holder.bind(clickListener,shoppingList)
    }

    override fun getItemCount() = shoppingLists.size

    class ShoppingListListener(val clickListener: (shoppingList: ShoppingList) -> Unit){
        fun onClick(shoppingList: ShoppingList) = clickListener(shoppingList)
    }
}