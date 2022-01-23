package com.spravochnic.scbguide

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.snackbar.Snackbar
import com.spravochnic.scbguide.api.ApiService

fun getUriForBackendImagePath(imagePath: String?): Uri {
    return Uri.parse(ApiService.baseUrl + imagePath)
}

fun getDefaultShimmer(context: Context): Drawable {
    val shimmer = Shimmer.ColorHighlightBuilder().setBaseColor(
        ContextCompat.getColor(context, R.color.main_blue)
    ).setHighlightColor(
        ContextCompat.getColor(context, R.color.main_screen_color)
    ).setDuration(1000)
        .setBaseAlpha(1f)
        .setHighlightAlpha(1f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()
    return ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
}

fun ImageView.glide(url: String) {
    Glide
        .with(this)
        .load(getUriForBackendImagePath(url))
        .placeholder(getDefaultShimmer(this.context))
        .error(R.color.main_blue)
        .into(this)
}

fun createSnackbar(
    anchorView: View,
    text: String?,
    buttonText: String? = null,
    onButtonClicked: (() -> Unit)? = null
): Snackbar {
    val imm =
        App.getInstance().applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) imm.hideSoftInputFromWindow(anchorView.windowToken, 0)
    val snackbar = Snackbar.make(
        anchorView,
        text.toString(),
        Snackbar.LENGTH_SHORT
    )
    snackbar.setBackgroundTint(ContextCompat.getColor(App.getInstance(), R.color.main_blue))
    snackbar.setTextColor(ContextCompat.getColor(App.getInstance(), android.R.color.white))
    snackbar.setActionTextColor(ContextCompat.getColor(App.getInstance(), android.R.color.white))
    val textView =
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.maxLines = 10
    if (buttonText != null) {
        snackbar.setAction(buttonText) {
            onButtonClicked?.invoke() ?: snackbar.dismiss()
        }
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
    }
    return snackbar
}

const val trafficlight = "trafficlight"
const val arrowtranslation = "arrowtranslation"
const val box = "box"
const val relay = "relay"
const val railchain = "railchain"
const val centralization = "centralization"
const val alsn = "alsn"
const val edrive = "edrive"
const val gatc = "gatc"
const val transformer = "transformer"
const val aps = "aps"
const val station = "station"
const val bmrc= "bmrc"
const val bridge= "bridge"