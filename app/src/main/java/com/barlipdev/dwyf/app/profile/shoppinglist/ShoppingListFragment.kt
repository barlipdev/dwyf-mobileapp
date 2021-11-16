package com.barlipdev.dwyf.app.profile.shoppinglist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.barlipdev.dwyf.R

class ShoppingListFragment : Fragment() {

    companion object {
        fun newInstance() = ShoppingListFragment()
    }

    private lateinit var viewModel: ShoppingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopping_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShoppingListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}