package com.example.colourmemory.base


import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(layoutId:Int):Fragment(layoutId) {

    abstract fun getFragmentTag():String
}