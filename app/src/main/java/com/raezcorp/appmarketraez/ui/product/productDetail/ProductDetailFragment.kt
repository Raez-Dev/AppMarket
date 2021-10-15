package com.raezcorp.appmarketraez.ui.product.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raezcorp.appmarketraez.core.SecurityPreferences
import com.raezcorp.appmarketraez.core.SecurityPreferences.encryptedPreferences
import com.raezcorp.appmarketraez.databinding.FragmentProductDetailBinding
import com.raezcorp.appmarketraez.util.Constants

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
           inflater: LayoutInflater, container: ViewGroup?,
           savedInstanceState: Bundle?
       ): View? {
           // Inflate the layout for this fragment
           _binding = FragmentProductDetailBinding.inflate(inflater,container,false)
           return binding.root
       }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    private fun loadData() {



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}