package com.barlipdev.dwyf.app.profile.shoppinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.databinding.ProductShoppinglistItemBinding
import com.barlipdev.dwyf.network.responses.Product

class ShoppingListProductAdapter(private val products: List<Product>):RecyclerView.Adapter<ShoppingListProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val binding: ProductShoppinglistItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.product = product
            binding.productItemLayout.animation = AnimationUtils.loadAnimation(binding.productName.context,
                R.anim.anim1)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            ProductShoppinglistItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount() = products.size

}