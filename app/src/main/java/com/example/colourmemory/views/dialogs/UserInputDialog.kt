package com.example.colourmemory.views.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.colourmemory.R
import com.example.colourmemory.base.BaseFragment
import com.example.colourmemory.databinding.FragmentUserInputDialogBinding
import com.example.colourmemory.model.PlayerDetails
import com.example.colourmemory.utils.extensions.hideKeyboard
import com.example.colourmemory.utils.extensions.showSnackBar
import com.example.colourmemory.utils.extensions.visible
import com.example.colourmemory.viewmodels.PlayerDataViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInputDialog: BottomSheetDialogFragment() {

    private lateinit var binding:FragmentUserInputDialogBinding
    private var onDataInsertSuccess:(()->Unit)?=null
    private val viewModel by viewModels<PlayerDataViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetTextDialogStyle)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserInputDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.run {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isCancelable = false
            with(binding){
                etName.requestFocus()
                txtScore.text = getString(R.string.score).plus(":").plus(" ").plus(arguments?.getInt(
                    SCORE)?:0)
                setListeners()
                initObservers()
            }
        }
    }

    private fun FragmentUserInputDialogBinding.setListeners(){
        etName.doOnTextChanged { text, _, _, _ ->
           val isValid = text?.isNotBlank()==true
           if(isValid) etName.error = null
           imgClearName.visible(isValid)
        }
        imgClearName.setOnClickListener {
            etName.setText("")
        }
        btnSave.setOnClickListener {
            if(etName.text.toString().isNotBlank()){
                context?.let { it1 -> it.hideKeyboard(it1) }
                val playerDetails = PlayerDetails(name = etName.text.toString().trim(), score = arguments?.getInt(
                    SCORE)?:0)
                viewModel.insertPlayerData(playerDetails)
            }
            else{
                etName.error = getString(R.string.valid_name_input_err_msg)
            }
        }
    }

    private fun FragmentUserInputDialogBinding.initObservers(){
        with(viewModel){
            onScoreInsertSuccess.observe(viewLifecycleOwner){
                dismiss()
                onDataInsertSuccess?.invoke()
            }
            onError.observe(viewLifecycleOwner){
                    errorMessage->
                activity?.showSnackBar(root,errorMessage)
            }
        }
    }

    fun setListener(onDataInsertSuccess:(()->Unit)){
        this.onDataInsertSuccess = onDataInsertSuccess
    }

    companion object{
        private const val TAG = "UserInputDialog"
        private const val SCORE ="Score"
        fun show(fragment:BaseFragment,score:Int,onDataInsertSuccess:(()->Unit)){
            UserInputDialog().apply {
                arguments = Bundle().apply {
                    putInt(SCORE,score)
                }
                setListener(onDataInsertSuccess)
                show(fragment.childFragmentManager,TAG)
            }
        }
    }
}