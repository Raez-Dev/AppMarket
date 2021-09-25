package com.raezcorp.appmarketraez

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.raezcorp.appmarketraez.data.Api
import com.raezcorp.appmarketraez.databinding.FragmentLoginBinding
import com.raezcorp.appmarketraez.model.LoginDto
import com.raezcorp.appmarketraez.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnIngresar.setOnClickListener {
            authCredentials()
        }
    }

    private fun authCredentials()= with(binding) {
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
        GlobalScope.launch(Dispatchers.Main) {
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
        }
        // Use MVVM
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null;
    }

}