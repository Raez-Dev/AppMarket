package com.raezcorp.appmarketraez.ui.shoppingCar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.raezcorp.appmarketraez.R
import com.raezcorp.appmarketraez.core.BaseAdapter
import com.raezcorp.appmarketraez.core.Message.toast
import com.raezcorp.appmarketraez.databinding.FragmentMyShoppingBinding
import com.raezcorp.appmarketraez.databinding.ItemProductBinding
import com.raezcorp.appmarketraez.databinding.ItemShoppingcarBinding
import com.raezcorp.appmarketraez.model.Product
import com.raezcorp.appmarketraez.model.shoppingCar
import com.squareup.picasso.Picasso

class MyShoppingFragment : Fragment() {

    private var _binding : FragmentMyShoppingBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ShoppingCarViewModel by viewModels()

    private var progress: KProgressHUD? = null

    private val adapter: BaseAdapter<shoppingCar> = object : BaseAdapter<shoppingCar>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<shoppingCar> {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_shoppingcar, parent, false)
            return object : BaseAdapter.BaseAdapterViewHolder<shoppingCar>(view) {
                private val binding : ItemShoppingcarBinding = ItemShoppingcarBinding.bind(view)
                override fun bind(entity: shoppingCar) = with(binding) {

                    tvDescription.text = entity.descripcion
                    tvCant.text = entity.cantidad.toString()
                    tvTotal.text = (entity.precio * entity.cantidad).toString()

                    Picasso.get().load(entity.imagen).error(R.drawable.product_error).into(imgCart)

                    imgEdit.setOnClickListener {
                        Toast.makeText(requireContext(),entity.descripcion, Toast.LENGTH_SHORT).show()
                    }

                    imgDelete.setOnClickListener {
                        deleteProduct(entity)
                    }

                }
            }
        }
    }

    fun deleteProduct(entity:shoppingCar){

        viewModel.delete(entity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init()
        setupAdapter()
        setupObservables()
    }

    private fun setupAdapter() = with(binding){

        rvCart.adapter = adapter
    }

    private fun setupObservables() {



        viewModel.loader.observe(viewLifecycleOwner, Observer { condicion ->
            if(condicion){
                progress = KProgressHUD.create(requireContext())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please, wait...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show()
            }
            else{
                progress?.dismiss()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            requireContext().toast(error)
        })

        viewModel.cart.observe(viewLifecycleOwner, Observer { cart ->
            adapter.update(cart)
        })

        viewModel.deleteSuccess.observe(viewLifecycleOwner, Observer { messageSucess ->
            requireContext().toast(messageSucess)
        })

    }

    private fun init() {

        viewModel.getShoppingCar?.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        progress = null
    }

}