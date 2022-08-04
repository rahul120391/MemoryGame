package com.example.colourmemory.navigator

import com.example.colourmemory.base.BaseFragment

interface Navigator {
    fun moveToGameFragment()
    fun showUserInputDialog(fragment: BaseFragment,score:Int,onSuccess:(()->Unit))
    fun moveToScoreListFragment()
}