package com.raezcorp.appmarketraez.ui.product.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.kaopiz.kprogresshud.KProgressHUD
import com.raezcorp.appmarketraez.R
import com.raezcorp.appmarketraez.core.BaseAdapter
import com.raezcorp.appmarketraez.core.Message.toast
import com.raezcorp.appmarketraez.databinding.FragmentProductDetailBinding
import com.raezcorp.appmarketraez.databinding.ItemImagesBinding
import com.raezcorp.appmarketraez.model.Product
import com.raezcorp.appmarketraez.model.shoppingCar
import com.squareup.picasso.Picasso

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private var progress : KProgressHUD? = null
    private lateinit var product: Product

    private var uuidCategory = ""

    private val viewModel : ProductDetailViewModel by viewModels()


    private val adapter: BaseAdapter<String> = object : BaseAdapter<String>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<String> {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent, false)
            return object : BaseAdapter.BaseAdapterViewHolder<String>(view) {
                private val binding : ItemImagesBinding = ItemImagesBinding.bind(view)
                override fun bind(entity: String) = with(binding) {
                    Picasso.get().load(entity).error(R.drawable.product_error).into(imgSecundary)
                }
            }
        }
    }


    override fun onCreateView(
           inflater: LayoutInflater, container: ViewGroup?,
           savedInstanceState: Bundle?
       ): View? {
           // Inflate the layout for this fragment
           _binding = FragmentProductDetailBinding.inflate(inflater,container,false)
           return binding.root
       }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        events()
        setupAdapter()
        setupObservables()


    }

    private fun setupObservables() = with(binding) {

        viewModel.loader.observe(viewLifecycleOwner, Observer { condicion ->
            if (condicion) {
                progress = KProgressHUD.create(requireContext())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please, wait...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show()
            } else {
                progress?.dismiss()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            requireContext().toast(error)
        })


        viewModel.responseSuccess.observe(viewLifecycleOwner, Observer { success ->
            requireContext().toast(success)
            Navigation.findNavController(binding.root).navigate(R.id.action_productDetailFragment_to_categoriesFragment)
        })
    }

    private fun events() = with(binding) {

        imgAdd.setOnClickListener {
            val number = tvCant.text.toString().toInt() + 1
            tvCant.text = "$number"
        }

        imgLess.setOnClickListener {
            if(tvCant.text.toString().toInt()!=0){
                val number = tvCant.text.toString().toInt() - 1
                tvCant.text = "$number"
            }
        }

        btnAdd.setOnClickListener {

            val _shoppingCar = shoppingCar(
                product.uuid,
                product.descripcion,
                product.precio,
                tvCant.text.toString().toInt(),
                product.imagenes[0],
                uuidCategory
            )

            viewModel.addShoppingCar(_shoppingCar)
        }
    }

    private fun setupAdapter() = with(binding) {
        rvDetail.adapter = adapter
    }


    private fun init() = with(binding) {


        arguments?.let { bundle ->

            product = ProductDetailFragmentArgs.fromBundle(bundle).product
            uuidCategory = ProductDetailFragmentArgs.fromBundle(bundle).uuidCategory

            product?.let { product ->

                tvDescription.text = product.descripcion
                tvPrice.text = product.precio.toString()
                tvDetail.text = product.caracteristicas

                Picasso.get().load(product.imagenes[0]).error(R.drawable.product_error).into(imgDetails)


                adapter.update(product.imagenes.filter { url ->
                    url != product.imagenes[0]
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        progress = null

    }
}