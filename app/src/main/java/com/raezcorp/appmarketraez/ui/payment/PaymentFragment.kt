package com.raezcorp.appmarketraez.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.raezcorp.appmarketraez.core.SecurityPreferences
import com.raezcorp.appmarketraez.core.SecurityPreferences.encryptedPreferences
import com.raezcorp.appmarketraez.databinding.FragmentPaymentBinding
import com.raezcorp.appmarketraez.model.DireccionEnvio
import com.raezcorp.appmarketraez.model.MetodoPago
import com.raezcorp.appmarketraez.model.PaymentRequest
import com.raezcorp.appmarketraez.util.Constants


class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val viewModel : PaymentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        events()

    }

    private fun events() = with(binding) {

        btnFinish.setOnClickListener {

            //ADDRESS
            val directionType =
                if (rbHome.isChecked) Constants.HOME else if (rbOffice.isChecked) Constants.OFFICE else Constants.OTHERS
            val address = edtAddress.text.toString()
            val ref = edtRef.text.toString()
            val district = edtDistrict.text.toString()

            //DATE
            val date = edtDate.text.toString()
            val hour = edtHour.text.toString()

            //DELIVERY
            val paymentType =
                if (rbHome.isChecked) Constants.YAPE else if (rbOffice.isChecked) Constants.PLIN else Constants.CASH
            val amount = edtAmount.text.toString().toDouble()

            val request = PaymentRequest(
                DireccionEnvio(address, district, ref, directionType),
                "$date $hour",
                MetodoPago(amount, paymentType),
                listOf(),
                0.0
            )

            val token =
                SecurityPreferences.getToken(requireContext().encryptedPreferences(Constants.PREFERENCES_TOKEN))

            //TODO Send Service
            viewModel.payment(request, token)

        }
    }

    private fun init() = with(binding) {

        containerAddress.setOnClickListener {

            containerChildAddress.visibility = View.VISIBLE
            containerChildDate.visibility = View.GONE
            containerChildPayment.visibility = View.GONE

        }

        containerDate.setOnClickListener {

            containerChildAddress.visibility = View.GONE
            containerChildDate.visibility = View.VISIBLE
            containerChildPayment.visibility = View.GONE
        }

        containerPayment.setOnClickListener {

            containerChildAddress.visibility = View.GONE
            containerChildDate.visibility = View.GONE
            containerChildPayment.visibility = View.VISIBLE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}