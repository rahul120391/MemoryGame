package com.example.colourmemory.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.colourmemory.databinding.ActivityGameBinding
import com.example.colourmemory.navigator.Navigator
import com.example.colourmemory.utils.extensions.viewBinding
import com.example.colourmemory.views.fragments.GameFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameActivity : AppCompatActivity() {


    private val binding by viewBinding(ActivityGameBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments.last()
        if (fragment is GameFragment){
            fragment.checkGameState()
        }
        else{
            super.onBackPressed()
        }

    }
}