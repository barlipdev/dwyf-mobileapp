package com.barlipdev.dwyf.app.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.databinding.ShoppinglistItemBinding
import com.barlipdev.dwyf.network.responses.ShoppingList

class ShoppingListsAdapter(private val shoppingLists: List<ShoppingList>): RecyclerView.Adapter<ShoppingListsAdapter.ShoppingListViewHolder>() {

    class ShoppingListViewHolder(private val binding: ShoppinglistItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(shoppingList: ShoppingList){
            binding.shoppingList = shoppingList
//            binding.recipeProductItemLayout.animation = AnimationUtils.loadAnimation(binding.productName.context,
//                R.anim.anim1)
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
        holder.bind(shoppingList)
    }

    override fun getItemCount() = shoppingLists.size

}