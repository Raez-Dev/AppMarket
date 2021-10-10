package com.raezcorp.appmarketraez.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.raezcorp.appmarketraez.core.Message.toast
import com.raezcorp.appmarketraez.databinding.FragmentRegisterBinding
import com.raezcorp.appmarketraez.model.CreateAccountRequest
import com.raezcorp.appmarketraez.model.Gender

class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    private var progress:KProgressHUD? = null

    private var genders : List<Gender> = listOf()

    private var gender =""

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
        setupObservables()
    }

    private fun setupObservables() {
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
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            requireContext().toast(error)
        })

        viewModel.genders.observe(viewLifecycleOwner,{
            binding.spGender.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,it)
            this.genders = it
        })

        viewModel.usuario.observe(viewLifecycleOwner,{usuario ->
            usuario?.let{
                requireContext().toast("Bienvenido Sr. ${usuario.nombres} ${usuario.apellidos}")
                requireActivity().onBackPressed()
            }
        })
    }

    private fun init() {
        // Get genders

        viewModel.getGenders()

    }


    private fun events() =with(binding){
        btnCreateAccount.setOnClickListener {
            createAccount()
        }

        /*Implement spinner methods*/
        spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gender = genders[position].genero
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun createAccount() =with(binding){

        val names = edtNames.editText?.text.toString()
        val lastname = edtLastname.editText?.text.toString()
        val email = edtEmail.editText?.text.toString()
        val mobile = edtMobile.editText?.text.toString()
        val nroDoc = edtNroDoc.editText?.text.toString()
        val password = edtPassword.editText?.text.toString()

        viewModel.createAccount(CreateAccountRequest(names,lastname,email,password,mobile,gender,nroDoc))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
        progress = null;
    }

}