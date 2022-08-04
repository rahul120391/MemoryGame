package com.example.colourmemory.views.fragments

import android.os.Bundle
import android.view.View
import com.example.colourmemory.R
import com.example.colourmemory.base.BaseFragment
import com.example.colourmemory.databinding.FragmentLaunchBinding
import com.example.colourmemory.navigator.Navigator
import com.example.colourmemory.utils.CustomOutlineProvider
import com.example.colourmemory.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchFragment:BaseFragment(R.layout.fragment_launch) {


    @Inject
    lateinit var navigator: Navigator
    private val binding by viewBinding(FragmentLaunchBinding::bind)
    override fun getFragmentTag(): String = TAG

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnStartGame.outlineProvider = CustomOutlineProvider(20F)
            setListener()
        }
    }

    private fun FragmentLaunchBinding.setListener(){
        btnStartGame.setOnClickListener {
             navigator.moveToGameFragment()
        }
    }

    companion object{
        private const val TAG = "LaunchView"
    }
}