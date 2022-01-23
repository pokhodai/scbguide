package com.spravochnic.scbguide

import android.view.View
import androidx.lifecycle.Observer

class DefaultNetworkEventObserver(
    private val anchorView: View,
    private val doOnLoading: (() -> Unit)? = null,
    private val doOnSuccess: (() -> Unit)? = null,
    private val doOnError: (() -> Unit)? = null,
    private val doOnFailure: (() -> Unit)? = null,
    private val invokeSnackbar: Boolean = true
) : Observer<NetworkEvent<State>> {

    override fun onChanged(event: NetworkEvent<State>?) {
        event?.run {
            event.getContentIfNotHandled()?.run {
                val context = App.getInstance()
                when (this) {
                    State.LOADING -> doOnLoading?.invoke()
                    State.SUCCESS -> doOnSuccess?.invoke()
                    State.ERROR -> {
                        if (invokeSnackbar) {
                            createSnackbar(
                                anchorView = anchorView,
                                text = error ?: context.getString(R.string.networkErrorMessage),
                                context.getString(R.string.buttonOk),
                                doOnError
                            ).show()
                        } else {
                            doOnError?.invoke()
                        }
                    }
                    State.FAILURE -> {
                        if (invokeSnackbar) {
                            createSnackbar(
                                anchorView = anchorView,
                                text = context.getString(R.string.networkFailureMessage),
                                context.getString(R.string.buttonOk),
                                doOnFailure
                            ).show()
                        } else {
                            doOnFailure?.invoke()
                        }
                    }
                }
            }
        }
    }
}

enum class State { LOADING, SUCCESS, ERROR, FAILURE }
