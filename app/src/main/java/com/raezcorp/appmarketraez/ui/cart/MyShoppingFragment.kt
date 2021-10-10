package com.raezcorp.appmarketraez.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raezcorp.appmarketraez.databinding.FragmentMyShoppingBinding

class MyShoppingFragment : Fragment() {

    private var _binding: FragmentMyShoppingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }
}