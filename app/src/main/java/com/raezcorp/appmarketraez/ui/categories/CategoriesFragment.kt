package com.raezcorp.appmarketraez.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.raezcorp.appmarketraez.core.SecurityPreferences.encryptedPreferences
import com.raezcorp.appmarketraez.core.SecurityPreferences.getToken
import com.raezcorp.appmarketraez.databinding.ActivityMainBinding.inflate
import com.raezcorp.appmarketraez.databinding.FragmentCategoriesBinding
import com.raezcorp.appmarketraez.util.Constants


class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    //private lateinit var adapter :CategoryAdapter

    private val viewModel:CategoriesViewModel by viewModels()

    private val adapter by lazy{
        CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        loadData()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.categories.observe(viewLifecycleOwner,{
            adapter.updateCategories(it)

        })
    }

    private fun loadData() {
        val token = getToken(requireContext().encryptedPreferences(
            Constants.PREFERENCES_TOKEN))
        viewModel.getCategories(token)

    }

    private fun setupAdapter() {
        binding.rvCategories.adapter = this.adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}