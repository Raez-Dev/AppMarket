package com.raezcorp.appmarketraez.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.raezcorp.appmarketraez.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        events()
    }

    private fun init() {
        // Get genders

        viewModel.getGenders()

    }


    private fun events() =with(binding){
        btnCreateAccount.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() =with(binding){

        val names = edtNames.editText?.text.toString()
        val lastname = edtLastname.editText?.text.toString()
        val email = edtEmail.editText?.text.toString()
        val mobile = edtMobile.editText?.text.toString()
        val nroDoc = edtNroDoc.editText?.text.toString()
        val password = edtPassword.editText?.text.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }

}