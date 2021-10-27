package com.barlipdev.dwyf.app.recipe.recipeingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.databinding.ProductItemBinding
import com.barlipdev.dwyf.databinding.RecipeProductItemBinding
import com.barlipdev.dwyf.network.responses.Product

class RecipeProductsAdapter(private val products: List<Product>):RecyclerView.Adapter<RecipeProductsAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val binding: RecipeProductItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.recipeProduct = product
            binding.recipeProductItemLayout.animation = AnimationUtils.loadAnimation(binding.productName.context,
                R.anim.anim1)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(RecipeProductItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount() = products.size

}