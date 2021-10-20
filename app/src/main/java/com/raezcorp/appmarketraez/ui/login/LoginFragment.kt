package com.raezcorp.appmarketraez.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.raezcorp.appmarketraez.MainMenuActivity
import com.raezcorp.appmarketraez.R
import com.raezcorp.appmarketraez.core.BaseFragment
import com.raezcorp.appmarketraez.core.SecurityPreferences.encryptedPreferences
import com.raezcorp.appmarketraez.core.SecurityPreferences.getToken
import com.raezcorp.appmarketraez.core.SecurityPreferences.saveToken
import com.raezcorp.appmarketraez.data.Api
import com.raezcorp.appmarketraez.databinding.FragmentLoginBinding
import com.raezcorp.appmarketraez.model.LoginRequest
import com.raezcorp.appmarketraez.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //Add variable to viewmodel
    private val viewModel: LoginViewModel by viewModels() // This is with implement "ktx"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*init()*/
        events()
        setUpObservables()
    }

   /* private fun init() {
        val token = getToken(requireContext().encryptedPreferences(Constants.PREFERENCES_TOKEN))

        if (!token.isEmpty()){
            startActivity(Intent(requireContext(),MainMenuActivity::class.java))
        }
    }*/

    private fun setUpObservables() = with(binding) {
        // Observables by LiveData
        viewModel.loader.observe(viewLifecycleOwner, {
            if (it == true) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })

        viewModel.token.observe(viewLifecycleOwner, { token ->
            //  Save locally
            /*requireContext().
            getSharedPreferences(Constants.PREFERENCES_TOKEN,0).
            edit().
            putString(Constants.KEY_TOKEN,token).
            apply()*/

            saveToken(token, requireContext().encryptedPreferences(Constants.PREFERENCES_TOKEN))

        })

        viewModel.user.observe(viewLifecycleOwner, {
            // Nav to Menu

            Toast.makeText(requireContext(), it.nombres, Toast.LENGTH_SHORT).show()
            it?.let {
                    startActivity(Intent(requireContext(),MainMenuActivity::class.java))
            }
        })
    }

    private fun events() = with(binding) {
        btnIngresar.setOnClickListener {
            authCredentials()
        }

        tvCreateAccount.setOnClickListener {
            //Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerFragment)
            navigate(action= R.id.action_loginFragment_to_registerFragment)

        }
    }

    private fun authCredentials() = with(binding) {
        val email = edtCorreo.editText?.text.toString()
        val password = edtClave.editText?.text.toString()
        // Use retrofit

        /*val response = Api.build().authenticate(LoginRequest(email,password))
        response.enqueue(object : Callback<LoginDto>{
            override fun onResponse(call: Call<LoginDto>, response: Response<LoginDto>) {

                if(response.isSuccessful){ //200

                    val loginDto = response.body();
                    // Let -> Scope to use when the val is not null
                    loginDto?.let {
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    // If you have cascade request, you need to add a new callback into this, that's call Hell Callbacks

                }else{ //400,401,500
                    Toast.makeText(requireContext(), response.message(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginDto>, t: Throwable) {
                Toast.makeText(requireContext(), t.message,Toast.LENGTH_LONG).show()
            }
        })*/

        // Use Coroutines

        // Coroutine Scope
        // First Define Scope
        // Second select dispatcher
        // Third use suspend functions
        /*GlobalScope.launch(Dispatchers.Main) {
            try {
                //  Main Thread
                val response = withContext(Dispatchers.IO){
                    // Extra thread
                    Api.build().authenticate(LoginRequest(email,password))
                }
                //  Main Thread
                if(response.isSuccessful){ //200

                    val loginDto = response.body();
                    loginDto?.let {
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                }else{ //400,401,500
                    Toast.makeText(requireContext(), response.message(),Toast.LENGTH_LONG).show()
                }
            }catch (ex:Exception){
                Toast.makeText(requireContext(), ex.message,Toast.LENGTH_LONG).show()
            }
        }*/

        // Use MVVM

        viewModel.auth(email, password)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }

}