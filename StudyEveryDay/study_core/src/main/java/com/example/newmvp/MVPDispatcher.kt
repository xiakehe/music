package com.example.newmvp

import android.content.Intent
import android.os.Bundle

class MVPDispatcher {
    private val presenters: MutableList<MVPPresenter<*>> = mutableListOf()
    fun <V : MVPView> addPresenter(presenter: MVPPresenter<V>) {
        if (presenter.isViewAttached() && !presenters.contains(presenter)) {
            addPresenter(presenter)
        }
    }

    internal fun <V : MVPView> removePresenter(presenter: MVPPresenter<V>) {
        if (presenters.contains(presenter)) {
            presenters.remove(presenter)
            if (presenter.isViewAttached()) {
                presenter.detach()
            }
        }
    }

    fun dispatchOnCreate(bundle: Bundle?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onCreate(bundle)
            }
        }
    }

    fun dispatchOnStart() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onStart()
            }
        }
    }

    fun dispatchOnResume() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onResume()
            }
        }
    }

    fun dispatchOnPause() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onPause()
            }
        }
    }

    fun dispatchOnStop() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onStop()
            }
        }
    }

    fun dispatchOnRestart() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onRestart()
            }
        }
    }

    fun dispatchOnDestroy() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onDestroy()
            }
            removePresenter(it)
        }
    }

    fun dispatchOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    fun dispatchOnSaveInstanceState(outState: Bundle?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onSaveInstanceState(outState)
            }
        }
    }

    fun dispatchOnRestoreInstanceState(savedInstanceState: Bundle?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onRestoreInstanceState(savedInstanceState)
            }
        }
    }
}