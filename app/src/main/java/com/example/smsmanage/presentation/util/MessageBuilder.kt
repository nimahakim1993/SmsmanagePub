package com.example.smsmanage.presentation.util

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.example.smsmanage.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


class MessageBuilder(private val context: Context) {

    fun showToastMessage(
        message: String,
        isShortMessage: Boolean = true
    ) {
        val duration = if (isShortMessage) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        Toast.makeText(context, message, duration).show()
    }

    fun showSnackMessage(parentView: View, message: String?, isTop: Boolean = true) {
        val color = parentView.context.resources.getColor(R.color.green, null)
        val snackbar = Snackbar.make(parentView, message!!, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(color)
            .setActionTextColor(parentView.context.resources.getColor(R.color.white, null))
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setDuration(3000)
            .show()
        ViewCompat.setLayoutDirection(snackbar.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        val tv =
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
        if (isTop) {
            val view = snackbar.view
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
        }
    }
}