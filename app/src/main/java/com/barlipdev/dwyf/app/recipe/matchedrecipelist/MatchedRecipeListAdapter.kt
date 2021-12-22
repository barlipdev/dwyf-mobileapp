package com.barlipdev.dwyf.app.recipe.matchedrecipelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barlipdev.dwyf.databinding.MatchedRecipeItemBinding
import com.barlipdev.dwyf.network.responses.MatchedRecipe
import com.barlipdev.dwyf.utils.visible

class MatchedRecipeListAdapter(val clickListener: MatchedRecipeListListener, val matchedRecipeList: List<MatchedRecipe>): RecyclerView.Adapter<MatchedRecipeListAdapter.MatchedRecipeListViewHolder>() {

    class MatchedRecipeListViewHolder(private val binding: MatchedRecipeItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: MatchedRecipeListListener, matchedRecipe: MatchedRecipe){
            binding.matchedRecipe = matchedRecipe
            binding.clickListener = clickListener
            binding.ingredientsCount.setText("Liczba posiadanych produktów: "+matchedRecipe.availableProducts.size.toString())

            if (matchedRecipe.notAvailableProducts.isEmpty()){
                binding.readyIcon.visible(true)
                binding.cancelIcon.visible(false)
            }else{
                binding.cancelIcon.visible(true)
                binding.readyIcon.visible(false)
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchedRecipeListViewHolder {
        return MatchedRecipeListViewHolder(MatchedRecipeItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MatchedRecipeListViewHolder, position: Int) {
        val matchedRecipe = matchedRecipeList[position]
        holder.bind(clickListener,matchedRecipe)
    }

    override fun getItemCount() = matchedRecipeList.size

    class MatchedRecipeListListener(val clickListener: (matchedRecipe: MatchedRecipe) -> Unit){
        fun onClick(matchedRecipe: MatchedRecipe) = clickListener(matchedRecipe)
    }


}