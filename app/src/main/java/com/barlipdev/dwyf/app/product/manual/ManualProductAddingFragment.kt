package com.barlipdev.dwyf.app.product.manual

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.barlipdev.dwyf.R

class ManualProductAddingFragment : Fragment() {

    companion object {
        fun newInstance() = ManualProductAddingFragment()
    }

    private lateinit var viewModel: ManualProductAddingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.manual_product_adding_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManualProductAddingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}