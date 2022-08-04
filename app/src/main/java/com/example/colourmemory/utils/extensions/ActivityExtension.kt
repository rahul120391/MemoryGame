package com.example.colourmemory.utils.extensions

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.example.colourmemory.R
import com.example.colourmemory.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun FragmentActivity.addFragment(fragment: BaseFragment,  addToBackstack: Boolean = true) {
    supportFragmentManager.inTransaction {
        if(addToBackstack){
            addToBackStack(fragment.getFragmentTag())
        }
        setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        add(R.id.container,fragment,fragment.getFragmentTag())
    }
}

fun FragmentActivity.replaceFragment(fragment: BaseFragment,  addToBackstack: Boolean = true){
    supportFragmentManager.inTransaction {
        if(addToBackstack){
            addToBackStack(fragment.getFragmentTag())
        }
        setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        replace(R.id.container,fragment,fragment.getFragmentTag())
    }
}

fun FragmentActivity.popToPreviousFragment(){
    supportFragmentManager.popBackStack()
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Activity.showSnackBar(view:View?=null,message:String){
    Snackbar.make(view?:findViewById(android.R.id.content),message, Snackbar.LENGTH_SHORT).show()
}