package com.barlipdev.dwyf.app.product.scanning

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.app.home.HomeActivity
import com.barlipdev.dwyf.authentication.ui.login.LoginViewModel
import com.barlipdev.dwyf.databinding.BottomAddingFragmentBinding
import com.barlipdev.dwyf.databinding.ScanFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.responses.Product
import com.barlipdev.dwyf.utils.startNewActivity
import com.barlipdev.dwyf.utils.visible
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ScanFragment : Fragment() {

    private val CAMERA_REQUEST_CODE = 101
    private lateinit var codeScanner: CodeScanner
    private lateinit var binding: ScanFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: ScanViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, ScanViewModel.Factory(activity.application)).get(
            ScanViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = ScanFragmentBinding.inflate(inflater,container,false);
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        preferences = DataStoreManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupPermissions()
        val scannerView = binding.scannerView
        val activity = requireActivity()
        var userId = arguments?.getString("userId").toString()
        val dialog = BottomSheetDialog(requireContext())
        codeScanner = CodeScanner(activity, scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                activity.runOnUiThread {
                    viewModel.addProductByBarCode(
                        arguments?.getString("userId").toString(),it.text,
                        "2021-08-22"
                    )
                    Toast.makeText(activity, "Skanuje...", Toast.LENGTH_SHORT).show()
                }

            }

            errorCallback = ErrorCallback {
                activity.runOnUiThread {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }

        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        viewModel.product.observe(viewLifecycleOwner, androidx.lifecycle.Observer { product -> product?.let { it ->
            when (it){
                is Resource.Success -> {
                    Log.i("ProductInfo",it.toString())
                    var date: String
                    val product = it.value


                    val view = layoutInflater.inflate(R.layout.bottom_adding_fragment,null)

                    val sheetBinding = BottomAddingFragmentBinding.inflate(layoutInflater,view as ViewGroup, false)
                    sheetBinding.lifecycleOwner = requireActivity()

                    sheetBinding.nameValue.text = it.value.name
                    sheetBinding.addProductBtn.setOnClickListener {
                        if (sheetBinding.datePicker.month+1 < 10){
                            val month = sheetBinding.datePicker.month+1
                            date = sheetBinding.datePicker.year.toString()+"-0"+month+"-"+sheetBinding.datePicker.dayOfMonth.toString()
                        }else{
                            val month = sheetBinding.datePicker.month+1
                            date = sheetBinding.datePicker.year.toString()+"-"+month+"-"+sheetBinding.datePicker.dayOfMonth.toString()
                        }
                        product.expirationDate = date
                        activity.runOnUiThread {
                            viewModel.addProduct(userId,product)
                        }

                    }
                    dialog.setCancelable(false)
                    dialog.setContentView(sheetBinding.bottomSheetView)
                    dialog.show()
                }
                is Resource.Failure -> {
                    Toast.makeText(context,"Nie udało się znaleźć tego produktu :/",Toast.LENGTH_SHORT).show()
                    Log.i("ProductInfo",it.toString())
                }
            }

        } })

        viewModel.addedProduct.observe(viewLifecycleOwner, androidx.lifecycle.Observer { addedProduct -> addedProduct?.let {it ->
            when(it){
                is Resource.Success ->{
                    Toast.makeText(context,"Dodano "+it.value.name,Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    codeScanner.startPreview()
                }
                is Resource.Failure ->{
                    Toast.makeText(context,"Nie udało sie dodać produktu",Toast.LENGTH_SHORT).show()
                }
            }
        } })

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions(){
        val permission = ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA),CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(activity, "Musisz zezwolić na używanie kamery, aby korzystać ze skanera!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}