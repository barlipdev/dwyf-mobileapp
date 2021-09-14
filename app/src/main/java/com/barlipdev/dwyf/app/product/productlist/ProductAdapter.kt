package com.barlipdev.dwyf.app.product.productlist

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.databinding.ProductItemBinding
import com.barlipdev.dwyf.network.responses.Product
import com.barlipdev.dwyf.network.responses.UsefulnessState

class ProductAdapter(private val products: List<Product>):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val binding: ProductItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.product = product
            binding.productItemLayout.animation = AnimationUtils.loadAnimation(binding.productName.context,
                R.anim.anim1)
            if (product.usefulnessState == UsefulnessState.GOOD){
                binding.productExpiryDate.setTextColor(Color.parseColor("#6bfc03"))
            }else if (product.usefulnessState == UsefulnessState.CLOSEEXPIRYDATE){
                binding.productExpiryDate.setTextColor(Color.parseColor("#EEC84B"))
            }else if (product.usefulnessState == UsefulnessState.EXPIRED){
                binding.productExpiryDate.setTextColor(Color.parseColor("#fc0303"))
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount() = products.size

}