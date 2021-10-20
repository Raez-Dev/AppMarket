package com.raezcorp.appmarketraez.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.kaopiz.kprogresshud.KProgressHUD
import com.raezcorp.appmarketraez.MainActivity
import com.raezcorp.appmarketraez.R
import com.raezcorp.appmarketraez.core.BaseAdapter
import com.raezcorp.appmarketraez.core.BaseFragment
import com.raezcorp.appmarketraez.core.Message.showMessage
import com.raezcorp.appmarketraez.core.Message.toast
import com.raezcorp.appmarketraez.core.SecurityPreferences
import com.raezcorp.appmarketraez.core.SecurityPreferences.encryptedPreferences
import com.raezcorp.appmarketraez.core.SecurityPreferences.getToken
import com.raezcorp.appmarketraez.databinding.DialogUnauthorizedBinding
import com.raezcorp.appmarketraez.databinding.FragmentCategoriesBinding
import com.raezcorp.appmarketraez.databinding.ItemCategoriesBinding
import com.raezcorp.appmarketraez.databinding.ItemProductBinding
import com.raezcorp.appmarketraez.model.Category
import com.raezcorp.appmarketraez.model.Product
import com.raezcorp.appmarketraez.util.Constants
import com.squareup.picasso.Picasso


class CategoriesFragment : BaseFragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private  var progress : KProgressHUD? = null

    private val viewModel:CategoriesViewModel by viewModels()

   /* private val adapter by lazy{
        CategoryAdapter() {
            // Nav with Save args
            val directions = CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment(it.uuid)
            Navigation.findNavController(binding.root).navigate(directions)
        }
    }*/

    private val adapter : BaseAdapter<Category> = object: BaseAdapter<Category>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<Category> {
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_categories,parent,false)
            return object: BaseAdapter.BaseAdapterViewHolder<Category>(view){
                private val binding: ItemCategoriesBinding = ItemCategoriesBinding.bind(view)
                override fun bind(entity: Category) {

                    Picasso.get().load(entity.cover).into(binding.imgCategories)

                    binding.imgCategories.setOnClickListener {
                        val directions = CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment(entity.uuid)
                        //Navigation.findNavController(binding.root).navigate(directions)
                        navigate(directions=directions)
                    }
                }
            }
        }
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
            adapter.update(it)
        })

        viewModel.loader.observe(viewLifecycleOwner, Observer { condition ->
            if (condition) {
                progress = KProgressHUD.create(requireContext())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please, wait")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show()
            }else{
                progress?.dismiss();
            }
        })
        viewModel.error.observe(viewLifecycleOwner,{
            requireContext().toast(it)
        })

        viewModel.authorize.observe(viewLifecycleOwner,{
            SecurityPreferences.saveToken(
                "",
                requireContext().encryptedPreferences(Constants.PREFERENCES_TOKEN)
            )
            requireContext().showMessage(it).show()
        })
    }

    private fun loadData() {
        val token = getToken(requireContext().encryptedPreferences(Constants.PREFERENCES_TOKEN))
        viewModel.getCategories(token)

    }

    private fun setupAdapter() {
        binding.rvCategories.adapter = adapter

        /*adapter.onClickCategory ={
            it.uuid
        }*/
    }

   /* private fun showMessage(message:String): AlertDialog {
        // Implement custom alert - dialog_unauthorized
        val binding = DialogUnauthorizedBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)

        val alertDialog = builder.create()
        binding.tvMessage.text = message

        binding.btnOk.setOnClickListener {
            alertDialog.dismiss()
            startActivity(Intent(requireContext(), MainActivity::class.java))

            SecurityPreferences.saveToken(
                "",
                requireContext().encryptedPreferences(Constants.PREFERENCES_TOKEN)
            )
        }
        return alertDialog
    }*/

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        progress = null
    }
}