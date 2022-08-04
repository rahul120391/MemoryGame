package com.example.colourmemory.views.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colourmemory.R
import com.example.colourmemory.base.BaseFragment
import com.example.colourmemory.databinding.FragmentScoreListBinding
import com.example.colourmemory.model.Scores
import com.example.colourmemory.utils.extensions.showSnackBar
import com.example.colourmemory.utils.extensions.viewBinding
import com.example.colourmemory.utils.extensions.visible
import com.example.colourmemory.viewmodels.PlayerDataViewModel
import com.example.colourmemory.views.adapters.ScoreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreListFragment:BaseFragment(R.layout.fragment_score_list) {

    private val binding by viewBinding(FragmentScoreListBinding::bind)
    private val viewModel by viewModels<PlayerDataViewModel>()
    override fun getFragmentTag(): String = TAG

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding)
        {
            initToolBar()
            initObservers()
        }
    }

    private fun FragmentScoreListBinding.initToolBar(){
        toolBar.apply {
            navigationIcon = ContextCompat.getDrawable(context,R.drawable.ic_baseline_arrow_back_ios_24)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun FragmentScoreListBinding.initObservers(){
        with(viewModel){
            fetchData()
            onScoreListFetch.observe(viewLifecycleOwner){
                group.visible(it.isNotEmpty())
                txtNoDataFound.visible(it.isEmpty())
                if(group.isVisible) initRecyclerView(it)
            }
            onError.observe(viewLifecycleOwner){
                errorMessage->
                activity?.showSnackBar(root,errorMessage)
            }
        }
    }

    private fun FragmentScoreListBinding.initRecyclerView(list:List<Scores>){
         with(rvScore){
             setHasFixedSize(true)
             layoutManager = LinearLayoutManager(context)
             addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
             adapter=ScoreAdapter(list)
         }
    }

    companion object{
        private const val TAG = "ScoreListView"
        fun newInstance():ScoreListFragment = ScoreListFragment()
    }
}