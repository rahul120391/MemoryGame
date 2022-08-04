package com.example.colourmemory.navigator

import androidx.fragment.app.FragmentActivity
import com.example.colourmemory.base.BaseFragment
import com.example.colourmemory.utils.extensions.addFragment
import com.example.colourmemory.utils.extensions.replaceFragment
import com.example.colourmemory.views.dialogs.UserInputDialog
import com.example.colourmemory.views.fragments.GameFragment
import com.example.colourmemory.views.fragments.ScoreListFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor(private val activity: FragmentActivity):Navigator {
    override fun moveToGameFragment() {
        activity.replaceFragment(GameFragment.newInstance())
    }

    override fun showUserInputDialog(fragment: BaseFragment,score:Int,onSuccess:(()->Unit)) {
        UserInputDialog.show(fragment,score,onSuccess)
    }

    override fun moveToScoreListFragment() {
        activity.addFragment(ScoreListFragment.newInstance())
    }


}