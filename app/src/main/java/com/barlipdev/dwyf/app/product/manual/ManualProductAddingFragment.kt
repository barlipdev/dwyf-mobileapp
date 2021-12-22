package com.barlipdev.dwyf.app.product.manual

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.barlipdev.dwyf.databinding.ManualProductAddingFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import java.util.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.network.Resource
import kotlinx.coroutines.launch

class ManualProductAddingFragment : Fragment() {

    private lateinit var binding: ManualProductAddingFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: ManualProductAddingViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, ManualProductAddingViewModel.Factory(activity.application)).get(
            ManualProductAddingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ManualProductAddingFragmentBinding.inflate(inflater,container,false)

        preferences = DataStoreManager(requireContext())

        preferences.userJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.user = preferences.getUserFromJson().value
            }
        })

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dp = DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                var m = ""
                var d = ""
                if (mMonth+1 < 10){
                    val mm = mMonth+1
                    m = "0"+ mm
                }else{
                    val mm = mMonth+1
                    m = ""+mm
                }
                if (mDay < 10){
                    d = "0"+mDay
                }else{
                    d = ""+mDay
                }
                binding.date.setText(""+mYear+"-"+m+"-"+d)
            },year,month,day)

            dp.show()
        }

        viewModel.user.observe(viewLifecycleOwner, Observer { it ->
            when (it){

                is Resource.Success -> {
                    lifecycleScope.launch {
                        preferences.saveUser(it.value)
                    }
                    Toast.makeText(context,"Produkt został dodany!",Toast.LENGTH_SHORT).show()
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(),"Coś poszło nie tak", Toast.LENGTH_SHORT).show()
                }

            }
        })

        viewModel.message.observe(viewLifecycleOwner, Observer { message -> message?.let {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        } })

    }

}