package com.raezcorp.appmarketraez

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.raezcorp.appmarketraez.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    //FragmentSplashBinding
    // late init evita la regla de kotlin para inicializar todas las variables
    // Simbolo ? Safety proteje variables nulas
    private var _binding : FragmentSplashBinding? = null;

    // Simbolo !! obtiene la variable cuando no es nulo
    private val binding get()= _binding!!

    private val SPLASH_TIME_OUT :Long = 3000

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        //return inflater.inflate(R.layout.fragment_splash, container, false)
        return binding?.root
    }

    // Despues de que la vista fue creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Opciones de mapeo de vista
        //  FindView
       /* val btnNext:Button = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener {
        }*/

        //  Kotlin Syntethic o Extensions (Ya se encuentra deprecado)
        //  Add plugin in Gradle
        /*btnNext.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
        }*/

        //  View Bindind (Manera correcta)
        /*binding.btnNext.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
        }*/

        // Splash screen waiting time
        Handler(Looper.getMainLooper()).postDelayed({
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
        },SPLASH_TIME_OUT)
    }

    // Cuando la pantalla se destruya se deja en null el binding para que sea seguro
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}