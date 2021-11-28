package com.barlipdev.dwyf.app.recipe.performingrecipe.performingrecipelist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barlipdev.dwyf.databinding.PerformingRecipeItemBinding
import com.barlipdev.dwyf.network.responses.PerformingRecipe
import com.barlipdev.dwyf.network.responses.RecipeStatus

class PerformingRecipesAdapter(val clickListener: PerformingRecipesListener, val performingRecipesList: List<PerformingRecipe>): RecyclerView.Adapter<PerformingRecipesAdapter.PerformingRecipesListViewHolder>() {

    class PerformingRecipesListViewHolder(private val binding: PerformingRecipeItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: PerformingRecipesListener, performingRecipe: PerformingRecipe){
            binding.performingRecipe = performingRecipe
            binding.clickListener = clickListener

            if (performingRecipe.recipeStatus == RecipeStatus.IN_PROGRESS){
                binding.recipeStatus.setTextColor(Color.parseColor("#EEC84B"))
                binding.recipeStatus.text = "Status: W trakcie przygotowywania"
            }else if (performingRecipe.recipeStatus == RecipeStatus.DONE){
                binding.recipeStatus.setTextColor(Color.parseColor("#6bfc03"))
                binding.recipeStatus.text = "Status: Zakończone"
            }else if (performingRecipe.recipeStatus ==  RecipeStatus.CANCELED){
                binding.recipeStatus.setTextColor(Color.parseColor("#fc0303"))
                binding.recipeStatus.text = "Status: Anulowane"
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PerformingRecipesListViewHolder{
        return PerformingRecipesListViewHolder(PerformingRecipeItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PerformingRecipesListViewHolder, position: Int) {
        val performingRecipe = performingRecipesList[position]
        holder.bind(clickListener,performingRecipe)
    }

    override fun getItemCount() = performingRecipesList.size

    class PerformingRecipesListener(val clickListener: (performingRecipe: PerformingRecipe) -> Unit){
        fun onClick(performingRecipe: PerformingRecipe) = clickListener(performingRecipe)
    }
}