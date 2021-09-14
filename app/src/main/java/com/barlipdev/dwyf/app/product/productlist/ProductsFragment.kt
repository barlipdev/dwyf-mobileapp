package com.barlipdev.dwyf.app.product.productlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.databinding.ProductsFragmentBinding
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.utils.visible

class ProductsFragment : Fragment() {

    private lateinit var binding: ProductsFragmentBinding
    private val viewModel: ProductsViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this,ProductsViewModel.Factory(activity.application)).get(ProductsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductsFragmentBinding.inflate(inflater,container,false)
        val activity = requireActivity()
        var userId = arguments?.getString("userId").toString()

        activity.runOnUiThread {
            viewModel.getProductsList(userId)
        }

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.progresBar.visible(true)


        viewModel.productsList.observe(viewLifecycleOwner, Observer { it ->
            when(it) {
                is Resource.Success -> {
                    binding.progresBar.visible(false)
                    binding.productListRecyclerView.adapter = ProductAdapter(it.value)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(),"Coś poszło nie tak",Toast.LENGTH_SHORT).show()
                }
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}