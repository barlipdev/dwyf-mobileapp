package com.barlipdev.dwyf.app.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.app.product.scanning.ScanViewModel
import com.barlipdev.dwyf.authentication.AuthenticationActivity
import com.barlipdev.dwyf.databinding.BottomAddingFragmentBinding
import com.barlipdev.dwyf.databinding.HomeFragmentBinding
import com.barlipdev.dwyf.databinding.ProductAddingTypeFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.utils.startNewActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, HomeViewModel.Factory(activity.application)).get(
            HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        preferences = DataStoreManager(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dialog = BottomSheetDialog(requireContext())


        viewModel.navigateToScan.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.homeFragment2){
                if (isNavigate){
                    val view = layoutInflater.inflate(R.layout.product_adding_type_fragment,null)

                    val sheetBinding = ProductAddingTypeFragmentBinding.inflate(layoutInflater,view as ViewGroup, false)
                    sheetBinding.lifecycleOwner = requireActivity()
                    sheetBinding.viewModel = viewModel

                    sheetBinding.cardViewScan.setOnClickListener {
                        val bundle = bundleOf("userId" to viewModel.user.value?.id)
                        this.findNavController().navigate(R.id.action_homeFragment2_to_scanFragment, bundle)
                        dialog.dismiss()
                    }

                    sheetBinding.cardViewMannualy.setOnClickListener{
                        this.findNavController().navigate(R.id.action_homeFragment2_to_manualProductAddingFragment)
                        dialog.dismiss()
                    }

                    dialog.setCancelable(true)
                    dialog.setContentView(sheetBinding.bottomSheetView)
                    dialog.show()
                    viewModel.navigateToScanFinished()
                }
            }
        } })

        viewModel.navigateToProducts.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.homeFragment2){
                if (isNavigate){
                    val bundle = bundleOf("userId" to viewModel.user.value?.id)
                    this.findNavController().navigate(R.id.action_homeFragment2_to_productsFragment, bundle)
                    viewModel.navigateToProductsFinished()
                }
            }
        } })

        viewModel.navigateToFilterRecipe.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.homeFragment2){
                if (isNavigate){
                    this.findNavController().navigate(R.id.action_homeFragment2_to_filterRecipeFragment)
                    viewModel.navigateToFilterRecipeFinished()
                }
            }
        } })

        viewModel.navigateToPerformingRecipes.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.homeFragment2){
                if (isNavigate){
                    this.findNavController().navigate(R.id.action_homeFragment2_to_performingRecipesFragment)
                    viewModel.navigateToPerformingRecipesFinished()
                }
            }
        } })


        preferences.userJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                viewModel.setUser(preferences.getUserFromJson().value)
            }
        })

    }

}