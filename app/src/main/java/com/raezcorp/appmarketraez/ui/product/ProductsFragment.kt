package com.raezcorp.appmarketraez.ui.product

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.kaopiz.kprogresshud.KProgressHUD
import com.raezcorp.appmarketraez.MainActivity
import com.raezcorp.appmarketraez.R
import com.raezcorp.appmarketraez.core.BaseAdapter
import com.raezcorp.appmarketraez.core.Message.showMessage
import com.raezcorp.appmarketraez.core.Message.toast
import com.raezcorp.appmarketraez.core.SecurityPreferences
import com.raezcorp.appmarketraez.core.SecurityPreferences.encryptedPreferences
import com.raezcorp.appmarketraez.databinding.DialogUnauthorizedBinding
import com.raezcorp.appmarketraez.databinding.FragmentProductsBinding
import com.raezcorp.appmarketraez.databinding.ItemProductBinding
import com.raezcorp.appmarketraez.model.Product
import com.raezcorp.appmarketraez.ui.categories.CategoriesFragmentDirections
import com.raezcorp.appmarketraez.ui.categories.CategoriesViewModel
import com.raezcorp.appmarketraez.util.Constants
import com.squareup.picasso.Picasso

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private  var progress : KProgressHUD? = null
    private val viewModel: ProductsViewModel by viewModels()
    private var uuidCategory = ""

    private val adapter :BaseAdapter<Product> = object:BaseAdapter<Product>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<Product> {
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
            return object:BaseAdapter.BaseAdapterViewHolder<Product>(view){
                private val binding:ItemProductBinding = ItemProductBinding.bind(view)
                override fun bind(entity: Product) {

                    binding.tvCode.text = entity.codigo
                    binding.tvDescription.text = entity.descripcion
                    binding.tvPrice.text = entity.precio.toString()

                    Picasso.get().load(entity.imagenes[0]).into(binding.imgProduct)

                    binding.contProduct.setOnClickListener {
                        onItemSelected(entity)
                    }
                }
            }
        }
    }

    private fun onItemSelected(entity:Product){
        val directions = ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(entity,uuidCategory )
        Navigation.findNavController(binding.root).navigate(directions)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            _binding = FragmentProductsBinding.inflate(inflater,container,false)
            return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setupObservables()
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.rvProducts.adapter=adapter
    }

    private fun init() {
        // Use save args
        arguments?.let {
            // Get uuid
            uuidCategory = ProductsFragmentArgs.fromBundle(it).uuid
            val token = SecurityPreferences.getToken(requireContext().encryptedPreferences(Constants.PREFERENCES_TOKEN))
            viewModel.getProducts(uuidCategory,token)

        }
    }

    private fun setupObservables() {
        viewModel.products.observe(viewLifecycleOwner,{
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
            //showMessage(it).show()
            SecurityPreferences.saveToken(
                "",
                requireContext().encryptedPreferences(Constants.PREFERENCES_TOKEN)
            )
            requireContext().showMessage(it).show()

        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        progress = null
    }
}