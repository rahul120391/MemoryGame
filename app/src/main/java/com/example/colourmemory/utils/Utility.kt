package com.example.colourmemory.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.ref.WeakReference

object Utility {

    private var dialog: AlertDialog?=null

    fun showDialog(context: WeakReference<Context>,title:String, message:String,
                   positiveButtonText:String,
                   negativeButtonText:String,
                    onPositiveButtonClick:()->Unit){
        val dialogBuilder = context.get()?.let { MaterialAlertDialogBuilder(it) }
        dialogBuilder?.setTitle(title)?.setMessage(message)?.setPositiveButton(positiveButtonText){
                _,_->
            onPositiveButtonClick()
        }?.setNegativeButton(negativeButtonText){
                _,_->
        }?.setCancelable(false)
        dialog = dialogBuilder?.create()
        if(dialog?.isShowing==false) dialog?.show()
    }
}