package com.spravochnic.scbguide

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.snackbar.Snackbar
import com.spravochnic.scbguide.api.ApiService

fun getUriForBackendImagePath(imagePath: String?): Uri {
    return Uri.parse(ApiService.baseUrl + imagePath)
}

fun getOtherUriForBackendImagePath(imagePath: String?): Uri {
    return Uri.parse(baseImageUrl + imagePath)
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

fun ImageView.glideOtherUrl(url: String) {
    Glide
        .with(this)
        .load(getOtherUriForBackendImagePath(url))
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

fun TextView.setCutText(text: String, cutOptions: CutOptions.() -> Unit = {}) {
    this.postDelayed(30) {
        this.text = text.smartCutText(this, cutOptions)
    }
}

private fun String.smartCutText(
    view: TextView,
    cutOptionsInit: CutOptions.() -> Unit = {}
): String {
    val cutOptions = CutOptions()
    cutOptionsInit(cutOptions)
    val textWidth = this.getWidth(view)
    val lines = if (view.maxLines < 300) view.maxLines else 4
    val words = this.split(" ").mapIndexed { i, s -> if (i > 0) " $s" else s }
    val space = cutOptions.space
    if (textWidth <= view.width) return this
    var groupWidth = 0
    var currLine = 1
    var endIndex = 0
    words.forEachIndexed { i, word ->
        if (endIndex != 0) return@forEachIndexed
        fun calcFunc() {
            val wordWidth = word.getWidth(view)
            val freeSpace = view.width - groupWidth
            groupWidth += wordWidth + space
            if (groupWidth >= view.width) {
                if (currLine >= lines) {
                    val indexLast =
                        findNotShortWordIndex(words.subList(0, i), cutOptions.minWordLength)
                    val groupLength = words.subList(0, indexLast + 1).joinToString("").length
                    val terminator = cutOptions.terminator.length
                    val halfWidth =
                        (wordWidth / cutOptions.divideCoefficientWord + "...".getWidth(view))
                    endIndex =
                        if (freeSpace - halfWidth > 0 &&
                            word.replace(" ", "").length > 5
                        ) {
                            groupLength + 1 + (word.length / cutOptions.divideCoefficientWord) + terminator
                        }
                        else {
                            groupLength + terminator
                        }
                }
                else {
                    groupWidth = 0
                    currLine++
                    calcFunc()
                }
            }
        }
        calcFunc()
    }
    return if (endIndex == 0) this
    else
        replaceRange(endIndex - cutOptions.terminator.length, length, cutOptions.terminator)
}

fun String.getWidth(view: TextView): Int {
    val bounds = Rect()
    view.paint.getTextBounds(this, 0, this.length, bounds)
    val paddingLeft = view.paddingLeft
    val paddingRight = view.paddingRight
    return bounds.width() + paddingLeft + paddingRight
}

data class CutOptions(
    var minWordLength: Int = 3,
    var space: Int = 10,
    var divideCoefficientWord: Int = 2,
    var terminator: String = "..."
)

private fun findNotShortWordIndex(words: List<String>, minWordLength: Int): Int {
    val reverseWords = words.asReversed().map { it.replace(" ", "") }
    var index = words.count() - 1
    reverseWords.onEachIndexed { i, word ->
        if (word.length >= minWordLength) {
            index = i
            return (words.count() - 1) - index
        }
    }
    return index
}

fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this


const val baseImageUrl = "http://ovz1.sergei-pokhodai.mzlgn.vps.myjino.ru/instruments/imageLectory/"

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