package com.raezcorp.appmarketraez.core

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

abstract class BaseFragment  : Fragment(){

    protected fun navigation(): NavController?{
        return view?.let {
            Navigation.findNavController(it)
        }
    }

    protected fun navigateToAction(action:Int){
        navigation()?.navigate(action)
    }

    protected fun navigationToDirection(directions: NavDirections){
        navigation()?.navigate(directions)
    }

    protected fun navigate(action:Int?=null,directions:NavDirections?=null){
        action?.let { action->
            navigation()?.navigate(action)
        }?: kotlin.run {
            directions?.let { directions->
                navigation()?.navigate(directions)
            }
        }
    }

}
