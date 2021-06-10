package com.sam.actonline.utils

import android.content.Context
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.sam.actonline.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Created by Dinh Sam Vu on 4/14/2021.
 */
object AlertHelper {

    fun showTipAlert(
        context: Context,
        icon: Int = R.drawable.ic_notification,
        title: String,
        desc: String,
        onCallback: (() -> Unit)? = null
    ) {
        val dialog = MaterialAlertDialogBuilder(context, R.style.RoundShapeTheme)
        dialog.setTitle(title)
            .setMessage(desc)
            .setPositiveButton(R.string.ok) { _, _ ->
                if (onCallback != null) {
                    onCallback()
                }
            }
            .setIcon(icon)
            .setCancelable(false)
        showDialog(dialog) {

        }
    }

    fun showYesNoAlert(
        context: Context,
        icon: Int,
        title: String,
        desc: String,
        onAgreeCallback: () -> Unit,
        onCancelCallback: () -> Unit
    ) {
        val dialog = MaterialAlertDialogBuilder(context)
        dialog.setTitle(title)
            .setMessage(desc)
            .setPositiveButton(R.string.ok) { _, _ -> onAgreeCallback() }
            .setNegativeButton(R.string.cancel) { _, _ -> onCancelCallback() }
            .setIcon(icon)
            .setCancelable(false)
        showDialog(dialog) {
        }
    }

    private fun showDialog(dialog: MaterialAlertDialogBuilder, callback: (a: AlertDialog) -> Unit) {
        val attributes: WindowManager.LayoutParams?
        val a: AlertDialog = dialog.create()
        val window: Window? = a.window
        callback(a)
        if (window != null) {
            attributes = window.attributes ?: return
            attributes.windowAnimations = R.style.DialogTheme
            a.show()
        }
    }
}